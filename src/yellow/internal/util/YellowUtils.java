package yellow.internal.util;

import arc.*;
import arc.files.*;
import arc.func.*;
import arc.graphics.*;
import arc.math.*;
import arc.scene.event.*;
import arc.scene.style.*;
import arc.scene.ui.*;
import arc.scene.ui.layout.*;
import arc.util.*;
import arc.util.io.*;
import mindustry.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.ui.*;
import yellow.*;
import yellow.type.*;

@SuppressWarnings("CallToPrintStackTrace")
public class YellowUtils{
    private static int currentButtons = 0;
    private static boolean once = false;

    public static Timer.Task loop(float delay, Runnable run){
        return Timer.schedule(run, delay, delay, -1);
    }

    /** Utility for manually mirroring disableable weapons.
     * Why does this exist? Simple, disableable weapons are pure jank with the usual mirror implementation. */
    public static void mirror(Weapon[] in, boolean nameable, boolean disableable, boolean alternate, UnitType unit){
        for (Weapon weapon : in) {
            Weapon mog = weapon.copy();
            mog.x = weapon.x - (weapon.x * 2);
            mog.baseRotation = weapon.baseRotation * -1f;
            if (alternate) {
                mog.reload = weapon.reload * 2;
            }
            mog.name = weapon.name + "-m";
            mog.load();
            if(disableable) ((DisableableWeapon) mog).mirroredVersion = true;
            if(nameable) ((NameableWeapon) mog).displayName = ((NameableWeapon) weapon).displayName + " (Inv)";
            unit.weapons.add(mog);
        }
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

    public static void controlledLog(Object log){
        if(YellowPermVars.INSTANCE.getVerboseLoggering()) Log.info(log);
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
    
    public static void unitBar(Table parent, UnitType type, String rightHandText){
        unitBar(parent, type, rightHandText, -1f);
    }
    
    public static void unitBar(Table parent, UnitType type, String rightHandText, float width){
        table(parent, c -> {
            if(width < 0f){
                c.growX();
            }else{
                c.width(width);
            }
            c.height(65f);
        }, cc -> {
            cc.setBackground(Styles.grayPanel);
            cc.image(type.uiIcon).size(40f).padLeft(20f);
            cc.add(type.localizedName).grow().left().pad(10f);
            cc.add(rightHandText).pad(25f);
        });
    }

    public static void foodOpts(Table parent, Unit unit, Cons<Cell<Table>> table, Cons<Table> tableChildren){
        table(parent, c -> {
            c.width(Core.graphics.getWidth());
            c.height(115);
            table.get(c);
        }, cc -> {
            cc.setBackground(Tex.pane);
            cc.image(unit.type.uiIcon).size(50).padLeft(20f);
            Cell<Label> suse = cc.add(unit.type.localizedName + "\n" + Mathf.round(unit.health) +  "/" + Mathf.round(unit.maxHealth)).grow().left().pad(15f);
            suse.update(up -> {
                if(unit.dead || !unit.isValid()){
                    up.setText("[red]DEAD/INVALID[]");
                    cc.getChildren().each(el -> {
                        el.touchable = Touchable.disabled;
                    });
                }else{
                    up.setText(unit.type.localizedName + "\n" + Mathf.round(unit.health) +  "/" + Mathf.round(unit.maxHealth));
                }
            });
            tableChildren.get(cc);
        });
    }

    //TODO use pages or scrollpane instead of rows
    //resolves issue against scheme size top ui overlapping
    public static void mobileHudButton(Drawable icon, Runnable listener){
        if(!Vars.mobile) return; //bewhewhe
        Table but = Vars.ui.hudGroup.<Table>find("mobile buttons");
        
        if(!once){
            once = true;
            but.row();
        }
        
        if(currentButtons > 4){
            currentButtons = 0;
            but.image().height(65f).width(4f).color(Color.darkGray);
            but.row();
        }
        
        but.table(Styles.none, a -> {
            a.button(icon, Styles.cleari, listener).grow();
        });
        
        currentButtons++;
    }

    public static void emptyHudButtonRow(){
        if(!Vars.mobile) return; //im gonna bomb this whole motherfucking plane
    	for(int i = 0; i < 5 - currentButtons; i++){
    		mobileHudButton(Icon.none, () -> {});
    	}
    	mobileHudButton(Icon.none, () -> {});
    	//god fucking damnit i hate ui
    	Vars.ui.hudGroup.<Table>find("mobile buttons").image().height(65f).width(4f).color(Color.gray);
    }

}
