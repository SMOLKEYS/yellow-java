package yellow;

import arc.*;
import arc.files.*;
import arc.func.*;
import arc.math.*;
import arc.scene.*;
import arc.scene.ui.*;
import mindustry.*;
import mindustry.game.*;
import mindustry.mod.*;
import yellow.combat.*;
import yellow.content.*;
import yellow.ui.*;
import yellow.util.*;

//third rewrite note: the more i do this the more i wanna throw my laptop against a wall
@SuppressWarnings({
        "unused", //oh and shut the fuck up java
        "GrazieInspection" //you too intellij
})
public class Yellow extends Mod{

    public static final boolean debug = YellowJVM.hasParameter("yellow-debug");

    public Yellow(){
        if(Vars.clientLoaded) YellowVars.onImport();

        Events.run(EventType.ClientLoadEvent.class, () -> {
            YellowVars.initUI();
            if(!Vars.mobile && SafeSettings.getBool("yellow-enable-rpc", true, true)) YellowRPC.init();
            YellowSettings.load();

            UpdateChecker.loadNotifier();
            JSLink.importPackage(
                    "yellow", "yellow.ui", "yellow.ui.fragments", "yellow.world.meta",
                    "yellow.ai", "yellow.content", "yellow.entities.units", "yellow.entities.effect",
                    "yellow.entities.units.entity", "yellow.io", "yellow.math", "yellow.type.weapons",
                    "yellow.util", "yellow.equality", "yellow.combat", "yellow.cutscene",
                    "yellow.cutscene.controllers"
            );

            Element e = Vars.ui.menuGroup.find("menu container");

            e.update(() -> {
                Dialog d = Core.scene.getDialog();

                if(d != null) e.color.a(Interp.reverse.apply(d.color.a));
            });
        });
    }

    public static Mods.ModMeta meta(){
        return Vars.mods.getMod(Yellow.class).meta;
    }

    public static Mods.LoadedMod mod(){
        return Vars.mods.getMod(Yellow.class);
    }

    public static Fi configDir(){
        Fi f = Core.settings.getDataDirectory().child("smol_common").child("yellow");
        f.mkdirs();
        return f;
    }

    @Override
    public void loadContent(){
        YellowUnitTypes.load();
    }
}
