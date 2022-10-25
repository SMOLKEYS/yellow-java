package yellow;

import arc.struct.Seq;
import mindustry.Vars;
import mindustry.mod.Mods;
import yellow.entities.units.entity.YellowUnitEntity;
import yellow.internal.MetaChaos;
import yellow.internal.util.YellowUtils;

public class YellowVars{
    
    public static Seq<YellowUnitEntity> entities = new Seq<YellowUnitEntity>();
    public static Mods.ModMeta meta;

    public static void load(){
        meta = Vars.mods.getMod("yellow-java").meta;

        MetaChaos locs = new MetaChaos();

        meta.subtitle = locs.getSubtitles().random();
        meta.description = locs.getDescriptions().random();

        YellowUtils.loop(1f, () -> {
            meta.subtitle = locs.getSubtitles().random();
            meta.description = locs.getDescriptions().random();
        });
    }
}