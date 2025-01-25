package yellow.graphics;

import arc.graphics.g2d.*;
import arc.util.*;
import mindustry.graphics.*;

public class Drawy{

    //on that betamindy pack fr
    public static void portalRing(float x, float y, float radius, float sizeScl, int branches, float offset){
        Lines.circle(x, y, radius);

        //using visual effects from other mods feels very wrong to me
        for(int i = 0; i < branches; i++){
            Tmp.v1.trns(i * 360f / branches - Time.time / 2f + offset, radius - 2f * sizeScl).add(x, y);
            Drawf.tri(Tmp.v1.x, Tmp.v1.y, Math.min(radius, 12f * sizeScl), 120f * sizeScl, i * 360f / branches - Time.time / 2f + 100f + offset);
        }

        for(int i = 0; i < branches; i++){
            Tmp.v1.trns(i * 360f / branches - Time.time / 3f + offset, radius - 4f * sizeScl).add(x, y);
            Drawf.tri(Tmp.v1.x, Tmp.v1.y, Math.min(radius, 16f * sizeScl), 160f * sizeScl, i * 360f / branches - Time.time / 3f + 110f + offset);
        }
    }
}
