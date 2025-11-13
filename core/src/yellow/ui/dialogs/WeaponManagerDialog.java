package yellow.ui.dialogs;

import arc.graphics.*;
import arc.math.*;
import arc.scene.actions.*;
import arc.scene.event.*;
import arc.scene.ui.*;
import arc.util.*;
import mindustry.entities.units.*;
import mindustry.graphics.*;
import mindustry.ui.*;
import mindustry.ui.dialogs.*;
import yellow.entities.units.*;
import yellow.type.weapons.*;
import yellow.ui.*;
import yellow.util.*;

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

                        /*
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
                        */

                        TextButton button = weapons.button(tww.displayName, Styles.defaultt, () -> {
                            tw.enabled = !tw.enabled;
                        })
                                .center()
                                .growX()
                                .height(40f)
                                .pad(10f)
                                .update(b -> {
                                    //b.setChecked(tw.enabled && visible);
                                    Label l = b.getLabel();
                                    l.setText(visible ? tww.displayName : "[lightgray]< ??? >[]");
                                    MiscUtils.updateLabelColor(l, tw.enabled, Pal.command, Pal.remove, 0.4f, Interp.pow3Out);
                                })
                                .touchable(() -> visible ? Touchable.enabled : Touchable.disabled)
                                .get();

                        button.getLabel().setStyle(YellowStyles.gothicStyle);

                        weapons.row();
                    }
                }
            }).grow().row();

            //what the fuck?

            mainManager.table(util -> {
                util.bottom().margin(15);
                util.button("@yellow.weapon-manager.enable-all", Styles.defaultt, () -> {
                    for(WeaponMount m : mounts){
                        if(m instanceof ToggleWeaponMount tm) tm.enabled = true;
                    }
                }).growX().row();
                util.button("@yellow.weapon-manager.disable-all", Styles.defaultt, () -> {
                    for(WeaponMount m : mounts){
                        if(m instanceof ToggleWeaponMount tm) tm.enabled = false;
                    }
                }).growX().row();
                util.button("@yellow.weapon-manager.enable-all-mirrors", Styles.defaultt, () -> {
                    for(WeaponMount m : mounts){
                        if(m instanceof ToggleWeaponMount tm && tm.weapon instanceof ToggleWeapon we && we.original != null)
                            tm.enabled = true;
                    }
                }).growX().row();
                util.button("@yellow.weapon-manager.disable-all-mirrors", Styles.defaultt, () -> {
                    for(WeaponMount m : mounts){
                        if(m instanceof ToggleWeaponMount tm && tm.weapon instanceof ToggleWeapon we && we.original != null)
                            tm.enabled = false;
                    }
                }).growX().row();
                util.button("@yellow.weapon-manager.enable-all-originals", Styles.defaultt, () -> {
                    for(WeaponMount m : mounts){
                        if(m instanceof ToggleWeaponMount tm && tm.weapon instanceof ToggleWeapon we && we.original == null)
                            tm.enabled = true;
                    }
                }).growX().row();
                util.button("@yellow.weapon-manager.disable-all-originals", Styles.defaultt, () -> {
                    for(WeaponMount m : mounts){
                        if(m instanceof ToggleWeaponMount tm && tm.weapon instanceof ToggleWeapon we && we.original == null)
                            tm.enabled = false;
                    }
                }).growX().row();
            }).grow();
        }).grow();

        return super.show();
    }
}
