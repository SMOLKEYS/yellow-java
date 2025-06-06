package yellow.equality;

import arc.*;
import arc.func.*;
import arc.util.*;
import mindustry.*;
import mindustry.ai.types.*;
import mindustry.entities.*;
import mindustry.game.*;
import mindustry.gen.*;
import yellow.util.*;

public class EqualityDamage{
    static final EventType.UnitDamageEvent damageEvent = new EventType.UnitDamageEvent();

    //float
    public static String[] ent = {"trueHealth"};
    //float
    public static String[] iframeEnt = {"iFrames", "iframes", "invframes", "invFrames"};
    //float
    public static String[] maxEnt = {"trueMaxHealth"};
    //float
    public static String[] dmgEnt = {"lastDamage", "lastdamage"};
    //static flame.entities.MockGroup<T extends Entityc>
    public static String[] mockGEntries = {"all", "build", "bullet", "draw", "sync", "unit"};
    //static Seq<*>
    public static String[] eDamageEntries = {"excludeSeq", "queueExcludeRemoval", "excludeReAdd", "toRemove", "units", "damages"};
    //Seq<T extends Entityc>
    public static String mockGOther = "added";

    public static boolean hasEntry(Object obj, String field){
        return SafeReflect.get(obj, field) != null;
    }

    public static boolean isEnabled(){
        return SafeSettings.getBool("yellow-equal-treatment", false, false);
    }

    /** Attempts to completely erase an entity. VERY aggressive. */
    public static void annihilate(Entityc target, boolean removeRemnants, boolean showDeathEffect, @Nullable Cons<Entityc> entityAfter, @Nullable Cons<Bullet> bulletAfter){
        target.remove();
        Groups.all.remove(target);

        Class<?> entClass = target.getClass();

        if(target instanceof Unit u){
            Groups.unit.remove(u);
            u.health = u.maxHealth = u.shield = u.armor = 0f;
            if(u instanceof TimedKillUnit tk) tk.lifetime = 0f;

            Structs.each(s -> {
                if(hasEntry(target, s)){
                    SafeReflect.set(target, s, 0f);
                    SafeReflect.set(entClass, target, s, 0f);
                }
            }, ent);

            Structs.each(s -> {
                if(hasEntry(target, s)){
                    SafeReflect.set(target, s, 0f);
                    SafeReflect.set(entClass, target, s, 0f);
                }
            }, maxEnt);

            Structs.each(s -> {
                if(hasEntry(target, s)){
                    SafeReflect.set(target, s, 0f);
                    SafeReflect.set(entClass, target, s, 0f);
                }
            }, iframeEnt);

            Structs.each(s -> {
                if(hasEntry(target, s)){
                    SafeReflect.set(target, s, 0f);
                    SafeReflect.set(entClass, target, s, 0f);
                }
            }, dmgEnt);

            //flameout handler
            //freaky ass code imna be 100.gov
            Class<?> eDamage = SafeReflect.clazz("flame.unit.empathy.EmpathyDamage");

            Structs.each(ed -> {
                SafeReflect.invoke(eDamage, SafeReflect.get(eDamage, ed), "remove", new Unit[]{u}, Unit.class);
            }, eDamageEntries);

            u.destroy();
        }

        Class<?> mockGroup = SafeReflect.clazz("flame.entities.MockGroup");

        Structs.each(mg -> {
            SafeReflect.invoke(EntityGroup.class, SafeReflect.get(mockGroup, mg), "remove", new Entityc[]{target}, Entityc.class);
        }, mockGEntries);
        SafeReflect.invoke(EntityGroup.class, SafeReflect.get(mockGroup, mockGOther), "remove", new Entityc[]{target}, Entityc.class);

        if(target instanceof Drawc d) Groups.draw.remove(d);
        if(target instanceof Syncc s) Groups.sync.remove(s);

        if(removeRemnants){
            Groups.bullet.each(e -> {
                if(e.owner == target){
                    Groups.all.remove(e);
                    Groups.bullet.remove(e);
                    Groups.draw.remove(e);
                    if(e instanceof Syncc s) Groups.sync.remove(s);
                    if(bulletAfter != null) bulletAfter.get(e);
                }
            });
            Groups.unit.each(e -> {
                if(e instanceof UnitTetherc u && u.spawner() == target) annihilate(e, true, showDeathEffect, entityAfter, bulletAfter);
                if(e.controller() instanceof MissileAI a && a.shooter == target) annihilate(e, true, showDeathEffect, entityAfter, bulletAfter);
            });
        }

        if(entityAfter != null) entityAfter.get(target);

        if(showDeathEffect && target instanceof Unitc u && u.type() != null) u.type().deathExplosionEffect.at(u.x(), u.y(), u.bounds() / 2f / 8f);
    }

