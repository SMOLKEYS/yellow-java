package yellow.entities.bullet;

import arc.util.*;
import arc.math.*;
import mindustry.entities.bullet.*;
import mindustry.entities.bullet.BulletType.*;
import mindustry.entities.bullet.ArtilleryBulletType;
import mindustry.gen.*;
import yellow.content.*;

public class AirstrikeFlare extends ArtilleryBulletType{
    //The bullet that the flare can call.
    public BulletType missile = YellowBullets.standardMissile;
    //The total amount of missiles that will be called.
    public int missileCount = 8;
    //The minimum amount of missiles that will be called. Does nothing if randomizeMissileCount is set to false.
    public int minMissileCount = 4;
    //The total time it takes for the missiles to arrive.
    public float arrivalDelay = 60f * 4f;
    //If randomizeArrivalDelays is set to true, then the missiles will be called at random intervals. The interval randomization range is set by the arrivalDelay variable and this variable. Does nothing if randomizeArrivalDelays is set to false.
    public float minArrivalDelay = 60f * 1f;
    //Missile lifetime randomization.
    public float missileLifetimeRandomization = 3f;
    //Random offset distance from the original coordinate the flare hit.
    public float posRandomization = 8f * 15f;
    //Whether or not to randomize the amount of missiles called.
    public boolean randomizeMissileCount = true;
    //Whether or not to randomize missile call times.
    public boolean randomizeArrivalDelays = true;
    /**
     * TODO sus
    public Bullet susMissile = YellowBullets.amogMissile;
    */
    
    public AirstrikeFlare(){
        shrinkX = 0f;
        shrinkY = 0f;
        lifetime = 10f;
        speed = 20f;
        collides = true;
        collidesAir = true;
        collidesGround = true;
    }
    
    @Override
    public void hit(Bullet b){
        Log.info("hit before super");
        super.hit(b);
        Log.info("hit after super");
        for(int i = 0; i < missileCount; i++){
            Time.run(Mathf.random(missileLifetimeRandomization), () -> {
                BulletType.createBullet(missile, b.team, b.x + Mathf.range(posRandomization), b.y + Mathf.range(posRandomization), 0f, 350f, 0f, 1f + Mathf.random(missileLifetimeRandomization));
                Log.info("bullet spawn");
            });
        };
    }
}
