package yellow.graphics;

import arc.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.util.*;
import mindustry.graphics.*;

import static yellow.YellowSettingValues.*;

public class YellowMenuRenderer extends MenuRenderer{
    private final Rand rand = new Rand();
    private final Vec2 vec = new Vec2();

    public YellowMenuRenderer(){
        rendererTextures.add(Core.atlas.getRegions());
    }

    @Override
    public void render(){
        if(rendererEnableOriginalMenu.get()) super.render();

        rand.setSeed(rendererSeed.get());

        if(rendererTextures.isEmpty()) return;

        for(int i = 0; i < rendererTextureCount.get(); i++){
            TextureRegion reg = rendererTextures.random(rand);
            vec.set(rand.random(Core.graphics.getWidth()), rand.random(Core.graphics.getHeight()));
            float ofs = reg.width * div10(rendererOutbound.get());
            float v = Time.time * rand.random(div10(rendererMinSpeed.get()), div10(rendererMaxSpeed.get()));
            float r = Time.time * rand.random(div10(rendererMinRotSpeed.get()), div10(rendererMaxRotSpeed.get()));
            boolean b = rendererReverse.get() && rand.chance(0.5);
            boolean br = reverseRotation.get() && rand.chance(0.5);

            float
                    vx = (b ? Core.graphics.getWidth() : 0f) + (ofs - vec.x + (b ? -v : v)) % (Core.graphics.getWidth() + ofs),
                    vy = vec.y;

            Draw.rect(
                    reg,
                    vx,
                    vy,
                    reg.width * reg.scl(),
                    reg.height * reg.scl(),
                    br ? -r : r
            );
        }
    }

    private static float div10(float f){
        return f / 10f;
    }


    public static class ChaosRendererConfig{
        ;

    }
}
