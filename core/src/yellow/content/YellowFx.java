package yellow.content;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.graphics.*;
import yellow.entities.effect.*;
import yellow.math.*;


public class YellowFx{

    private static final Rand rand = new Rand(1997);
    
    public static final Effect ghostDespawn = new Effect(25f, e -> {
        Lines.stroke(e.fout() * 7);
        Lines.circle(e.x, e.y, e.fin() * 10);
    });

    public static final Effect ghostDespawn2 = new Effect(29f, e -> {
        Lines.stroke(e.fout() * 7);
        Lines.circle(e.x, e.y, e.fin() * 17);
    });

    public static final Effect ghostDespawn3 = new Effect(38f, e -> {
        Lines.stroke(e.fout() * 3);
        Lines.circle(e.x, e.y, e.fin() * 23);
        Angles.randLenVectors(e.id, 5, e.fin() * 25f, (rx, ry) -> {
            Lines.line(e.x, e.y, e.x + rx, e.y + ry);
            Fill.circle(e.x + rx, e.y + ry, e.fout() * 5);
        });
    });

    public static final Effect ghostDespawn4 = new Effect(45f, e -> {
        Lines.stroke(e.fout() * 5);
        Lines.circle(e.x, e.y, e.fin() * 15);
        Angles.randLenVectors(e.id, 5, e.fin() * 33f, (rx, ry) -> {
            Lines.line(e.x, e.y, e.x + rx, e.y + ry);
            Fill.circle(e.x + rx, e.y + ry, e.fout() * 6);
        });
    });

    public static final Effect ghostDespawnMulti = new RandomEffect(ghostDespawn, ghostDespawn2, ghostDespawn3, ghostDespawn4);

    //TODO: remake
    public static final Effect decimatorPortalExplosion = new Effect(180f, e -> {
        float sz = e.fin(InterpStack.pow10OutSlope);

        Lines.stroke(sz * 15);
        Draw.color(Color.yellow);

        Angles.randLenVectors(e.id, 25, e.finpow() * 240, (x, y) -> Fill.circle(e.x + x, e.y + y, e.fout(Interp.pow3Out) * 15));

        Mathf.rand.setSeed(e.id);

        Angles.randLenVectors(e.id, 20, sz * 70, -Time.time * 2.3f, 360f, (rx, ry) -> {
            Draw.z(Layer.effect - 0.001f);
            Draw.color(Tmp.c1.set(Color.purple).lerp(Color.violet, Mathf.random()));
            Fill.circle(e.x + rx, e.y + ry, sz * Mathf.random(24, 37));
        });

        Draw.z(Layer.effect - 0.002f);
        Draw.color(Tmp.c1.set(Color.purple).lerp(Color.violet, Mathf.random()));
        Fill.circle(e.x, e.y, sz * 85);
    });

    public static final Effect yellowDeathEffect = new Effect(210f, e -> {
        Draw.color(Color.yellow, Color.orange, e.finpow());
        
        Lines.stroke(e.fout() * 5);
        Lines.square(e.x, e.y, e.finpow() * 90, e.finpow() * 180);
        Lines.square(e.x, e.y, e.finpow() * 90, e.foutpow() * 180);
        Lines.circle(e.x, e.y, e.finpow() * 130);
        Lines.circle(e.x, e.y, e.finpow() * 120);
        Lines.circle(e.x, e.y, e.finpow() * 110);

        Angles.randLenVectors(e.id, 50, e.finpow() * 100, (x, y) -> {
            Lines.stroke(1);
            Draw.color(Color.yellow, Color.orange, e.finpow());
            Fill.circle(e.x + x, e.y + y, e.fout() * 10);
        });

        Draw.alpha(e.fout() * 4);
        Draw.rect("yellow-java-yellow", e.x, e.y, e.finpow() * 200, e.finpow() * 200);
    });

    public static final Effect despawn = new Effect(120f, e -> {
        Lines.stroke(e.fout() * 6f);
        
        Draw.color(Color.yellow, Color.white, e.fin());
        Lines.line(e.x, e.y - 2000f, e.x, e.y + 2000f);
    });

    public static final Effect fireCircle = new Effect(180f, e -> {
        Draw.z(Layer.effect);
        Lines.stroke(e.fout() * 40);

        Draw.color(Pal.lightFlame, Color.orange, e.fin());
        Lines.circle(e.x, e.y, e.finpow() * 8*22);

        Angles.randLenVectors(e.id, 40, e.finpow() * 200, (x, y) -> {
            Fx.fire.at(e.x + x, e.y + y);
        });

        Angles.randLenVectors(e.id, 100, e.finpow() * 280, (x, y) -> {
            Draw.color(Color.gray);
            Fill.circle(e.x + x, e.y + y, e.foutpow() * 30);
        });
    });

    public static final Effect energySphereExplosion = new Effect(120f, e -> {
        Draw.z(Layer.effect);
        Draw.color(Pal.lancerLaser);

        Lines.stroke(e.fout() * 10);
        Lines.circle(e.x, e.y, e.finpow() * 8*15);

        Angles.randLenVectors(e.id, 10, e.finpow() * 8*30, (x, y) -> {
            Lines.line(e.x, e.y, e.x + x, e.y + y);
            Fill.circle(e.x + x, e.y + y, e.fout() * 20);
        });
    });
}
