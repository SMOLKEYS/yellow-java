package yellow.type.weapons;

import arc.struct.*;
import mindustry.entities.*;
import mindustry.entities.units.*;
import mindustry.gen.*;
import yellow.entities.units.*;

/** A weapon that can be toggled. Toggling a weapon allows you to effectively enable/disable it when needed, stopping it from functioning. Does NOT support regular mirroring, use {@link Mirror} for that. */
public class ToggleWeapon extends NamedWeapon{

    /** Whether this weapon is enabled by default. */
    public boolean enabledDefault = true;
    /** The original weapon this one is sourced from. Null for root weapons. {@link Mirror} handles this. Do NOT modify! */
    public ToggleWeapon original;
    /** The mirror variant of this weapon. {@link Mirror} handles this. Do NOT modify! */
    public ToggleWeapon mirrored;
    /** If true, {@link Mirror#apply(Seq, ToggleWeapon...)} will create a copy of this weapon. */
    public boolean willMirror = false;
    /** Special properties used if this weapon will be mirrored. */
    public Mirror.ReflectProperty[] properties = {};

    public ToggleWeapon(String name){
        super(name);
        mountType = ToggleWeaponMount::new;
        mirror = false;
    }

    public ToggleWeapon copy(){
        try{
            return (ToggleWeapon) clone();
        }catch(CloneNotSupportedException foul){
            throw new RuntimeException("absolutely horrible", foul);
        }
    }

    //stop as many methods as possible
    @Override
    public void drawOutline(Unit unit, WeaponMount mount){
        if(mount instanceof ToggleWeaponMount t){
            if(t.enabled) super.drawOutline(unit, mount);
            return;
        }
        super.drawOutline(unit, mount);
    }

    @Override
    public void draw(Unit unit, WeaponMount mount){
        if(mount instanceof ToggleWeaponMount t){
            if(t.enabled) super.draw(unit, mount);
            return;
        }
        super.draw(unit, mount);
    }

    @Override
    public void update(Unit unit, WeaponMount mount){
        if(mount instanceof ToggleWeaponMount t){
            if(t.enabled) super.update(unit, mount);
            return;
        }
        super.update(unit, mount);
    }

    @Override
    protected float bulletRotation(Unit unit, WeaponMount mount, float bulletX, float bulletY){
        if(mount instanceof ToggleWeaponMount t){
            if(t.enabled) return super.bulletRotation(unit, mount, bulletX, bulletY);
        }
        return super.bulletRotation(unit, mount, bulletX, bulletY);
    }

    @Override
    protected void shoot(Unit unit, WeaponMount mount, float shootX, float shootY, float rotation){
        if(mount instanceof ToggleWeaponMount t){
            if(t.enabled) super.shoot(unit, mount, shootX, shootY, rotation);
            return;
        }
        super.shoot(unit, mount, shootX, shootY, rotation);
    }

    @Override
    protected void bullet(Unit unit, WeaponMount mount, float xOffset, float yOffset, float angleOffset, Mover mover){
        if(mount instanceof ToggleWeaponMount t){
            if(t.enabled) super.bullet(unit, mount, xOffset, yOffset, angleOffset, mover);
            return;
        }
        super.bullet(unit, mount, xOffset, yOffset, angleOffset, mover);
    }

    @Override
    protected void handleBullet(Unit unit, WeaponMount mount, Bullet bullet){
        if(mount instanceof ToggleWeaponMount t){
            if(t.enabled) super.handleBullet(unit, mount, bullet);
            return;
        }
        super.handleBullet(unit, mount, bullet);
    }
}
