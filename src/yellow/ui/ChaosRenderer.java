package yellow.ui;

import arc.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.struct.*;
import arc.util.*;
import mindustry.graphics.*;
import mindustry.io.*;
import yellow.*;
import yellow.util.*;

public class ChaosRenderer extends MenuRenderer{
    private final Rand rand = new Rand();
    private final Vec2 vec = new Vec2();
    public ChaosRendererConfig config;

    public ChaosRenderer(){
        config = new ChaosRendererConfig(){{
            textures.add(Core.atlas.getRegions());
        }};
    }

    @Override
    public void render(){
        if(config.renderOriginalMenu) super.render();

        rand.setSeed(config.seed);

        if(config.textures.isEmpty()) return;

        for(int i = 0; i < config.count; i++){
            TextureRegion reg = config.textures.random(rand);
            vec.set(rand.random(Core.graphics.getWidth()), rand.random(Core.graphics.getHeight()));
            float ofs = reg.width * config.outbound;
            float v = Time.time * rand.random(config.minSpeed, config.maxSpeed);
            float r = Time.time * rand.random(config.minRotSpeed, config.maxRotSpeed);
            boolean b = config.reverse && rand.chance(0.5);
            boolean br = config.reverseRotation && rand.chance(0.5);

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


    public static class ChaosRendererConfig{
        /** seed, can be changed directly or with {@link #shuffle()} */
        public int seed = Mathf.random(999999);
        /** amount of sprites flying over at once */
        public int count = 450;
        /** textures to be used, typically all the vanilla atlases ({@code Core.atlas.getRegions()}). can be empty */
        public Seq<TextureRegion> textures = new Seq<>();
        /** extra "out of bounds" offset so sprites don't teleport from one side of the screen to the other */
        public float outbound = 2.5f;
        /** minimum and maximum speed the sprites move at. can be disorienting at high values */
        public float minSpeed = 0.5f, maxSpeed = 2f;
        /** minimum and maximum rotation speed for each flying sprite */
        public float minRotSpeed = 0.5f, maxRotSpeed = 2f;
        /** whether the sprites should go and/or rotate the other way */
        public boolean reverse, reverseRotation;
        /** whether the original menu renderer appears *below* the flying sprites */
        public boolean renderOriginalMenu;

        public void load(){
            seed = SafeSettings.getInt(p("seed"), 19987, Mathf.random(999999));
            count = SafeSettings.getInt(p("tex-count"), 450);
            outbound = SafeSettings.getFloat(p("outbound"), 2.5f);
            minSpeed = SafeSettings.getFloat(p("min-speed"), 0.5f);
            maxSpeed = SafeSettings.getFloat(p("max-speed"), 2f);
            minRotSpeed = SafeSettings.getFloat(p("min-rot-speed"), 0.5f);
            maxRotSpeed = SafeSettings.getFloat(p("max-rot-speed"), 2f);
            reverse = SafeSettings.getBool(p("reverse"), false);
            reverseRotation = SafeSettings.getBool(p("reverse-rot"), true);
            renderOriginalMenu = SafeSettings.getBool(p("render-original-renderer"), false);
        }

        public void shuffle(){
            seed = Mathf.random(999999);
        }

        private String p(String n){
            String r = "yellow-chaos-renderer-";
            return r + n;
        }
    }
}
