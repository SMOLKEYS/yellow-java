package yellow.content;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.*;
import mindustry.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.graphics.*;
import mindustry.world.*;
import yellow.entities.effect.*;


public class YellowFx{
    
    public static final Effect

    ghostDespawn = new Effect(10f, e -> {
        Draw.z(Layer.effect);
        Draw.alpha(e.fout() * 3);
        
        Lines.stroke(e.fout() * 7);
        Lines.circle(e.x, e.y, e.fin() * 10);
    }),

    ghostDespawn2 = new Effect(15f, e -> {
        Draw.z(Layer.effect);
        Draw.alpha(e.fout() * 3);

        Lines.stroke(e.fout() * 7);
        Lines.circle(e.x, e.y, e.fin() * 17);
    }),

    ghostDespawn3 = new Effect(15f, e -> {
        Draw.z(Layer.effect);
        Draw.alpha(e.fout() * 3);

        Lines.stroke(e.fout() * 7);
        Lines.circle(e.x, e.y, e.fin() * 23);
    }),

    ghostDespawnMulti = new RandomEffect(ghostDespawn, ghostDespawn2, ghostDespawn3),
    
    /** Outward explosion effect.
     * (<- center ->)
     * The magic explosion is coming for you.
     */
    yellowExplosionOut = new Effect(120f, e -> {
        Draw.color(Color.yellow);

        float h = e.fin(Interp.smooth);
        
        Lines.stroke(e.fout() * 15);
        Lines.circle(e.x, e.y, h * 25);
        Lines.square(e.x, e.y, h * 50, Time.time * 9);
        Lines.circle(e.x, e.y, h * 50);
        Lines.square(e.x, e.y, h * 100, Time.time * 9);
        Lines.circle(e.x, e.y, h * 25);
        Lines.square(e.x, e.y, h * 50, -Time.time * 9);
        Lines.circle(e.x, e.y, h * 50);
        Lines.square(e.x, e.y, h * 100, -Time.time * 9);
    }),
    
    /** Inward explosion effect. 
     * (-> center <-)
     * Not good realism-wise, but meh, who cares?
     */
    yellowExplosionIn = new Effect(120f, e -> {
        Draw.color(Color.yellow);
        
        Lines.stroke(e.fin() * 15);
        Lines.circle(e.x, e.y, e.fout() * 25);
        Lines.square(e.x, e.y, e.fout() * 50, Time.time * 9);
        Lines.circle(e.x, e.y, e.fout() * 50);
        Lines.square(e.x, e.y, e.fout() * 100, Time.time * 9);
        Lines.circle(e.x, e.y, e.fout() * 25);
        Lines.square(e.x, e.y, e.fout() * 50, -Time.time * 9);
        Lines.circle(e.x, e.y, e.fout() * 50);
        Lines.square(e.x, e.y, e.fout() * 100, -Time.time * 9);
    }),
    
    /** clEan! */
    yellowDeathEffect = new Effect(210f, e -> {
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
    }),
    
    bullethellShootEffect = new Effect(60f, e -> {
        Draw.z(Layer.effect + 0.001f);
        Draw.color(Color.white);
        Draw.alpha(e.fout());
        
        Lines.square(e.x, e.y, 40, Time.time * 6);
        Lines.square(e.x, e.y, 40, -Time.time * 6);
        Lines.square(e.x, e.y, 80, Time.time * 6);
        Lines.square(e.x, e.y, 80, -Time.time * 6);
        
        Lines.stroke(10);
        Lines.poly(e.x, e.y, 3, e.fin(Interp.pow5Out) * 130, Time.time * 6);
        Lines.poly(e.x, e.y, 3, e.fin(Interp.pow5Out) * 130, Time.time * 6 - 180);
    }),
    
    despawn = new Effect(120f, e -> {
        Lines.stroke(e.fout() * 6f);
        
        Draw.color(Color.yellow, Color.white, e.fin());
        Lines.line(e.x, e.y - 2000f, e.x, e.y + 2000f);
    }),


    fireCircleEffect = new Effect(180f, e -> {
        Draw.z(Layer.effect);
        Lines.stroke(e.fout() * 40);

        Draw.color(Pal.lightFlame, Color.orange, e.fin());
        Lines.circle(e.x, e.y, e.fin() * 8*22);

        Angles.randLenVectors(e.id, 40, e.finpow() * 200, (x, y) -> {
            Fx.fire.at(e.x + x, e.y + y);
        });

        Angles.randLenVectors(e.id, 100, e.finpow() * 280, (x, y) -> {
            Draw.color(Color.gray);
            Fill.circle(e.x + x, e.y + y, e.foutpow() * 30);
        });
    }),

    groundBreaker = new Effect(180f, e -> {
        Tile t = Vars.world.tile((int) (e.x / 8), (int) (e.y / 8));
        if(t != null && t.floor() != Blocks.empty.asFloor()){
            Mathf.rand.setSeed(e.id);

            Angles.randLenVectors(e.id, 1, e.fin(Interp.pow10Out) * Mathf.random(70, 150), e.rotation + Mathf.random(20, 40), 30, (x, y) -> {
                Draw.rect(t.floor().region, e.x + x, e.y + y, Mathf.random(360));
            });
        }
    });
    
    
    //endregion
}
