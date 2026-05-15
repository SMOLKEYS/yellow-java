package yellow;

import arc.*;
import arc.files.*;
import arc.struct.*;
import arc.util.*;
import mindustry.*;
import mindustry.game.EventType.*;
import mindustry.mod.*;
import mindustry.mod.Mods.*;
import yellow.content.*;
import yellow.entities.*;
import yellow.extras.*;
import yellow.gen.*;
import yellow.graphics.*;
import yellow.js.*;
import yellow.ui.*;

import java.io.*;
import java.util.*;

import static yellow.YellowSettingValues.*;

@SuppressWarnings({"unused", "SpellCheckingInspection"})
public class Yellow extends Mod{

    public static final Seq<ExtraAddition> extras = new Seq<>();

    public static final boolean debug = YellowJVM.hasParameter("yellow-debug", () -> Objects.equals(System.getenv("YELLOW_HAKAMAKADAJAKA"), "fumo"), str -> {
        Log.infoTag(str, "Yellow debug mode enabled.");
        YellowDebug.loadDebug();
    });

    public Yellow(){
        if(Vars.clientLoaded) YellowVars.onImport();

        if(!Vars.clientLoaded){
            BaseExtras.load();
            YellowVars.initNatives();
            YellowGroups.init();
            YellowVars.preInit();
        }

        Events.run(ClientLoadEvent.class, () -> {
            YellowVars.init();
            YellowGraphics.init();
            YellowSettings.load();
            YellowFonts.load();
            YellowStyles.load();
            YellowGameStyles.load();
            YellowCodes.load();
            Rhinor.importMainModPackages(this);
            Fi c = Core.files.cache("yellow-exports");
            if(c.exists()) c.emptyDirectory();

            if(!Vars.mobile && enableRpc.get()) YellowRPC.init();

            if(enableAutoupdate.get()) UpdateChecker.loadNotifier();
        });
    }

    public static ModMeta meta(){
        return Vars.mods.getMod(Yellow.class).meta;
    }

    public static LoadedMod mod(){
        return Vars.mods.getMod(Yellow.class);
    }

    /** Loads a file from Yellow's JAR archive to a cache file and returns that file.
     * Mostly for accessing internal files during the constructor initialization phase. */
    public static Fi file(String path){
        try(InputStream stream = Yellow.class.getResourceAsStream(path)){
            Fi child = Core.files.cache("yellow-exports").child(String.valueOf(System.currentTimeMillis()));
            child.write(stream, false);
            return child;
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public static Fi configDir(){
        Fi f = Core.settings.getDataDirectory().child("smol_common").child("yellow");
        f.mkdirs();
        return f;
    }

    @Override
    public void loadContent(){
        EntityRegistry.register();
        YellowSounds.load();
        YellowWeapons.load();
        YellowUnitTypes.load();
        YellowWeapons.afterLoad();
        Yellow.extras.each(ExtraAddition::loadContent);
    }

    public static class ExtraAddition{

        public ExtraAddition(){

        }

        public void varsPreInit(){

        }

        public void varsInit(){

        }

        public void onModImport(){

        }

        public void loadContent(){

        }
    }
}
