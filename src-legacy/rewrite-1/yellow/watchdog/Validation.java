package yellow.watchdog;

import arc.*;
import arc.func.*;
import arc.math.*;
import arc.struct.*;
import arc.util.*;
import arc.util.pooling.*;
import mindustry.*;
import mindustry.entities.abilities.*;
import mindustry.entities.units.*;
import mindustry.game.*;
import mindustry.gen.*;
import yellow.*;
import yellow.entities.bullet.*;
import yellow.entities.units.*;
import yellow.entities.units.entity.*;
import yellow.equality.*;

import static yellow.entities.bullet.SpinSpearBulletType.*;

/** Checks states of content associated with Yellow. */
public class Validation{

    private static boolean inMenu;

    public static void initYellowChecker(){
        TeamIndex.init();

        Events.run(EventType.Trigger.update, () -> {
            if(Vars.state.isMenu()){
                if(!inMenu){
                    TeamIndex.init();
                    inMenu = true;
                }
                return;
            }

            inMenu = false;

            TeamIndex.entries.each((t, e) -> {
                if(e != null && e.team != t){
                    TeamIndex.entries.put(e.team, e);
                    TeamIndex.entries.put(t, null);
                }

                if(e == null){
                    try{
                        TeamIndex.entries.put(t, (YellowUnitEntity) Groups.unit.find(ex -> ex instanceof YellowUnitEntity && ex.team() == t));
                    }catch(Exception ignored){}
                }else if(e.lives <= 0){
                    TeamIndex.entries.put(t, null);
                }else if(e.type().locationMatch(t)){
                    if(!e.isValid() && !e.isAdded() && e.lives > 0){
                        YellowDebug.info("Fixer, go! (@ of team @)", e, t);

                        /*
                        Class<?> eDamage = SafeReflect.clazz("flame.unit.empathy.EmpathyDamage");
                        Class<?> mockGroup = SafeReflect.clazz("flame.entities.MockGroup");
                        Class<?> aDamage = SafeReflect.clazz("flame.unit.empathy.EmpathyDamage$AbsoluteDamage");

                        Structs.each(mg -> SafeReflect.invoke(EntityGroup.class, SafeReflect.get(mockGroup, mg), "remove", new Entityc[]{e}, Entityc.class), Equality.mockGEntries);
                        SafeReflect.invoke(SafeReflect.get(mockGroup, Equality.mockGOther), "remove", new Entityc[]{e}, Entityc.class);
                        Structs.each(ed -> SafeReflect.invoke(eDamage, SafeReflect.get(eDamage, ed), "remove", new Unit[]{e}, Unit.class), Equality.eDamageEntries);

                        //absolutely not
                        SafeReflect.invoke(SafeReflect.get(Seq.class, eDamage, "toRemove"), "clear");
                        SafeReflect.invoke(SafeReflect.get(Seq.class, eDamage, "damages"), "clear");
                        SafeReflect.invoke(SafeReflect.get(IntMap.class, eDamage, "damageMap"), "clear");
                        Structs.each(ind -> SafeReflect.invoke(SafeReflect.get(Seq.class, eDamage, ind), "clear"), Equality.eDamageEntries);

                        //i invoke the great unholy fifth
                        SafeReflect.invoke(eDamage, "reset");
                        */

                        // :3
                        int lives = e.lives();
                        WeaponMount[] mounts = e.mounts();
                        Ability[] abilities = e.abilities();
                        SpellEntry[] spells = e.spells();
                        float mana = e.mana();
                        float invFrames = e.invFrames;
                        UnitController controller = e.controller();

                        Equality.annihilate(e, false, false, null, null);

                        //entity substitution; good luck caching the original instance
                        if(e.type().spawn(e.team(), e.x, e.y) instanceof YellowUnitEntity ent){
                            ent.vel(e.vel());
                            ent.lives(lives);
                            ent.mounts(mounts);
                            ent.abilities(abilities);
                            ent.spells(spells);
                            ent.mana(mana);
                            ent.invFrames = invFrames;
                            //edge case
                            if(controller != null) ent.controller(controller);

                            //clear life
                            ent.kill();

                            YellowDebug.info("@ of team @ has inherited data from @!", ent, t, e);
                        }
                    }
                }
            });

        });
    }

    public static void handleBullet(Class<?> type, Cons<Bullet> found){
        Groups.bullet.each(inst -> inst.type.getClass() == type, found);
    }

    public static void validateSpearBullets(){
        if(Vars.state.isMenu()) return;

        handleBullet(SpinSpearBulletType.class, b -> {
            if(b.lifetime > b.type.lifetime && b.data == null){
                SpearBulletData d = Pools.obtain(SpearBulletData.class, SpearBulletData::new);
                b.lifetime(99999999);
                b.drag(0);
                b.vel().setZero();
                b.rotation(d.startRotation = b.rotation() + Mathf.random(470, 750));
                d.targetRotation = Angles.angle(b.x, b.y, b.aimX, b.aimY);
                b.data(d);
            }
        });
    }

    public static <T> void nullPass(@Nullable T t, Cons<T> ifNotNull){
        if(t != null) ifNotNull.get(t);
    }

    static class TeamIndex{
        public static ObjectMap<Team, YellowUnitEntity> entries = new ObjectMap<>();

        public static void init(){
            for(Team t : Team.all){
                entries.put(t, null);
            }
        }
    }
}
