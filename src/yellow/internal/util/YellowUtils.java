package yellow.internal.util;

import arc.*;
import arc.files.*;
import arc.func.*;
import arc.graphics.*;
import arc.math.*;
import arc.scene.style.*;
import arc.scene.ui.*;
import arc.scene.ui.layout.*;
import arc.util.*;
import arc.util.io.*;
import mindustry.*;
import mindustry.gen.*;
import mindustry.ui.*;
import yellow.*;

@SuppressWarnings("CallToPrintStackTrace")
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
                Vars.ui.showException("Http Get Error", e);
            }
        }, err -> Core.app.post(() -> {
            err.printStackTrace();
            Vars.ui.showException("Error", err);
        }));
    }

    public static <T> T random(T[] arr){
        return arr[Mathf.random(arr.length)];
    }

    public static void internalLog(Object log){
        if(YellowPermVars.INSTANCE.getInternalLoggering()) Log.info(log);
    }
    
    public static float combineInterp(Interp main, Interp other, float base){
        return main.apply(other.apply(base));
    }
    
    public static void table(Table parent, Cons<Cell<Table>> child, Cons<Table> childContents){
        child.get(parent.table(childContents));
    }
    
    public static void dialogTable(Dialog parent, Cons<Cell<Table>> tableParent, Cons<Table> tableContents){
        tableParent.get(parent.cont.table(tableContents));
    }

    //TODO use pages or scrollpane instead of rows
    //resolves issue against scheme size top ui overlapping
    public static void mobileHudButton(Drawable icon, Runnable listener){
        if(!Vars.mobile) return; //bwehehe
        Table but = Vars.ui.hudGroup.<Table>find("mobile buttons");
        
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

    public static void emptyHudButtonRow(){
    	for(int i = 0; i < 5 - currentButtons; i++){
    		mobileHudButton(Icon.none.tint(Color.clear), () -> {});
    	}
    }

}
