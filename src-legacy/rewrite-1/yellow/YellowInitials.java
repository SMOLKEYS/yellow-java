package yellow;

import arc.*;
import arc.files.*;
import arc.graphics.*;
import arc.scene.event.*;
import arc.scene.style.*;
import arc.scene.ui.*;
import arc.scene.ui.layout.*;
import arc.struct.*;
import arc.util.*;
import arc.util.io.*;
import mindustry.*;
import mindustry.gen.*;
import mindustry.mod.*;

import static mindustry.Vars.*;

final class YellowInitials{

    private static final TextureRegionDrawable whiteui = (TextureRegionDrawable) Tex.whiteui;

    public static void begin(){
        Mods.LoadedMod mod = mods.getMod(Yellow.class);
        boolean bundlesLoaded = false;
        boolean setup = Core.settings.getBoolOnce("yellow-setup-active");

        try{
            //try and early-load some of the mod assets into the internal file tree on import
            //needed for the freakshow that is the first fragment
            //ref. Mods#buildFiles()
            ObjectSet<String> s = ObjectSet.with("bundles", "sprites", "sprites-override");

            boolean zipFolder = !mod.file.isDirectory() && mod.root.parent() != null;
            String parentName = zipFolder ? mod.root.name() : null;
            for(Fi file : mod.root.list()){
                //ignore special folders like bundles or sprites
                if(file.isDirectory() && !s.contains(file.name())){
                    file.walk(f -> tree.addFile(mod.file.isDirectory() ? f.path().substring(1 + mod.file.path().length()) :
                            zipFolder ? f.path().substring(parentName.length() + 1) : f.path(), f));
                }
            }

            //try bundles here
            bundlesLoaded = tryLoadingBundles(mod);

            WidgetGroup multiGroup = new WidgetGroup();

            multiGroup.setFillParent(true);
            multiGroup.touchable = Touchable.childrenOnly;
            multiGroup.visible(() -> true);

            Core.scene.add(multiGroup);

            FirstLoadFragment frag = new FirstLoadFragment();
            frag.build(multiGroup);
            multiGroup.update(multiGroup::toFront);

            if(setup) frag.query(YellowInitials::beginSetup);

            //you can click behind the fragment so uh, lets prevent that.
            Dialog d = new Dialog();

            d.setBackground(whiteui.tint(Color.clear));

            d.show().update(() -> {
                if(multiGroup.<Table>find("first load") == null){
                    d.hide();
                    d.remove();
                    multiGroup.clear();
                    multiGroup.remove();
                }
            });
        }catch(Exception e){
            //try displaying the setup dialog if it wasnt already shown before
            if(setup) try{
                //try again if applicable
                if(!bundlesLoaded) bundlesLoaded = tryLoadingBundles(mod);

                if(bundlesLoaded) Vars.ui.showInfoOnHidden("@yellow-setup.ndfailed", YellowInitials::beginSetup);
            }catch(Exception e2){
                //surrender if all goes wrong
                //no need to try and show the setup dialog on game reload, as they can now just head to the settings
            }
        }
    }

    private static boolean tryLoadingBundles(Mods.LoadedMod mod){
        try{
            ObjectMap<String, Seq<Fi>> bundles = new ObjectMap<>();

            //try bundles
            Fi folder = mod.root.child("bundles");
            if(folder.exists()){
                for(Fi file : folder.list()){
                    if(file.name().startsWith("bundle") && file.extension().equals("properties")){
                        String name = file.nameWithoutExtension();
                        bundles.get(name, Seq::new).add(file);
                    }
                }
            }

            //add new keys to each bundle
            I18NBundle bundle = Core.bundle;
            while(bundle != null){
                String str = bundle.getLocale().toString();
                String locale = "bundle" + (str.isEmpty() ? "" : "_" + str);
                for(Fi file : bundles.get(locale, Seq::new)){
                    try{
                        PropertiesUtils.load(bundle.getProperties(), file.reader());
                    }catch(Throwable e){
                        Log.err("Error loading bundle: " + file + "/" + locale, e);
                    }
                }
                bundle = bundle.getParent();
            }
            return true;
        }catch(Exception e){
            return false;
        }
    }

    private static void beginSetup(){

    }
}
