package yellow;

import arc.*;
import arc.struct.*;
import mindustry.*;
import mindustry.mod.*;
import yellow.entities.units.entity.*;
import yellow.internal.*;
import yellow.internal.util.*;

public class YellowVars{
    
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
