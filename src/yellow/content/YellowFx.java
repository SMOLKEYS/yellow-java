package yellow.content;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.struct.*;
import arc.util.*;
import mindustry.entities.*;
import mindustry.game.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.world.Block;

import static arc.graphics.g2d.Draw.rect;
import static arc.graphics.g2d.Draw.*;
import static arc.graphics.g2d.Lines.*;
import static arc.math.Angles.*;
import static mindustry.Vars.*;
/**
 * All effects are sorted by oldest-top, newest-bottom.
 */
public class YellowFx{
    
    public static final Effect
    
    /** A circle effect that expands and slowly fades. Used by the bullethell attack. */
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
        });
        
        //endregion
}