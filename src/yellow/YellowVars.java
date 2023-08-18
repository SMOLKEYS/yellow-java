package yellow;

import arc.*;
import mindustry.mod.*;
import yellow.internal.*;
import yellow.internal.util.*;

public class YellowVars{

    public static void load(){
        Mods.ModMeta meta = Yellow.getSelf().meta;

        MetaChaos.load();

        meta.subtitle = MetaChaos.getSubtitles().random();
        meta.description = MetaChaos.getDescriptions().random();

        YellowUtils.loop(1f, () -> {
            meta.subtitle = MetaChaos.getSubtitles().random();
            meta.description = MetaChaos.getDescriptions().random();
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
