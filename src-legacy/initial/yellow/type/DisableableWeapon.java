package yellow.type;

import mindustry.entities.units.*;
import mindustry.gen.*;
import mindustry.type.*;

public class DisableableWeapon extends NameableWeapon{
    
    public boolean mirroredVersion = false;
    public boolean enabledDefault = true;
    
    public DisableableWeapon(String name){
        super(name);
        mountType = DisableableWeaponMount::new;
        mirror = false;
        shootCone = 35f;
    }

    /** Utility for manually mirroring disableable weapons.
     * Why does this exist? Simple, disableable weapons are do not work with the usual mirror implementation. */
    public static void mirror(Weapon[] in, boolean nameable, boolean disableable, boolean alternate, UnitType unit){
        try{
            for(var weapon : in){
                var mog = weapon.copy();
                mog.x = weapon.x - (weapon.x * 2);
                mog.baseRotation = weapon.baseRotation * -1f;
                if(alternate){
                    //mog.reload = weapon.reload * 2; worknt, innit?
                }
                mog.name = weapon.name + "-m";
                mog.load();
                if(disableable) ((DisableableWeapon) mog).mirroredVersion = true;
                if(nameable) ((NameableWeapon) mog).displayName = ((NameableWeapon) weapon).displayName + " (Inv)";
                unit.weapons.add(mog);
            }
        }catch(Exception three){
            if(three instanceof ClassCastException caster){
                throw new RuntimeException("how do you even get this far?", caster);
            }
            throw new RuntimeException("amazing high level language which three billion devices run", three);
        }
    }

    //basically, if the mount is disabled, ignore most code entirely
    @Override
    public void update(Unit unit, WeaponMount mount){
        //compatibility layer
        if(!(unit instanceof YellowUnitEntity)){
            super.update(unit, mount);
            return;
        }

        if(mount instanceof DisableableWeaponMount d && !d.enabled) return;
        super.update(unit, mount);
    }
    
    @Override
    public void draw(Unit unit, WeaponMount mount){
        if(!(unit instanceof YellowUnitEntity)){
            super.draw(unit, mount);
            return;
        }
        if(mount instanceof DisableableWeaponMount d && !d.enabled) return;
        super.draw(unit, mount);
    }

    @Override
    protected void shoot(Unit unit, WeaponMount mount, float shootX, float shootY, float rotation){
        if(!(unit instanceof YellowUnitEntity)){
            super.shoot(unit, mount, shootX, shootY, rotation);
            return;
        }
        if(mount instanceof DisableableWeaponMount d && !d.enabled) return;
        super.shoot(unit, mount, shootX, shootY, rotation);
    }

    @Override
    protected float bulletRotation(Unit unit, WeaponMount mount, float bulletX, float bulletY){
        if(!(unit instanceof YellowUnitEntity)){
            return super.bulletRotation(unit, mount, bulletX, bulletY);
        }
        if(mount instanceof DisableableWeaponMount d && !d.enabled) return 0f;
        return super.bulletRotation(unit, mount, bulletX, bulletY);
    }

    @Override
    protected void handleBullet(Unit unit, WeaponMount mount, Bullet bullet){
        if(!(unit instanceof YellowUnitEntity)){
            super.handleBullet(unit, mount, bullet);
            return;
        }
        if(mount instanceof DisableableWeaponMount d && !d.enabled) return;
        super.handleBullet(unit, mount, bullet);
    }


}