    public static void groupWipe(Entityc target){
        Groups.all.remove(target);
    }

    public static void theSpeedOfA(float speed){
        Class<?> sMain = SafeReflect.clazz("flame.special.SpecialMain");
        Class<?> stage4 = SafeReflect.clazz("flame.special.state.Stage4");

        Object o = SafeReflect.get(sMain, "activeState");
        if(o != null && o.getClass() == stage4){
            Object a = SafeReflect.get(o, "a");

            SafeReflect.set(a, "speed", speed);
        }
    }

    public static void theTimeOfA(float time){
        Class<?> sMain = SafeReflect.clazz("flame.special.SpecialMain");
        Class<?> stage4 = SafeReflect.clazz("flame.special.state.Stage4");

        Object o = SafeReflect.get(sMain, "activeState");
        if(o != null && o.getClass() == stage4){
            SafeReflect.set(o, "waitTime", time);
        }
    }

    public static void lights(boolean toggle){
        Vars.state.rules.lighting = toggle;
    }

    public static void handle(Unit entity, Bullet source, float health){
        boolean wasDead = entity.dead;
        float shieldBlock = Math.min(Math.max(entity.shield, 0.0F), source.damage);
        float definitiveHealth = entity.health + shieldBlock;
        float armorPierceExt = Damage.applyArmor(source.damage, entity.armor) / entity.healthMultiplier / Vars.state.rules.unitHealth(entity.team);
        float expectedHealth = definitiveHealth - armorPierceExt;

        if(source.type.pierceArmor){
            entity.damagePierce(source.damage);
        }else{
            entity.damage(source.damage);
        }

        //if entity health is more than the expected health, begin proper damage checking
        if(entity.health > expectedHealth){
            //calculate unapplied damage
            float blockedDamage = entity.health - expectedHealth + (source.type.pierceArmor ? armorPierceExt : 0f);

            //write damage to known variable names
            Structs.each(s -> {
                if(hasEntry(entity, s)){
                    SafeReflect.set(entity, s, entity.health - blockedDamage);
                }
            }, ent);

            //apply damage to health too
            entity.health -= blockedDamage;

            //check for invincibility frame variables and last damage trackers
            //if found, write zero to allow quick damaging
            Structs.each(s -> {
                if(hasEntry(entity, s)){
                    SafeReflect.set(entity, s, 0f);
                }
            }, iframeEnt);

            Structs.each(s -> {
                if(hasEntry(entity, s)){
                    SafeReflect.set(entity, s, 0f);
                }
            }, dmgEnt);
        }

        source.type.handlePierce(source, health, entity.x(), entity.y());

        Tmp.v3.set(entity).sub(source).nor().scl(source.type.knockback * 80f);
        if(source.type.impact) Tmp.v3.setAngle(source.rotation() + (source.type.knockback < 0 ? 180f : 0f));
        entity.impulse(Tmp.v3);
        entity.apply(source.type.status, source.type.statusDuration);

        Events.fire(damageEvent.set(entity, source));

        if(!wasDead && entity.dead){
            Events.fire(new EventType.UnitBulletDestroyEvent(entity, source));
            annihilate(entity, false, true, null, null);
        }
    }
}
