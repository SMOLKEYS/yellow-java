package yellow.internal.util;

import arc.files.Fi;
import arc.func.Cons;
import arc.math.Mathf;
import arc.util.*;
import arc.util.io.Streams;
import arc.util.serialization.*;
import mindustry.Vars;
import mindustry.type.UnitType;
import mindustry.type.Weapon;
import yellow.type.NameableWeapon;

import static arc.Core.settings;

public class YellowUtils{

    private static final JsonReader jsr = new JsonReader();
    private static String strd;
    private static int requestLimit = 5, requestsSent = 0;
    private static float requestLimitResetTime = 10f; //in seconds
    private static boolean statusRequestRunning = false;

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
        });
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
            try{
                String res = req.getResultAsString();
                JsonValue pros = jsr.parse(res).get("workflow_runs").get(0);
                JsonValue cons = jsr.parse(res).get("workflow_runs").get(1);
                strd = pros.get("name") + "\n" + pros.get("display_title") + "\n" + pros.get("run_number") + "\n" + pros.get("status") + "\n" + pros.get("conclusion") + "\n-----\n" + cons.get("name") + "\n" + cons.get("display_title") + "\n" + cons.get("run_number") + "\n" + cons.get("status") + "\n" + cons.get("conclusion") + "\n";
                statusRequestRunning = false;
                Vars.ui.showCustomConfirm("RESULT", strd, "@internal.checkagain", "@ok", YellowUtils::getWorkflowStatus, () -> {});
            }catch(Exception e){
                Vars.ui.showException("Workflow Status GET Error", e);
                statusRequestRunning = false;
            }
        });
        requestsSent++;
    }
    
    public static void startRequestLimitHandler(){
        loop(requestLimitResetTime, () -> {
            requestsSent = 0;
        });
    }
}
