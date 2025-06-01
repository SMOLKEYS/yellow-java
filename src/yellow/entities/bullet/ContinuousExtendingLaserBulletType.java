package yellow.entities.bullet;

import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.util.*;
import mindustry.entities.*;
import mindustry.entities.bullet.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import yellow.equality.*;

public class ContinuousExtendingLaserBulletType extends ContinuousLaserBulletType{
    private static final Rand rand = new Rand(1934);


    /** If true, this laser will anchor to the shooter weapon properly. */
    public boolean anchor = true;
    /** If specified, this laser will start with zero length, then slowly reach the target {@link #length}. */
    public boolean grow = false;
    /** Delay before the laser starts growing. */
    public float growDelay;
    /** Random offset range for the grow delay. */
    public float growDelayRandRange;
    /** Time needed for the laser to reach its peak length, in ticks. */
    public float growTime;
    /** Interpolation used for laser growth. */
    public Interp interp = Interp.linear;
    /** If specified, this laser will slowly shrink back to zero length. */
    public boolean shrink = false;
    /** Delay before the laser begins shrinking. */
    public float shrinkDelay;
    /** Random offset range for the shrink delay. */
    public float shrinkDelayRandRange;
    /** Time needed for the laser to shrink back to zero length, in ticks. */
    public float shrinkTime;
    /** Interpolation used for laser shrinking. */
    public Interp shrinkInterp = Interp.smooth;

    public ContinuousExtendingLaserBulletType(float damage){
        super(damage);
    }

    public ContinuousExtendingLaserBulletType(){
        super();
    }

    @Override
    public void update(Bullet b){
        super.update(b);

        if(anchor && b.owner instanceof Position p) b.set(p);

    }

    @Override
    public void draw(Bullet b){
        //copypasted code with some changes, woe is thine

        rand.setSeed(b.id);
        float gRand = rand.range(growDelayRandRange);
        float sRand = rand.range(shrinkDelayRandRange);

        float fout = Mathf.clamp(b.time > b.lifetime - fadeTime ? 1f - (b.time - (lifetime - fadeTime)) / fadeTime : 1f);
        float tLength = length * fout;

        float gInterp = interp.apply(Mathf.clamp(Math.max(0, b.time - (growDelay + gRand)) / growTime));
        float sInterp = shrinkInterp.apply(Mathf.clamp(Math.max(0, b.time - (shrinkDelay + sRand)) / shrinkTime));

        //Log.info("@ gi, @ si, @ gtime-del, @ stime-del", gInterp, sInterp, b.time - growTime, b.time - shrinkDelay);

        float gLength = Mathf.lerp(0, tLength, gInterp);
        float sLength = Mathf.lerp(0, tLength, sInterp);
        float fLength = (grow ? gLength : tLength) - (shrink ? sLength : 0);

        float fgLength = Mathf.lerp(0, frontLength, gInterp);
        float fsLength = Mathf.lerp(0, frontLength, sInterp);
        float ffLength = (grow ? fgLength : frontLength) - (shrink ? fsLength : 0);

        float bgLength = Mathf.lerp(0, backLength, gInterp);
        float bsLength = Mathf.lerp(0, backLength, sInterp);
        float bfLength = (grow ? bgLength : backLength) - (shrink ? bsLength : 0);

        //Log.info("@ base, @ front, @ back, @ time, @ gdt, @ sdt, @ gdc, @ sdc", fLength, ffLength, bfLength, b.time, b.time - growDelay, b.time - shrinkDelay, b.time - growDelay / growTime, b.time - shrinkDelay / shrinkTime);

        float realLength = Damage.findLength(b, fLength, laserAbsorb, pierceCap);
        float rot = b.rotation();

        for(int i = 0; i < colors.length; i++){
            Draw.color(Tmp.c1.set(colors[i]).mul(1f + Mathf.absin(Time.time, 1f, 0.1f)));

            float colorFin = i / (float)(colors.length - 1);
            float baseStroke = Mathf.lerp(strokeFrom, strokeTo, colorFin);
            float stroke = (width + Mathf.absin(Time.time, oscScl, oscMag)) * fout * baseStroke;
            float ellipseLenScl = Mathf.lerp(1 - i / (float)(colors.length), 1f, pointyScaling);

            Lines.stroke(stroke);
            Lines.lineAngle(b.x, b.y, rot, realLength - ffLength, false);

            //back ellipse
            Drawf.flameFront(b.x, b.y, divisions, rot + 180f, bfLength, stroke / 2f);

            //front ellipse
            Tmp.v1.trnsExact(rot, realLength - ffLength);
            Drawf.flameFront(b.x + Tmp.v1.x, b.y + Tmp.v1.y, divisions, rot, ffLength * ellipseLenScl, stroke / 2f);
        }

        Tmp.v1.trns(b.rotation(), realLength * 1.1f);

        Drawf.light(b.x, b.y, b.x + Tmp.v1.x, b.y + Tmp.v1.y, lightStroke, lightColor, 0.7f);
        Draw.reset();
    }

    @Override
    public float currentLength(Bullet b){
        float tLength = super.currentLength(b);

        rand.setSeed(b.id);
        float gRand = rand.range(growDelayRandRange);
        float sRand = rand.range(shrinkDelayRandRange);

        float gInterp = interp.apply(Mathf.clamp(Math.max(0, b.time - (growDelay + gRand)) / growTime));
        float sInterp = shrinkInterp.apply(Mathf.clamp(Math.max(0, b.time - (shrinkDelay + sRand)) / shrinkTime));

        float gLength = Mathf.lerp(0, tLength, gInterp);
        float sLength = Mathf.lerp(0, tLength, sInterp);

        return (grow ? gLength : tLength) - (shrink ? sLength : 0);
    }

    @Override
    public void hitEntity(Bullet b, Hitboxc entity, float health){
        if(!EqualityDamage.isEnabled()){
            super.hitEntity(b, entity, health);
            return;
        }

        if(entity instanceof Unit u && EqualityDamage.isEnabled()) EqualityDamage.handle(u, b, health);
    }

    @Override
    public void createSplashDamage(Bullet b, float x, float y){
        if(!EqualityDamage.isEnabled()){
            super.createSplashDamage(b, x, y);
            return;
        }

        if(splashDamageRadius > 0 && !b.absorbed && EqualityDamage.isEnabled()){
            Units.nearbyEnemies(b.team, x, y, splashDamageRadius, en -> EqualityDamage.handle(en, b, en.health));
        }
    }
}
