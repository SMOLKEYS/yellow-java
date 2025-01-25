package yellow;

import arc.*;
import arc.scene.actions.*;
import arc.scene.ui.*;
import arc.util.*;
import mindustry.game.*;
import mindustry.mod.*;
import mindustry.ui.dialogs.*;

public class Yellow extends Mod{

    public Yellow(){
        Events.run(EventType.ClientLoadEvent.class, () -> {
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
            wip.cont.button("Noted", () -> {
                wip.hide(Actions.fadeOut(2));
            }).size(150, 50);

            wip.show();
        });
    }
}
