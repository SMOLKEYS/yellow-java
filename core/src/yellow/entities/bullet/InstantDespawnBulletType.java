package yellow.entities.bullet;

import mindustry.content.*;
import mindustry.entities.bullet.*;

public class InstantDespawnBulletType extends EmptyBulletType{

    public InstantDespawnBulletType(){
        super();
        lifetime = 0f;
        instantDisappear = true;
        hitEffect = despawnEffect = shootEffect = Fx.none;
    }
}
