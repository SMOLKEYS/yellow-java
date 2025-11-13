package yellow.async;

import arc.math.geom.*;
import arc.struct.*;
import arc.util.*;
import mindustry.*;
import mindustry.async.*;
import mindustry.async.PhysicsProcess.*;
import mindustry.async.PhysicsProcess.PhysicsWorld.*;
import mindustry.entities.*;
import mindustry.gen.*;

/** Exact copy of {@link PhysicsProcess} for any entity that implements {@link Physicsc}. */
@SuppressWarnings("FieldMayBeFinal")
public class PhysicsHandler<T extends Physicsc> implements AsyncProcess{
    private PhysicsWorld physics;
    private Seq<PhysicRef> refs = new Seq<>(false);
    private EntityGroup<T> group;

    public float collisionRadiusScale = 0.8f;

    public PhysicsHandler(EntityGroup<T> group){
        this.group = group;
    }

    @Override
    public void begin(){
        if(physics == null) return;
        boolean local = !Vars.net.client();

        //remove stale entities
        refs.removeAll(ref -> {
            if(!ref.entity.isAdded()){
                physics.remove(ref.body);
                ref.entity.physref(null);
                return true;
            }
            return false;
        });

        for(T entity : group){
            if(entity == null || (entity instanceof Unitc u && (u.type() == null || !u.type().physics))) continue;

            if(entity.physref() == null){
                PhysicsBody body = new PhysicsBody();
                body.x = entity.x();
                body.y = entity.y();
                body.mass = entity.mass();
                body.radius = entity.hitSize() * collisionRadiusScale;

                PhysicRef ref = new PhysicRef(entity, body);

                refs.add(ref);

                entity.physref(ref);

                physics.add(body);
            }

            //save last position
            PhysicRef ref = entity.physref();

            ref.body.layer = entity instanceof Unitc u ? u.collisionLayer() : 0;
            ref.x = entity.x();
            ref.y = entity.y();
            ref.body.local = local || entity.isLocal();
        }
    }

    @Override
    public void process(){
        if(physics == null) return;

        //get last position vectors before step
        for(PhysicRef ref : refs){
            //force set target position
            ref.body.x = ref.x;
            ref.body.y = ref.y;
        }

        physics.update();
    }

    @Override
    public void end(){
        if(physics == null) return;

        //move entities
        for(PhysicRef ref : refs){
            Physicsc entity = ref.entity;

            //move by delta
            entity.move(ref.body.x - ref.x, ref.body.y - ref.y);
        }
    }

    @Override
    public void reset(){
        if(physics != null){
            refs.clear();
            physics = null;
        }
    }

    @Override
    public void init(){
        reset();

        physics = new PhysicsWorld(Vars.world.getQuadBounds(new Rect()));
    }
}
