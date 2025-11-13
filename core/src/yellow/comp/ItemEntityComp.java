package yellow.comp;

import arc.*;
import arc.func.*;
import arc.graphics.g2d.*;
import arc.input.*;
import arc.math.*;
import arc.math.geom.*;
import arc.util.*;
import arc.util.io.*;
import ent.anno.Annotations.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.entities.EntityCollisions.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import yellow.content.*;
import yellow.entities.*;
import yellow.gen.*;
import yellow.math.*;

import static yellow.YellowSettingValues.*;

@EntityComponent
@EntityDef({ItemEntityc.class, PhysicsEntityc.class, Itemsc.class})
abstract class ItemEntityComp implements PhysicsEntityc, Itemsc{

    @Import float x, y, rotation;
    @Import Vec2 vel;
    @Import ItemStack stack = new ItemStack();

    int stackLimit = 1;

    public void pickup(Itemsc entity){
        if(!entity.acceptsItem(stack.item)) return;
        for(int i = 0; i < stack.amount; i++){
            entity.addItem(stack.item, 1);
            stack.amount--;
            if(stack.amount <= 0){
                Fx.breakProp.at(x, y);
                remove();
            }
        }
        Fx.itemTransfer.at(x, y, 0f, entity);
    }

    public boolean willGravitate(@Nullable Boolp extra){
        return gravitateItems.get() && (extra == null || extra.get());
    }

    @Replace
    public SolidPred solidity(){
        return EntityCollisions::solid;
    }

    @Override
    public int itemCapacity(){
        return stackLimit;
    }

    @Override
    public void add(){
        rotation = Mathf.random(360f);
    }

    @Override
    public void update(){
        //players only
        //does this sync?
        Groups.player.each(pl -> {
            Unit unit = pl.unit();
            if(unit == null) return;

            ItemStack carried = unit.stack;

            boolean acceptsItem = carried.item == stack.item && unit.acceptsItem(stack.item);

            if(!unit.hasItem() || acceptsItem){
                float dst = Mathf.dst(x, y, unit.x, unit.y);
                float aimDst = Mathf.dst(x, y, unit.aimX, unit.aimY);

                //click to collect
                if(dst < unit.hitSize + (8*12f) && aimDst < 8*1.5f && Core.input.keyTap(YellowBinds.pickupItem)) pickup(unit);

                //gravitate
                if(dst < unit.hitSize + (8*5f) && willGravitate(
                        () -> (!unit.hasItem() && gravitateOnEmptyInventory.get()) || (acceptsItem && unit.hasItem())
                )){
                    vel.trns(Angles.angle(x, y, unit.x, unit.y), 1);

                    //auto pickup if close enough
                    if(dst < unit.hitSize + 3f) pickup(unit);
                }
            }
        });
    }

    @Override
    public void draw(){
        if(stack.item == null) return;
        Draw.z(Layer.block);
        Draw.rect(stack.item.fullIcon, x, y, rotation);
    }

    @Override
    public void afterReadAll(){
        if(stack.item == null) remove();
    }
}
