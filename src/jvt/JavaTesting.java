package jvt;

import arc.*;
import arc.util.*;
import arc.util.Log.*;
import mindustry.*;
import mindustry.game.EventType.*;
import mindustry.gen.*;
import mindustry.mod.*;
import mindustry.ui.dialogs.*;
import jvt.content.*;

public class JavaTesting extends Mod{
    
    public JavaTesting(){
        Log.info("amogus");
        
        Events.on(ClientLoadEvent.class, e -> {
            BaseDialog sus = new BaseDialog("sussy baka");
            sus.cont.add("you have downloaded a curse\ndelete me now").row();
            sus.cont.button("haha no", sus::hide).size(300f, 250f);
            sus.show();
        });
    };
    
    private static final ContentList[] JvtContent = {
        new JvtUnitTypes()
    };
    
    @Override
    public void loadContent(){
        for(ContentList list : JvtContent){
            list.load();
        };
    }
    
}