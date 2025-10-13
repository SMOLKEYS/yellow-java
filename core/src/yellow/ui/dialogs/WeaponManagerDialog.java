package yellow.ui.dialogs;

import arc.scene.event.*;
import arc.scene.ui.*;
import arc.util.*;
import mindustry.entities.units.*;
import mindustry.ui.dialogs.*;
import yellow.entities.units.*;
import yellow.type.weapons.*;

public class WeaponManagerDialog extends BaseDialog{

    private WeaponMount[] cache;

    public WeaponManagerDialog(){
        super("@yellow.weapon-manager");
        addCloseButton();
    }

    public Dialog show(WeaponMount[] mounts){
        if(cache == mounts) return super.show();

        if(!Structs.contains(mounts, t -> t instanceof ToggleWeaponMount) || mounts.length == 0){
            cache = null;
            cont.clear();
            cont.add("@yellow.weapon-manager.no-weapons");
            return super.show();
        }

        cache = mounts;
        cont.clear();

        cont.table(mainManager -> {
            mainManager.pane(weapons -> {
                weapons.top().margin(15);
                for(WeaponMount m : mounts){
                    if(m instanceof ToggleWeaponMount tw){
                        ToggleWeapon tww = (ToggleWeapon) tw.weapon;
                        boolean visible = tww.visibility.visible() && tww.isUnlocked();

                        weapons.check(tww.displayName, tw.enabled, res -> {
                            tw.enabled = res;
                        })
                                .left()
                                .update(b -> {
                                    b.setChecked(tw.enabled && visible);
                                    b.getLabel().setText(visible ? tww.displayName : "[lightgray]< ??? >[]");
                                })
                                .touchable(() -> visible ? Touchable.enabled : Touchable.disabled)
                                .row();
                    }
                }
            }).grow().row();

            //what the fuck?

            mainManager.table(util -> {
                util.bottom().margin(15);
                util.button("@yellow.weapon-manager.enable-all", () -> {
                    for(WeaponMount m : mounts){
                        if(m instanceof ToggleWeaponMount tm) tm.enabled = true;
                    }
                }).growX().row();
                util.button("@yellow.weapon-manager.disable-all", () -> {
                    for(WeaponMount m : mounts){
                        if(m instanceof ToggleWeaponMount tm) tm.enabled = false;
                    }
                }).growX().row();
                util.button("@yellow.weapon-manager.enable-all-mirrors", () -> {
                    for(WeaponMount m : mounts){
                        if(m instanceof ToggleWeaponMount tm && tm.weapon instanceof ToggleWeapon we && we.original != null)
                            tm.enabled = true;
                    }
                }).growX().row();
                util.button("@yellow.weapon-manager.disable-all-mirrors", () -> {
                    for(WeaponMount m : mounts){
                        if(m instanceof ToggleWeaponMount tm && tm.weapon instanceof ToggleWeapon we && we.original != null)
                            tm.enabled = false;
                    }
                }).growX().row();
                util.button("@yellow.weapon-manager.enable-all-originals", () -> {
                    for(WeaponMount m : mounts){
                        if(m instanceof ToggleWeaponMount tm && tm.weapon instanceof ToggleWeapon we && we.original == null)
                            tm.enabled = true;
                    }
                }).growX().row();
                util.button("@yellow.weapon-manager.disable-all-originals", () -> {
                    for(WeaponMount m : mounts){
                        if(m instanceof ToggleWeaponMount tm && tm.weapon instanceof ToggleWeapon we && we.original == null)
                            tm.enabled = false;
                    }
                }).growX().row();
            }).grow();
        });

        return super.show();
    }
}
