package yellow.util;

import arc.*;
import arc.files.*;
import arc.func.*;
import arc.graphics.*;
import arc.scene.style.*;
import arc.scene.ui.layout.*;
import arc.util.*;
import arc.util.io.*;
import mindustry.*;
import mindustry.gen.*;
import mindustry.ui.*;
import yellow.*;
import yellow.entities.units.entity.*;

@SuppressWarnings({"CallToPrintStackTrace", "unused"})
public class YellowUtils{
    private static int currentButtons = 0;
    private static boolean once = false;

    public static void loop(float delay, Runnable run){
        Timer.schedule(run, delay, delay, -1);
    }

    /** Gets the resource from the inputted link and writes it to the specified file path. If the file already exists and is not empty, the overwrite parameter determines whether the file contents get overwritten. */
    public static void getAndWrite(String link, Fi file, boolean overwrite, Cons<Fi> cons){
        Http.get(link, a -> {
            try{
                if(overwrite && file.exists() && file.readString().isEmpty()) return;
                Streams.copyProgress(a.getResultAsStream(), file.write(), a.getContentLength(), 4096, l -> {});

                cons.get(file);
            }catch(Exception e){
                Vars.ui.showException("Error during file writing", e);
            }
        }, err -> Core.app.post(() -> {
            err.printStackTrace();
            Vars.ui.showException("GET request error", err);
        }));
    }

    public static void internalLog(Object log){
        if(YellowPermVars.INSTANCE.getInternalLoggering()) Log.info(log);
    }

    public static void table(Table parent, Cons<Cell<Table>> child, Cons<Table> childContents){
        child.get(parent.table(childContents));
    }

    //TODO use pages or scrollpane instead of rows
    public static void mobileHudButton(Drawable icon, Runnable listener){
        if(!Vars.mobile) return; //bwehehe
        Table but = Vars.ui.hudGroup.find("mobile buttons");
        
        if(!once){
            once = true;
            but.row(); //for first custom row
        }
        
        but.table(Styles.none, a -> {
            a.button(icon, Styles.cleari, listener).grow();
            currentButtons++;
        });

        if(currentButtons == 5){
            currentButtons = 0;
            but.image().height(65f).width(4f).color(Color.darkGray);
            but.row();
        }

    }

    //accepts icon1, run1, icon2, run2...
    public static void mobileHudButtons(Object... each){
        for(int i = 0; i < each.length / 2; i++){
            mobileHudButton((Drawable) each[i], (Runnable) each[i + 1]);
        }
    }


    public static void emptyHudButtonRow(){
    	for(int i = 0; i < 5 - currentButtons; i++){
    		mobileHudButton(Icon.none.tint(Color.clear), () -> {});
    	}
    }

    public static YellowUnitEntity getActiveYellow(){

        return (YellowUnitEntity) Groups.unit.find(a -> a instanceof YellowUnitEntity);
    }

}
