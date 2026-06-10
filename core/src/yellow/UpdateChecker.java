package yellow;

import arc.*;
import arc.func.*;
import arc.scene.actions.*;
import arc.util.*;
import arc.util.serialization.*;
import mindustry.*;
import mindustry.gen.*;
import yellow.YellowVars.*;

import java.util.*;
import java.util.concurrent.atomic.*;

public class UpdateChecker{

    private static final JsonReader reader = new JsonReader();

    public static boolean updateAvailable, updateQueued;

    public static void checkUpdate(@Nullable ConsT<Http.HttpResponse, Exception> http, Cons2<String, String> found, Runnable notFound, Cons<Throwable> onErr){
        if(YellowVars.build() == BuildType.rapid){
            Log.info("Using a rapid development build of Yellow. Skipping update check.");
            return;
        }

        //TODO support for other mirrors (i.e codeberg and gitlab)
        //microsoft on it's way to ruin absolutely everything again
        Http.get("https://api.github.com/repos/SMOLKEYS/yellow-java/releases", upd -> {
            if(http != null) http.get(upd);

            String version = Vars.mods.getMod(Yellow.class).meta.version;
            JsonValue val = reader.parse(upd.getResultAsStream());
            String[] ver = new String[val.size];
            for(int r = 0; r < val.size; r++) ver[r] = val.get(r).getString("tag_name", "err");
            /*
            TODO reimplement later
            AtomicBoolean wasFound = new AtomicBoolean(false);
            AtomicReference<String> selectVersion = new AtomicReference<>();

            for(String s : ver){
                if(Objects.equals(s, version)){
                    wasFound.set(true);
                    selectVersion.set(s);
                }
            }

            if(wasFound.get()){
                found.get(version, selectVersion.get());
            }else{
                notFound.run();
            }
             */

            /*
            TODO this works if the latest entry is listed as the latest correct version, but if not, it just doesn't
            if(version != lat){
                found.get(version, lat);
            }else{
                notFound.run();
            }
             */

            /*
            TODO this only works for version tags that only contain numbers and the dot symbol
            float cur = Stringy.handleNumber(version);
            float lat = Stringy.handleNumber(ver[0]);

            if(lat > cur){
                found.get(cur, lat);
            }else{
                notFound.run();
            }
            */
        }, onErr);
    }

    public static void loadNotifier(){
        if(YellowVars.build() == BuildType.rapid){
            Log.info("Using a rapid development build of Yellow. Skipping update check.");
            return;
        }

        updateQueued = true;
        int id = YellowVars.ltfrag.add(Core.bundle.get("yellow.updater-scanning"), Core.atlas.drawable("yellow-java-yellow"), true);
        checkUpdate(
                null,
                (cur, lat) -> {
                    updateAvailable = true;
                    updateQueued = false;
                    YellowVars.notifrag.showPersistentNotification(Icon.box, Core.bundle.format("yellow.updater-found-notification", cur, lat));
                    YellowVars.ltfrag.hide(id, 0, Actions.fadeOut(2));
                    Log.info("New Yellow update available. (@ -> @)", cur, lat);
                },
                () -> {
                    updateAvailable = false;
                    updateQueued = false;
                    YellowVars.ltfrag.reconfigure(id, Core.bundle.get("yellow.updater-notfound"), Icon.cancel, false);
                    YellowVars.ltfrag.hide(id, 60*3f, Actions.fadeOut(2));
                    Log.info("No new Yellow updates found.");
                },
                err -> {
                    Log.err("========== CHECKING ERROR ==========");
                    Log.err(err);
                    Log.err("====================================");

                    updateQueued = false;
                    updateAvailable = false;

                    YellowVars.ltfrag.reconfigure(id, Core.bundle.get("yellow.updater-failed"), Icon.cancel, false);
                    YellowVars.ltfrag.hide(id, 60*3f, Actions.fadeOut(2));
                }
        );
    }
}
