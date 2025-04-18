package yellow;

import arc.*;
import arc.func.*;
import arc.math.*;
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
        Http.get("https://api.github.com/repos/SMOLKEYS/yellow-java/releases", upd -> {
            if(http != null) http.get(upd);

            JsonValue val = reader.parse(upd.getResultAsStream());
            String[] ver = new String[val.size];
            for(int r = 0; r < val.size; r++) ver[r] = val.get(r).getString("tag_name", "err");

            float cur = Stringy.handleNumber(Vars.mods.getMod(Yellow.class).meta.version);
            float lat = Stringy.handleNumber(ver[0]);

            if(lat > cur){
                found.get(cur, lat);
            }else{
                notFound.run();
            }
        }, onErr);
    }

    public static void loadNotifier(){
        int id = Mathf.random(90000);
        updateQueued = true;
        YellowVars.ltfrag.add(id, "Checking for updates...", Core.atlas.drawable("yellow-java-yellow"), true);
        checkUpdate(
                null,
                (cur, lat) -> {
                    updateAvailable = true;
                    updateQueued = false;
                    YellowVars.notifrag.showPersistentNotification(Icon.box, Strings.format("New update found! ([accent]@[] -> [accent]@[])\nTo update, open the mods list, find the mod and then reinstall.", cur, lat));
                    YellowVars.ltfrag.hide(id, 0, Actions.fadeOut(2));
                },
                () -> {
                    updateAvailable = false;
                    updateQueued = false;
                    YellowVars.ltfrag.reconfigure(id, "No new updates found.", Icon.cancel, false);
                    YellowVars.ltfrag.hide(id, 60*5, Actions.fadeOut(2));
                },
                err -> {
                    Log.err("========== CHECKING ERROR ==========");
                    Log.err(err);
                    Log.err("====================================");

                    updateQueued = false;
                    updateAvailable = false;

                    YellowVars.ltfrag.reconfigure(id, "Failed to check for updates!", Icon.cancel, false);
                    YellowVars.ltfrag.hide(id, 60*3, Actions.fadeOut(2));
                }
        );
    }
}
