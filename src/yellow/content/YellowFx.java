package yellow.content;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.math.Angles;
import arc.math.Interp;
import arc.util.Time;
import mindustry.entities.Effect;
import mindustry.graphics.Layer;

/**
 * All effects are sorted by oldest-top, newest-bottom.
 */
public class YellowFx{
    
    public static final Effect
    
    /** A circle effect that expands and quickly fades away. */
    ghostDespawn = new Effect(10f, e -> {
        Draw.z(Layer.effect);
        Draw.alpha(e.fout() * 3);
        
        Lines.stroke(e.fout() * 7);
        Lines.circle(e.x, e.y, e.fin() * 10);
    }),
    
    /** Inward and outward explosion effect.
     * (-> center <-) and (<- center ->) at the same time
     * Mercy to anyone with a low-end device.
     */
    yellowExplosionOutIn = new Effect(120f, e -> {
        Draw.color(Color.yellow);
        
        Lines.stroke(e.fout() * 15);
        Lines.circle(e.x, e.y, e.fin() * 25);
        Lines.square(e.x, e.y, e.fin() * 50, Time.time * 7);
        Lines.circle(e.x, e.y, e.fin() * 50);
        Lines.square(e.x, e.y, e.fin() * 100, Time.time * 7);
        Lines.circle(e.x, e.y, e.fin() * 25);
        Lines.square(e.x, e.y, e.fin() * 50, -Time.time * 7);
        Lines.circle(e.x, e.y, e.fin() * 50);
        Lines.square(e.x, e.y, e.fin() * 100, -Time.time * 7);
        Lines.stroke(e.fin() * 15);
        Lines.circle(e.x, e.y, e.fout() * 50);
        Lines.square(e.x, e.y, e.fout() * 100, Time.time * 7);
        Lines.circle(e.x, e.y, e.fout() * 75);
        Lines.square(e.x, e.y, e.fout() * 150, -Time.time * 7);
    }),
    
    /** Outward explosion effect.
     * (<- center ->)
     * The magic explosion is coming for you.
     */
    yellowExplosionOut = new Effect(120f, e -> {
        Draw.color(Color.yellow);
        
        Lines.stroke(e.fout() * 15);
        Lines.circle(e.x, e.y, e.fin() * 25);
        Lines.square(e.x, e.y, e.fin() * 50, Time.time * 9);
        Lines.circle(e.x, e.y, e.fin() * 50);
        Lines.square(e.x, e.y, e.fin() * 100, Time.time * 9);
        Lines.circle(e.x, e.y, e.fin() * 25);
        Lines.square(e.x, e.y, e.fin() * 50, -Time.time * 9);
        Lines.circle(e.x, e.y, e.fin() * 50);
        Lines.square(e.x, e.y, e.fin() * 100, -Time.time * 9);
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
    });
        
        //endregion
}
