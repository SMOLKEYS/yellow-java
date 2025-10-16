package yellow;

import arc.*;
import arc.func.*;
import arc.scene.actions.*;
import arc.util.*;
import arc.util.serialization.*;
import mindustry.*;
import mindustry.gen.*;
import yellow.util.*;

public class UpdateChecker{

    private static final JsonReader reader = new JsonReader();

    public static boolean updateAvailable, updateQueued;

    public static void checkUpdate(@Nullable ConsT<Http.HttpResponse, Exception> http, Cons2<Float, Float> found, Runnable notFound, Cons<Throwable> onErr){
        String version = Vars.mods.getMod(Yellow.class).meta.version;

        if(version.contains("rapid")){
            Log.info("Using a rapid development build of Yellow. Skipping update check.");
            return;
        }

        Http.get("https://api.github.com/repos/SMOLKEYS/yellow-java/releases", upd -> {
            if(http != null) http.get(upd);

            JsonValue val = reader.parse(upd.getResultAsStream());
            String[] ver = new String[val.size];
            for(int r = 0; r < val.size; r++) ver[r] = val.get(r).getString("tag_name", "err");

            float cur = Stringy.handleNumber(version);
            float lat = Stringy.handleNumber(ver[0]);

            if(lat > cur){
                found.get(cur, lat);
            }else{
                notFound.run();
            }
        }, onErr);
    }

    public static void loadNotifier(){
        updateQueued = true;
        int id = YellowVars.ltfrag.add( Core.bundle.get("yellow.updater-scanning"), Core.atlas.drawable("yellow-java-yellow"), true);
        checkUpdate(
                null,
                (cur, lat) -> {
                    updateAvailable = true;
                    updateQueued = false;
                    YellowVars.notifrag.showPersistentNotification(Icon.box, Core.bundle.format("yellow.updater-found-notification", cur, lat));
                    YellowVars.ltfrag.hide(id, 0, Actions.fadeOut(2));
                    Log.info("New Yellow update available.");
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
