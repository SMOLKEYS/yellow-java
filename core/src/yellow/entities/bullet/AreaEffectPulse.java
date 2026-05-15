package yellow.entities.bullet;

import arc.graphics.*;
import arc.math.*;
import arc.util.*;
import arc.util.pooling.Pool.*;
import arc.util.pooling.*;
import mindustry.entities.*;
import mindustry.gen.*;
import mindustry.type.*;
import yellow.content.*;

public class AreaEffectPulse extends InstantDespawnBulletType{
    public AppliedAreaEffect[] effects = {};
    public Effect pulseEffect = YellowFx.areaEffectPulse;
    public float range = 8*8f;

    public AreaEffectPulse(){

    }

    public AreaEffectPulse(AppliedAreaEffect... effects){
        this.effects = effects;
    }

    public AreaEffectPulse(float range, AppliedAreaEffect... effects){
        this.range = range;
        this.effects = effects;
    }

    @Override
    public float calculateRange(){
        return range;
    }

    @Override
    public void init(Bullet b){
        super.init(b);

        AppliedAreaEffectData data = Pools.obtain(AppliedAreaEffectData.class, AppliedAreaEffectData::new);
        data.color = Structs.random(effects).effect.color;
        data.size = range;

        pulseEffect.at(b.x, b.y, b.rotation(), data);

        Units.nearbyEnemies(b.team, b.x, b.y, range, unit -> {
            for(AppliedAreaEffect e : effects) unit.apply(e.effect, Mathf.random(e.minTime, e.maxTime));
        });

        Time.run(65f, data::reset);
    }

    public static class AppliedAreaEffect{
        public StatusEffect effect;
        public float minTime = 60f, maxTime = 180f;

        public AppliedAreaEffect(StatusEffect effect){
            this.effect = effect;
        }

        public AppliedAreaEffect(StatusEffect effect, float time){
            this.effect = effect;
            this.minTime = this.maxTime = time;
        }

        public AppliedAreaEffect(StatusEffect effect, float minTime, float maxTime){
            this.effect = effect;
            this.minTime = minTime;
            this.maxTime = maxTime;
        }
    }

    public static class AppliedAreaEffectData implements Poolable{
        public Color color;
        public float size;

        @Override
        public void reset(){
            color = null;
            size = 0f;
        }
    }
}
