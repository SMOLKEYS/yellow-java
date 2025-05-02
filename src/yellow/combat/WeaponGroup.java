package yellow.combat;

import arc.*;
import arc.input.*;
import arc.struct.*;
import mindustry.type.*;

public class WeaponGroup{
    private int currentWeapon = 0;
    private KeyBind swapper;
    private KeyBind shooter;

    public String name;
    public Seq<Weapon> weapons = new Seq<>();

    /**
     * Build a new weapon group.
     *
     * @param name the group name
     * @param leftSwapper default key to move to the left side of the list
     * @param rightSwapper default key to move to the right side of the list
     * @param shooter default key to shoot the current selected weapon in this group, typically left click
     * @param weapons the weapons in this group
     */
    public WeaponGroup(String name, KeyCode leftSwapper, KeyCode rightSwapper, KeyBind.KeybindValue shooter, Weapon... weapons){
        this.swapper = KeyBind.add(name + "_swapper", new KeyBind.Axis(leftSwapper, rightSwapper), "yellow_combat");
        this.shooter = KeyBind.add(name, shooter, "yellow_combat");
    }
}
