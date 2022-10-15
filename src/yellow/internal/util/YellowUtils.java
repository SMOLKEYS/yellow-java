package yellow.internal.util;

import arc.files.Fi;
import arc.func.Cons;
import arc.math.Mathf;
import arc.util.Http;
import arc.util.io.Streams;
import arc.util.serialization.*;
import mindustry.Vars;
import mindustry.type.UnitType;
import mindustry.type.Weapon;
import yellow.type.NameableWeapon;

import static arc.Core.settings;

public class YellowUtils{

    private static final JsonReader jsr = new JsonReader();

    public static boolean isEnabled(String modName){
        return settings.getBool("mod-" + modName + "-enabled");
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
        try{
            Http.get(link, a -> {
                Streams.copyProgress(a.getResultAsStream(), file.write(!overwrite), a.getContentLength(), 4096, l -> {});

                cons.get(file);
            });

        }catch(Exception e){
            Vars.ui.showException("HTTP GET ERROR", e);
        }
    }

    public static Object random(Object[] arr){
        return arr[Mathf.random(arr.length)];
    }

    public static void getWorkflowStatus(){
        StringBuilder bus = new StringBuilder();
        Http.get("https://api.github.com/repos/SMOLKEYS/yellow-java/actions/runs", req -> {
            String res = req.getResultAsString();
            for(int i = 0; i > 7; i++){
                JsonValue pros = jsr.parse(res).get("workflow_runs").get(i);
                bus.append(pros.get("name") + "\n");
                bus.append(pros.get("display_title") + "\n");
                bus.append(pros.get("run_number") + "\n");
                bus.append(pros.get("status") + "\n");
                bus.append(pros.get("conclusion") + "\n");
            }
            Vars.ui.showCustomConfirm("RESULT OF LAST 7 RUNS", bus.toString(), "Check Again", "Ok", YellowUtils::getWorkflowStatus, () -> {});
        });
    }
}
