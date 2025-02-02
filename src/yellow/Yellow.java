package yellow;

import arc.*;
import arc.scene.actions.*;
import arc.scene.ui.*;
import arc.util.*;
import arc.util.serialization.*;
import mindustry.*;
import mindustry.game.*;
import mindustry.mod.*;
import mindustry.ui.dialogs.*;

import java.util.*;
import java.util.concurrent.atomic.*;

public class Yellow extends Mod{

    public Yellow(){

        Events.run(EventType.ClientLoadEvent.class, () -> {
            AtomicReference<String> s = new AtomicReference<>();
            JsonReader reader = new JsonReader();

            BaseDialog wip = new BaseDialog("Yellow");

            wip.cont.labelWrap("Hello there!\nYellow is entering a third rewrite, and the code is currently empty.\nYou probably shouldn't be here.\n\n\nYet.").labelAlign(Align.center).row();
            Image i = wip.cont.image(Core.atlas.find("yellow-java-yellow")).get();

            i.clicked(() -> {
                i.actions(Actions.sequence(
                        Actions.scaleBy(0.5f, -0.5f),
                        Actions.scaleBy(-0.5f, 0.5f, 0.25f)
                ));
            });

            wip.cont.row();
            wip.cont.button("Noted", wip::hide).size(150, 50).row();

            wip.cont.button("Repository", () -> {
                Core.app.openURI("https://github.com/SMOLKEYS/yellow-java");
            }).size(450, 50).row();

            s.set("Checking for updates...");
            wip.cont.labelWrap(s::get).labelAlign(Align.center);

            Http.get("https://api.github.com/repos/SMOLKEYS/yellow-java/releases", upd -> {
                JsonValue val = reader.parse(upd.getResultAsStream());
                String[] ver = new String[val.size];
                for(int r = 0; r < val.size; r++) ver[r] = val.get(r).getString("tag_name", "err");

                String cur = Vars.mods.getMod(Yellow.class).meta.version;
                String lat = ver[0];

                if(!Objects.equals(cur, lat)){
                    s.set("New update available!\n[accent]" + cur + "[] -> [accent]" + lat + "[]\nYou can update from the mods list.");
                }else{
                    s.set("No new updates found.");
                }
            }, err -> {
                Log.err("========== CHECKING ERROR ==========");
                Log.err(err);
                Log.err("====================================");
                s.set("Couldn't check for updates; check last_log.txt for more details. ([red]" + err.getClass().getSimpleName() + "[])");
            });

            wip.show();
        });
    }
}
