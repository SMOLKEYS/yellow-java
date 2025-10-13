package yellow.world.meta;

import arc.*;
import arc.audio.*;
import arc.func.*;
import arc.graphics.*;
import arc.scene.style.*;
import arc.struct.*;
import arc.util.*;
import mindustry.*;
import mindustry.core.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import yellow.*;
import yellow.util.*;

import java.util.*;

public class YellowAchievement{

    private static final Color unlockNotifColor = Pal.heal.cpy().a(0.5f);
    private static final Seq<YellowAchievement> hierarchyTmp = new Seq<>();

    public static final Seq<YellowAchievement> instances = new Seq<>();

    public String name, displayName, description, hint;

    /** The parent of this achievement. Must be unlocked before this one. */
    public @Nullable YellowAchievement parent;
    /** List of other achievements required before unlocking this one. */
    public @Nullable YellowAchievement[] requiredAchievements;
    public Drawable unlockIcon = Icon.lockOpen, lockIcon = Icon.lock;
    /** Required game state to unlock this achievemeent. TODO useless? */
    public StateCondition requiredGameState = StateCondition.any;
    /** Required condition(s) to unlock this achievement. */
    public Boolp requiredCondition = () -> true;
    /** Unlock sound for this achievement. */
    public Sound unlockSound = Sounds.message;
    /** If true, a notification popup will appear when unlocking this achievement. */
    public boolean notifyOnUnlock = true;
    /** Extra code to be executed upon unlocking this achievement. Posted on the {@link Application#post(Runnable) main loop thread}. */
    public Runnable onUnlock = () -> {};
    /** If true, this achievement will stay locked even if all conditions are met. */
    public boolean hold = false;

    public YellowAchievement(String name){
        this(name, null);
    }

    public YellowAchievement(String name, @Nullable YellowAchievement parent){
        if(name == null) throw new IllegalArgumentException("Achievement name cannot be null.");

        YellowAchievement potentialClone = instances.find(b -> Objects.equals(b.name, this.name));
        if(potentialClone != null) throw new IllegalArgumentException("Cannot have two achievements of the same name! (" + name + ")");

        if(parent != null){
            if(parent.parent == this) throw new IllegalArgumentException("CIRCULAR DEPENDENCY ERROR: Achievement " + name + " requires " + parent.name + " to be unlocked, and vice versa (" + name + " -> " + parent.name + " -> " + name + "...)");
            if(parent == this) throw new IllegalArgumentException("SELF DEPENDENCY ERROR: Achievement " + name + " has itself as its parent.");
        }

        this.name = name;
        this.parent = parent;
        this.displayName = Core.bundle.get("achievement." + name + ".name");
        this.description = Core.bundle.get("achievement." + name + ".description");
        this.hint = Core.bundle.get("achievement." + name + ".hint");

        instances.add(this);
    }

    public void unlock(){
        if(isUnlocked()) return;

        Core.settings.put("yellow-achievement-" + name + "-unlocked", true);
        unlockSound.play();
        Core.app.post(onUnlock);

        if(notifyOnUnlock) YellowVars.notifrag.showTintedNotification(
                ((TextureRegionDrawable)Tex.whiteui).tint(unlockNotifColor),
                Icon.lockOpen,
                Core.bundle.format("yellow.achievement-unlocked", displayName),
                70,
                true,
                null
        );
    }

    public boolean canUnlock(){
        boolean parentUnlocked = parent == null || parent.isUnlocked();

        if(requiredAchievements != null){
            boolean unlocks = true;

            for(YellowAchievement a: requiredAchievements){
                if(unlocks){
                    unlocks = a.isUnlocked();
                }
            }

            return parentUnlocked && unlocks && requiredGameState.condition.get(Vars.state) && requiredCondition.get() && !hold;
        }
        return parentUnlocked && requiredGameState.condition.get(Vars.state) && requiredCondition.get() && !hold;
    }

    public boolean isUnlocked(){
        return SafeSettings.getBool("yellow-achievement-" + name + "-unlocked", false, false);
    }

    public void update(){
        if(canUnlock() && !isUnlocked()) unlock();
    }

    @Override
    public String toString(){
        return "Achievement: " + name;
    }

    public static class StateCondition{

        public static final Seq<StateCondition> all = new Seq<>();

        public static final StateCondition
                //menu
                menu = new StateCondition(GameState::isMenu),
                //campaign
                campaign = new StateCondition(GameState::isCampaign),
                //custom game (not campaign)
                custom = new StateCondition(s -> s.isGame() && !s.isCampaign()),
                //survival game (not campaign)
                survival = new StateCondition(s -> s.isGame() && !s.isCampaign() && !s.rules.infiniteResources),
                //sandbox
                sandbox = new StateCondition(s -> s.isGame() && !s.isCampaign() && s.rules.infiniteResources),
                //in-game in general
                ingame = new StateCondition(GameState::isGame),
                //any
                any = new StateCondition(s -> true);

        public final int id;
        public final Boolf<GameState> condition;

        public StateCondition(Boolf<GameState> condition){
            this.condition = condition;
            this.id = all.size;
            all.add(this);
        }
    }

}
