package yellow.type;

import mindustry.entities.units.*;
import mindustry.gen.*;
import mindustry.type.*;
import yellow.entities.units.*;
import yellow.entities.units.entity.*;

public class DisableableWeapon extends NameableWeapon{
    
    public boolean mirroredVersion = false;
    public boolean enabledDefault = true;
    
    public DisableableWeapon(String name){
        super(name);
        mountType = DisableableWeaponMount::new;
        mirror = false;
        shootCone = 360f;
    }

    /** Utility for manually mirroring disableable weapons.
     * Why does this exist? Simple, disableable weapons are pure jank with the usual mirror implementation. */
    public static void mirror(Weapon[] in, boolean nameable, boolean disableable, boolean alternate, UnitType unit){
        for (Weapon weapon : in) {
            Weapon mog = weapon.copy();
            mog.x = weapon.x - (weapon.x * 2);
            mog.baseRotation = weapon.baseRotation * -1f;
            if (alternate) {
                //mog.reload = weapon.reload * 2; worknt, innit?
            }
            mog.name = weapon.name + "-m";
            mog.load();
            if(disableable) ((DisableableWeapon) mog).mirroredVersion = true;
            if(nameable) ((NameableWeapon) mog).displayName = ((NameableWeapon) weapon).displayName + " (Inv)";
            unit.weapons.add(mog);
        }
    }

    //basically, if the mount is disabled, ignore update and draw code entirely
    //breaks with normal mirror implementation
    @Override
    public void update(Unit unit, WeaponMount mount){
        //compatibility with ESR Flarogus
        if(!(unit instanceof YellowUnitEntity)){
            super.update(unit, mount);
            return;
        }
        if(!((DisableableWeaponMount) mount).enabled) return;
        super.update(unit, mount);
    }
    
    @Override
    public void draw(Unit unit, WeaponMount mount){
        if(!(unit instanceof YellowUnitEntity)){
            super.draw(unit, mount);
            return;
        }
        if(!((DisableableWeaponMount) mount).enabled) return;
        super.draw(unit, mount);
    }
}
