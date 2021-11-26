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
    });
    
}