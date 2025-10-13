package yellow.util;

import arc.graphics.*;
import arc.scene.style.*;
import arc.scene.ui.layout.*;
import arc.util.*;
import mindustry.*;
import mindustry.core.*;
import mindustry.maps.*;
import mindustry.ui.*;

public class MultiUtils{

    private static int buttons = 0;
    private static boolean once = false;

    public static void forceHideLoading(){
        Vars.ui.loadfrag.hide();
        ((Table) Reflect.get(Vars.ui.loadfrag, "table")).visible = false;
    }

    /** Forcibly loads a map. */
    public static void forceLoad(Map map){
        YellowVars.ui.blankfrag.show();
        YellowVars.ui.multiGroup.toFront();
        Time.run(15f, () -> {
            Vars.world.loadMap(map);
            Vars.state.set(GameState.State.playing);
            YellowVars.ui.blankfrag.hide();
        });
    }

    //TODO starts being intrusive at 10 buttons, use scrollpane or pages
    /** Adds a button to the button group located at the top-left side of the HUD in mobile. Does nothing on desktop. */
    public static void mobileHudButton(Drawable icon, Runnable listener){
        if(!Vars.mobile) return;
        var but = Vars.ui.hudGroup.<Table>find("mobile buttons");


        if(!once){
            once = true;
            but.row(); //for first custom row
        }

        but.table(Styles.none, a -> {
            a.button(icon, Styles.cleari, listener).grow();
            buttons++;
        });

        if(buttons == 5){
            buttons = 0;
            but.image().height(65f).width(4f).color(Color.darkGray);
            but.row();
        }

    }
}
