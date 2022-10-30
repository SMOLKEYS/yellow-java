package yellow;

import arc.ApplicationListener;
import arc.Core;
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


        if(YellowPermVars.INSTANCE.getTemporary()){
            Core.app.addListener(new ApplicationListener(){
                @Override
                public void exit(){
                    Yellow.getSelf().file.delete();
                }
            });
        }
    }
}