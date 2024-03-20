package yellow.util;

import arc.*;
import arc.files.*;
import arc.func.*;
import arc.graphics.*;
import arc.math.*;
import arc.scene.style.*;
import arc.scene.ui.layout.*;
import arc.util.*;
import arc.util.io.*;
import mindustry.*;
import mindustry.game.*;
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

    public static void internalLog(String text, Object... args){
        if(YellowPermVars.INSTANCE.getInternalLoggering()) Log.info(text, args);
    }

    public static void table(Table parent, Cons<Cell<Table>> child, Cons<Table> childContents){
        child.get(parent.table(childContents));
    }

    //TODO use pages or scrollpane instead of rows
    public static void mobileHudButton(Drawable icon, Runnable listener){
        if(!Vars.mobile) return; //bwehehe
        var but = Vars.ui.hudGroup.<Table>find("mobile buttons");
        
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
            if(each[i] instanceof Drawable d && each[i + 1] instanceof Runnable r) mobileHudButton(d, r);
        }
    }


    public static void emptyHudButtonRow(){
    	for(int i = 0; i < 5 - currentButtons; i++){
    		mobileHudButton(Icon.none.tint(Color.clear), () -> {});
    	}
    }

    public static YellowUnitEntity getActiveYellow(Team team){
        return (YellowUnitEntity) Groups.unit.find(a -> a instanceof YellowUnitEntity && a.team == team);
    }

    public static <T> T safeInvoke(Object object, String methodName){
        try{
            return Reflect.invoke(object, methodName);
        }catch(Exception ignored){
            return null;
        }
    }

    public static <T> T safeInvoke(Object object, String methodName, Object... args){
        try{
            return Reflect.invoke(object, methodName, args);
        }catch(Exception ignored){
            return null;
        }
    }

    public static <T> T safeGet(Object object, String field){
        try{
            return Reflect.get(object, field);
        }catch(Exception e){
            return null;
        }
    }

    public static void safeSet(Object object, String field, Object value){
        try{
            Reflect.set(object, field, value);
        }catch(Exception ignored){

        }
    }

    public static <T> T safeInvoke(String className, String methodName){
        try{
            return Reflect.invoke(Class.forName(className, true, Vars.mods.mainLoader()), methodName);
        }catch(Exception ignored){
            return null;
        }
    }

    public static <T> T safeInvoke(String className, String methodName, Object... args){
        try{
            return Reflect.invoke(Class.forName(className, true, Vars.mods.mainLoader()), methodName, args);
        }catch(Exception ignored){
            return null;
        }
    }

    public static <T> T safeGet(String className, String field){
        try{
            return Reflect.get(Class.forName(className, true, Vars.mods.mainLoader()), field);
        }catch(Exception ignored){
            return null;
        }
    }

    public static void safeSet(String className, String field, Object value){
        try{
            Reflect.set(Class.forName(className, true, Vars.mods.mainLoader()), field, value);
        }catch(Exception ignored){

        }
    }

    public static Color pulse(Color from, Color to, float scl){
        return Tmp.c1.set(from).lerp(to, Mathf.absin(Time.time, scl, 1f));
    }

    public static Color pulse(Color to, float scl){
        return pulse(Color.white, to, scl);
    }
}
