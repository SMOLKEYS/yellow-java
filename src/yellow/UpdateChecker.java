package yellow;

import arc.*;
import arc.func.*;
import arc.math.*;
import arc.scene.actions.*;
import arc.util.*;
import arc.util.serialization.*;
import mindustry.*;
import mindustry.gen.*;

import java.util.*;

public class UpdateChecker{

    private static final JsonReader reader = new JsonReader();

    public static void checkUpdate(@Nullable ConsT<Http.HttpResponse, Exception> http, Cons2<String, String> found, Runnable notFound, Cons<Throwable> onErr){
        Http.get("https://api.github.com/repos/SMOLKEYS/yellow-java/releases", upd -> {
            if(http != null) http.get(upd);

            JsonValue val = reader.parse(upd.getResultAsStream());
            String[] ver = new String[val.size];
            for(int r = 0; r < val.size; r++) ver[r] = val.get(r).getString("tag_name", "err");

            String cur = Vars.mods.getMod(Yellow.class).meta.version;
            String lat = ver[0];

            if(!Objects.equals(cur, lat)){
                found.get(cur, lat);
            }else{
                notFound.run();
            }
        }, onErr);
    }

    public static void loadNotifier(){
        int id = Mathf.random(90000);
        YellowVars.ltfrag.add(id, "Checking for updates...", Core.atlas.drawable("yellow-java-yellow"), true);
        checkUpdate(
                null,
                (cur, lat) -> {
                    YellowVars.ltfrag.reconfigure(id, Strings.format("New update found! ([accent]@[] -> [accent]@[])\nTo update, open the mods list, find the mod and then reinstall..", cur, lat), Icon.box, false);
                    YellowVars.ltfrag.hide(id, 60*2, Actions.fadeOut(2));
                },
                () -> {
                    YellowVars.ltfrag.reconfigure(id, "No new updates found.", Icon.cancel, false);
                    YellowVars.ltfrag.hide(id, 60*5, Actions.fadeOut(2));
                },
                err -> {
                    Log.err("========== CHECKING ERROR ==========");
                    Log.err(err);
                    Log.err("====================================");

                    YellowVars.ltfrag.reconfigure(id, "Failed to check for updates!", Icon.cancel, false);
                    YellowVars.ltfrag.hide(id, 60*3, Actions.fadeOut(2));
                }
        );
    }
}
