package yellow.comp;

import arc.*;
import arc.graphics.g2d.*;
import arc.input.*;
import arc.math.*;
import arc.math.geom.*;
import arc.util.*;
import arc.util.io.*;
import ent.anno.Annotations.*;
import mindustry.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.entities.EntityCollisions.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.input.*;
import mindustry.type.*;
import yellow.*;
import yellow.entities.*;
import yellow.gen.*;

import static yellow.YellowSettingValues.*;

@EntityComponent(base = true)
@EntityDef({ItemEntityc.class, Hitboxc.class, Drawc.class, Posc.class, Velc.class, Rotc.class})
abstract class ItemEntityComp implements Hitboxc, Drawc, Posc, Velc, Rotc{

    @Import float x, y, drag, rotation;
    @Import Vec2 vel;

    transient Item item = Items.copper;
    transient int amount = 1;

    public void pickup(Itemsc entity){
        entity.addItem(item, amount);
        Fx.itemTransfer.at(x, y, 0f, entity);
        Fx.breakProp.at(x, y);
        remove();
    }

    @Replace
    public SolidPred solidity(){
        return EntityCollisions::solid;
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
        Groups.unit.each(Unitc::isPlayer, un -> {
            ItemStack carried = un.stack;
            if((gravitateOnEmptyInventory.get() && carried.item == null) || carried.amount <= 0 || (carried.item == item && un.acceptsItem(item))){
                if(Mathf.dst(x, y, un.x, un.y) < 8*5f) vel.trns(Angles.angle(x, y, un.x, un.y), 1f);

                if(Mathf.dst(x, y, un.x, un.y) < 8f || (Mathf.dst(x, y, un.x, un.y) < 8*12f && Mathf.dst(x, y, un.aimX, un.aimY) < 8*1f && Core.input.keyTap(KeyCode.mouseLeft))) pickup(un);
            }
        });
    }

    @Override
    public void draw(){
        if(item == null) return;
        Draw.z(Layer.block);
        Draw.rect(item.fullIcon, x, y, rotation);
    }

    @Override
    public void read(Reads read){
        Item i = Vars.content.item(read.s());
        if(i == null) i = Items.copper;

        item = i;
        amount = read.i();
        drag = read.f();
    }

    @Override
    public void write(Writes write){
        write.s(item.id);
        write.i(amount);
        write.f(drag);
    }
}
