package yellow.graphics;

import arc.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.struct.*;
import arc.util.*;
import mindustry.graphics.*;
import yellow.util.*;

public class ChaosRenderer extends MenuRenderer{
    private final Rand rand = new Rand();
    private final Vec2 vec = new Vec2();
    private final Interval interval = new Interval();

    private float alpha = 1f;

    public ChaosRendererConfig config;

    public ChaosRenderer(){
        config = new ChaosRendererConfig(){{
            textures.add(Core.atlas.getRegions());
        }};
    }

    @Override
    public void render(){
        if(config.renderOriginalMenu.get()) super.render();

        rand.setSeed(config.seed.get());

        if(config.textures.isEmpty()) return;

        for(int i = 0; i < config.count.get(); i++){
            TextureRegion reg = config.textures.random(rand);
            vec.set(rand.random(Core.graphics.getWidth()), rand.random(Core.graphics.getHeight()));
            float ofs = reg.width * div10(config.outbound.get());
            float v = Time.time * rand.random(div10(config.minSpeed.get()), div10(config.maxSpeed.get()));
            float r = Time.time * rand.random(div10(config.minRotSpeed.get()), div10(config.maxRotSpeed.get()));
            boolean b = config.reverse.get() && rand.chance(0.5);
            boolean br = config.reverseRotation.get() && rand.chance(0.5);

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
        /** seed, can be changed directly or with {@link #shuffle()} */
        public SettingBoundVariable<Integer> seed = new SettingBoundVariable<>(p("seed"), 1923).set(Mathf.random(999999));
        /** whether the textures shuffle every set amount of seconds */
        public SettingBoundVariable<Boolean> shuffleAuto = new SettingBoundVariable<>(p("shuffle-auto"), false);
        /** amount of sprites flying over at once */
        public SettingBoundVariable<Integer> count = new SettingBoundVariable<>(p("tex-count"), 450);;
        /** textures to be used, typically all the vanilla atlases ({@code Core.atlas.getRegions()}). can be empty */
        public Seq<TextureRegion> textures = new Seq<>();
        /** extra "out of bounds" offset so sprites don't visibly teleport from one side of the screen to the other */
        public SettingBoundVariable<Float> outbound = new SettingBoundVariable<>(p("outbound"), 2.5f);
        /** minimum and maximum speed the sprites move at. can be disorienting at high values */
        public SettingBoundVariable<Float> minSpeed = new SettingBoundVariable<>(p("min-speed"), 0.5f), maxSpeed = new SettingBoundVariable<>(p("max-speed"), 2f);
        /** minimum and maximum rotation speed for each flying sprite */
        public SettingBoundVariable<Float> minRotSpeed = new SettingBoundVariable<>(p("min-rot-speed"), 0.5f), maxRotSpeed = new SettingBoundVariable<>(p("max-rot-speed"), 2f);
        /** whether the sprites should go and/or rotate the other way */
        public SettingBoundVariable<Boolean> reverse = new SettingBoundVariable<>(p("reverse"), false), reverseRotation = new SettingBoundVariable<>(p("reverse-rot"), true);
        /** whether the original menu renderer appears <em>below</em> the flying sprites */
        public SettingBoundVariable<Boolean> renderOriginalMenu = new SettingBoundVariable<>(p("render-original-renderer"), false);

        public void shuffle(){
            seed.set(Mathf.random(999999));
        }

        private String p(String n){
            String r = "yellow-chaos-renderer-";
            return r + n;
        }
    }
}
