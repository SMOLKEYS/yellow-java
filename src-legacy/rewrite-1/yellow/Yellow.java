package yellow;

import arc.*;
import arc.files.*;
import arc.struct.*;
import arc.util.*;
import mindustry.*;
import mindustry.core.*;
import mindustry.game.EventType.*;
import mindustry.mod.*;

public class Yellow extends Mod{

    static boolean debug = false, bypassFailsafe = false, crashed = false;

    static int foundExtensions, loadedExtensions, erroredExtensions;
    static Seq<ErroneousExtension> erroredExtensionList = new Seq<>();

    public Yellow(){
        Events.fire(new YellowPreInitializationEvent());
        debug = YellowJVM.hasParameter("yellow-debug", s -> Log.infoTag("YellowJVM", "'" + s +"' parameter detected. Debug mode is now active."));
        bypassFailsafe = YellowJVM.hasParameter("yellow-nofailsafe", s -> Log.infoTag("YellowJVM", "Yellow failsafe bypassed!"));

        if(launchFile().exists() && Core.settings.getBool("yellow-enable-failsafe", true) && !bypassFailsafe){
            crashed = true;
            launchFile().delete();
            Events.on(ClientLoadEvent.class, e -> Vars.ui.showInfo("@yellow.initfailed"));
            return;
        }

        if(!Vars.clientLoaded){
            launchFile().writeString("go away");
        }else{
            YellowVars.onImport();
            Time.run(30f, YellowVars::onLaterImport);
        }

        YellowVars.extensionDir.mkdirs();
        YellowVars.extensionDir.walk(f -> {
            Log.info("Registration: @", f.name());
            foundExtensions++;

            try{
                ExtensionMeta ext = ExtensionCore.load(f);
                Log.info("Construction: @ (@)", ext.displayName, f.name());
                loadedExtensions++;
            }catch(Exception e){
                Log.err("Constructor for extension " + f.name() + " failed to load.", e);
                erroredExtensionList.add(new ErroneousExtension(f, e));
                erroredExtensions++;
            }
        });

        YellowVars.init();
    }

    public static Mods.ModMeta meta(){
        return Vars.mods.getMod(Yellow.class).meta;
    }

    public static Mods.LoadedMod mod(){
        return Vars.mods.getMod(Yellow.class);
    }

    public static Fi launchFile(){
        return configDir().child("yellow_launchid.dat");
    }

    public static Fi configDir(){
        Fi f = Core.settings.getDataDirectory().child("smol_common").child("yellow");
        f.mkdirs();
        return f;
    }

    @Override
    public void init(){
        extensions.each(s -> {
            if(s.meta.enabled()){
                Log.info("Initialization: @", s.meta.displayName);
                s.main.init();
            }
        });
    }

    @Override
    public void loadContent(){
        if(crashed) return;
        YellowDebug.info("Loading Yellow...");
        YellowWeapons.load();
        YellowSpells.load();
        YellowCharacters.load();
        YellowUnitTypes.load();
        YellowWeapons.afterLoad();

        KeyBinds.KeyBind[] kbArr = Core.keybinds.getKeybinds();
        KeyBinds.KeyBind[] okbArr = new KeyBinds.KeyBind[SpellBinding.inited.size];

        for(int i = 0; i < okbArr.length; i++){
            okbArr[i] = SpellBinding.inited.get(i);
        }

        Core.keybinds.setDefaults(Structsy.mergeArray(KeyBinds.KeyBind.class, kbArr, okbArr));

        extensions.each(s -> {
            try{
                if(s.meta.enabled()){
                    Log.info("Content Load: @", s.meta.displayName);
                    s.main.loadContent();
                }
            }catch(Exception e){
                throw new RuntimeException("Extension " + s.name + " contains content that failed to load.", e);
            }
        });

        Events.fire(new YellowContentInitEvent());
        YellowDebug.info("Yellow loaded!");
    }
}
