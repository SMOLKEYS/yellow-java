package yellow.combat;

import arc.math.*;
import mindustry.game.*;
import mindustry.gen.*;


public class Parry{

    public static void cone(Team team, float x, float y, float radius, float angle, float cone, boolean resetLifetime, float lifetimeMultiplier, float speedMultiplier, boolean teamParry){
        Groups.bullet.intersect(x - radius, y - radius, radius*2f, radius*2f, bullet -> {
            float ang = Angles.angle(bullet.x, bullet.y, x, y);

            if((bullet.team != team || teamParry) && bullet.within(x, y, radius + bullet.hitSize/2f) && Angles.within(ang - 180, angle, cone)){
                bullet.team(team);

                if(resetLifetime){
                    bullet.time(0f);
                    bullet.lifetime(bullet.lifetime() * lifetimeMultiplier);
                }

                bullet.initVel(ang - 180, bullet.type().speed * speedMultiplier);
            }
        });
    }
}
