package yellow;

import arc.graphics.g2d.*;
import arc.math.*;
import arc.struct.*;
import yellow.util.SettingBoundVariable.*;

public class YellowSettingValues{

    // region misc

    public static final BooleanSetting
            enableRpc = new BooleanSetting(yellow("enable-rpc"), false),
            enableAutoupdate = new BooleanSetting(yellow("check-for-updates"), false),
            enableUnitDrops = new BooleanSetting(yellow("enable-unit-drops"), false),
            enableBuildDrops = new BooleanSetting(yellow("enable-build-drops"), false),
            enableBuildInventoryDrops = new BooleanSetting(yellow("enable-build-inv-drops"), false);

    // endregion misc

    // region menu renderer

    /** seed, can be changed directly or with {@link #shuffleChaosRenderer()} */
    public static final IntSetting rendererSeed = new IntSetting(chaosRend("seed"), 1937);
    /** whether the textures shuffle every set amount of seconds */
    public static final BooleanSetting rendererShuffleAuto = new BooleanSetting(chaosRend("shuffle-auto"), false);
    /** amount of sprites flying over at once */
    public static final IntSetting rendererTextureCount = new IntSetting(chaosRend("tex-count"), 450);
    /** textures to be used, typically all the vanilla atlases ({@code Core.atlas.getRegions()}). can be empty */
    public static final Seq<TextureRegion> rendererTextures = new Seq<>();
    /** extra "out of bounds" offset so sprites don't visibly teleport from one side of the screen to the other */
    public static final FloatSetting rendererOutbound = new FloatSetting(chaosRend("outbound"), 2.5f);
    /** minimum and maximum speed the sprites move at. can be disorienting at high values */
    public static final FloatSetting rendererMinSpeed = new FloatSetting(chaosRend("min-speed"), 0.5f), rendererMaxSpeed = new FloatSetting(chaosRend("max-speed"), 2f);
    /** minimum and maximum rotation speed for each flying sprite */
    public static final FloatSetting rendererMinRotSpeed = new FloatSetting(chaosRend("min-rot-speed"), 0.5f), rendererMaxRotSpeed = new FloatSetting(chaosRend("max-rot-speed"), 2f);
    /** whether the sprites should go and/or rotate the other way */
    public static final BooleanSetting rendererReverse = new BooleanSetting(chaosRend("reverse"), false), reverseRotation = new BooleanSetting(chaosRend("reverse-rot"), true);
    /** whether the original menu renderer appears <em>below</em> the flying sprites */
    public static final BooleanSetting rendererEnableOriginalMenu = new BooleanSetting(chaosRend("render-original-renderer"), false);

    // endregion menu renderer

    public static void shuffleChaosRenderer(){
        rendererSeed.set(Mathf.random(999999));
    }

    private static String chaosRend(String n){
        return "yellow-chaos-renderer-" + n;
    }

    private static String yellow(String n){
        return "yellow-" + n;
    }
}
