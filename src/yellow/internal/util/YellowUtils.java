package yellow.internal.util;

import arc.Core;
import arc.files.Fi;
import arc.func.Cons;
import arc.math.Mathf;
import arc.util.*;
import arc.util.io.Streams;
import arc.util.serialization.*;
import mindustry.Vars;
import mindustry.type.UnitType;
import mindustry.type.Weapon;
import yellow.game.YellowPermVars;
import yellow.type.NameableWeapon;
import yellow.ui.YellowSettings;

import static arc.Core.settings;

public class YellowUtils{

    private static final JsonReader jsr = new JsonReader();
    private static String strd;
    private static final int requestLimit = 5;
    private static int requestsSent = 0;
    private static float requestLimitResetTime = 10f; //in seconds
    private static boolean statusRequestRunning = false;
    private static final String[][] choices = {{"@ok", "@internal.checkagain"}, {"@internal.openrepo"}};

    public static boolean isEnabled(String modName){
        return settings.getBool("mod-" + modName + "-enabled");
    }
    
    public static Timer.Task loop(float delay, Runnable run){
        return Timer.schedule(run, delay, delay, -1);
    }
    
    public static void mirror(Weapon[] in, boolean nameable, boolean alternate, UnitType unit){
        for (Weapon weapon : in) {
            Weapon mog = weapon.copy();
            mog.x = weapon.x - (weapon.x * 2);
            if (alternate) {
                mog.reload = weapon.reload * 2;
            }
            mog.name = weapon.name + "-m";
            mog.load();
            if (nameable) {
                ((NameableWeapon) mog).displayName = ((NameableWeapon) weapon).displayName + " (Inv)";
            }
            unit.weapons.add(mog);
        }
    }

    public static void getAndWrite(String link, Fi file, boolean overwrite, Cons<Fi> cons){
        Http.get(link, a -> {
            try{
                Streams.copyProgress(a.getResultAsStream(), file.write(!overwrite), a.getContentLength(), 4096, l -> {});

                cons.get(file);
            }catch(Exception e){
                Vars.ui.showException("Http Get Error", e);
            }
        }, err -> Core.app.post(() -> {
            err.printStackTrace();
            Vars.ui.showException("Mod update error", err);
        }));
    }

    public static Object random(Object[] arr){
        return arr[Mathf.random(arr.length)];
    }

    public static void getWorkflowStatus(){
        if(requestsSent >= requestLimit){
            Vars.ui.showInfo("@internal.request-limit-hit");
            return;
        }else if(statusRequestRunning){
            Vars.ui.showInfo("@internal.waiting-for-request");
            return;
        }
        
        statusRequestRunning = true;
        
        Http.get("https://api.github.com/repos/SMOLKEYS/yellow-java/actions/runs", req -> {
            String res = req.getResultAsString();
            
            try{
                JsonValue pros = jsr.parse(res).get("workflow_runs").get(0);
                JsonValue cons = jsr.parse(res).get("workflow_runs").get(1);
                strd = pros.get("name") + "\n" + pros.get("display_title") + "\n" + pros.get("run_number") + "\n" + pros.get("status") + "\n" + pros.get("conclusion") + "\n-----\n" + cons.get("name") + "\n" + cons.get("display_title") + "\n" + cons.get("run_number") + "\n" + cons.get("status") + "\n" + cons.get("conclusion") + "\n";
                statusRequestRunning = false;
                Vars.ui.showMenu("RESULT", strd, choices, sel -> {
                    switch(sel){
                        case 0:
                            break;
                        case 1:
                            getWorkflowStatus();
                            break;
                        case 2:
                            Core.app.openURI("https://github.com/SMOLKEYS/yellow-java");
                            break;
                    }
                });
            }catch(Exception e){
                e.printStackTrace();
                Vars.ui.showException("Workflow Status GET Error", e);
                statusRequestRunning = false;
            }
        }, err -> Core.app.post(() -> {
            err.printStackTrace();
            Vars.ui.showException("Workflow Status GET Error", err);
            statusRequestRunning = false;
        }));
        
        requestsSent++;
    }
    
    public static void startRequestLimitHandler(){
        loop(requestLimitResetTime, () -> requestsSent = 0);
    }

    public static void controlledLog(Object log){
        if(YellowPermVars.INSTANCE.getVerboseLoggering()) Log.info(log);
    }
}
