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
import yellow.entities.*;
import yellow.gen.*;
import yellow.math.*;

import static yellow.YellowSettingValues.*;

@EntityComponent
@EntityDef({ItemEntityc.class, Hitboxc.class, Drawc.class, Posc.class, Velc.class, Rotc.class, Itemsc.class})
abstract class ItemEntityComp implements Hitboxc, Drawc, Posc, Velc, Rotc, Itemsc{

    @Import float x, y, drag, rotation, hitSize;
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
        YellowGroups.item.add((ItemEntityc) this);
    }

    @Override
    public void remove(){
        YellowGroups.item.add((ItemEntityc) this);
    }

    @Override
    public void update(){
        Groups.unit.each(Unitc::isPlayer, unit -> {
            ItemStack carried = unit.stack;

            boolean acceptsItem = carried.item == stack.item && unit.acceptsItem(stack.item);

            if(!unit.hasItem() || acceptsItem){
                float dst = Mathf.dst(x, y, unit.x, unit.y);

                //click to collect
                if(dst < 8*12f && Mathf.dst(x, y, unit.aimX, unit.aimY) < 8*1.5f && Core.input.keyTap(KeyCode.mouseLeft)) pickup(unit);

                //gravitate
                if(dst < 8*5f && willGravitate(
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
    public void read(Reads read){
        drag = read.f();
    }

    @Override
    public void write(Writes write){
        write.f(drag);
    }

    @Override
    public void afterReadAll(){
        if(stack.item == null) remove();
    }
}
