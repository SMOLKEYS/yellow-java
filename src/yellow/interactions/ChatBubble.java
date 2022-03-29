package yellow.interactions;

import arc.*;
import arc.Core.*;
import arc.util.*;
import arc.util.Align.*;
import arc.math.geom.*;
import arc.math.geom.Vec2.*;
import arc.scene.actions.*;
import arc.scene.actions.Actions.*;
import arc.scene.ui.layout.*;
import arc.scene.ui.layout.Table.*;
import arc.scene.event.*;
import arc.scene.event.Touchable.*;
import mindustry.gen.Unit;
import mindustry.ui.*;
import mindustry.ui.Styles.*;

import static mindustry.Vars.*;


public class ChatBubble{
    
    
    public static void createBubble(Unit unit, String text){
        var table = new Table(Styles.black3).margin(4);
        table.touchable = Touchable.disabled;
        table.update(() -> {
            if(state.isMenu()) table.remove();
            table.setPosition(unit.x, unit.y + 24, Align.center);
        });
        table.actions(Actions.delay(5f), Actions.fadeOut(7f));
        table.add(text).style(Styles.outlineLabel);
        table.pack();
        table.act(0f);
        
        Core.scene.root.addChildAt(0, table);
        
        table.getChildren.first().act(0f);
    }
}
