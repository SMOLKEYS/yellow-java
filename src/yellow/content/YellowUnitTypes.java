package yellow.content;

//a
import arc.*;
import arc.flabel.*;
import arc.graphics.*;
import arc.graphics.Color.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.struct.*;
import mindustry.ai.types.*;
import mindustry.ctype.*;
import mindustry.entities.*;
import mindustry.entities.abilities.*;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.*;
import mindustry.mod.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.type.ammo.*;
import mindustry.type.weapons.*;
import mindustry.world.meta.*;

import static mindustry.Vars.*;

public class YellowUnitTypes implements ContentList{
    public static UnitType
    
    //yellow units
    yellowAir, yellowGround;
    
    @Override
    public void load(){
        
        yellowAir = new UnitType("yellowAir"){{
            flying = true;
            health = 2147483647f;
            armor = 2147483647f;
            speed = 3f;
            accel = 0.08f;
            drag = 0.01f;
            range = 240f;
            maxRange = 240f;
            commandLimit = 85000;
            
            constructor = UnitEntity::create;
            defaultController = DefenderAI::new;
            region = Core.atlas.find("yellow");
            
            weapons.add(
                new Weapon("meltdown-shotgun"){{
                    reload = 60f;
                    x = 56f;
                    mirror = true;
                    shots = 15;
                    inaccuracy = 30f;
                    bullet = new ContinuousLaserBulletType(){{
                        damage = 100f;
                        width = 8f;
                        length = 240f;
                        lifetime = 60f;
                    }};
                }}
            );
        }
            @Override
            public void setStats(){
                super.setStats();
                stats.remove(Stat.health);
                stats.remove(Stat.armor);
                stats.remove(Stat.size);
                stats.remove(Stat.commandLimit);
                stats.remove(Stat.speed);
                stats.remove(Stat.weapons);
                /** FLabel soam might not be a good idea, performance-wise */
                stats.add(Stat.health, l -> l.add(new FLabel("{shake}[yellow]very high")));
                stats.add(Stat.armor, l -> l.add(new FLabel("{wind}[red]many")));
                stats.add(Stat.size, l -> l.add(new FLabel("{jump}shmol")));
                stats.add(Stat.commandLimit, l -> l.add(new FLabel("{wave}{rainbow}ALLLL THE ZEROOOOOOOOOOOOOOOOOOOOOOS")));
                stats.add(Stat.speed, "flar");
                stats.add(Stat.weapons, l -> l.add(new FLabel("{shake}[red]Meltdown Shotgun")));
                
            }
        };
        
        yellowGround = new UnitType("yellowGround"){{
            health = 2147483647f;
            armor = 2147483647f;
            speed = 1f;
            range = 400f;
            maxRange = 400f;
            
            constructor = MechUnit::create;
            region = Core.atlas.find("panama");
            
            weapons.add(
                new Weapon("anti-moth-spray"){{
                    reload = 2f;
                    x = 3f;
                    mirror = true;
                    shots = 25;
                    inaccuracy = 15f;
                    bullet = new BasicBulletType(){{
                        damage = 1f;
                        lifetime = 60f;
                        speed = 4f;
                        width = 8f;
                        height = 8f;
                        knockback = 5f;
                    }};
                }}
            );
        }};
    };
}