package yellow.entities;

import arc.*;
import arc.math.geom.*;
import arc.util.*;
import mindustry.gen.*;
import mindustry.type.*;
import yellow.core.YellowEventType.*;
import yellow.gen.*;

public class ItemEntitySpawner{

    public static ItemEntity spawn(Item item, int amount, int stackLimit, float x, float y, @Nullable Vec2 presetVelocity, float drag, @Nullable Building originBuilding, @Nullable Unit originUnit){
        ItemEntity en = ItemEntity.create();
        en.stack.item = item;
        en.stack.amount = amount;
        en.stackLimit(stackLimit);
        en.x(x);
        en.y(y);
        if(presetVelocity != null) en.vel.set(presetVelocity);
        en.drag(drag);
        en.add();
        Events.fire(new ItemEntityCreateEvent(en, originBuilding, originUnit));
        return en;
    }

    public static ItemEntity spawn(Item item, int amount, int stackLimit, float x, float y, @Nullable Vec2 presetVelocity, float drag){
        return spawn(item, amount, stackLimit, x, y, presetVelocity, drag, null, null);
    }

    public static ItemEntity spawn(Item item, int amount, int stackLimit, float x, float y){
        return spawn(item, amount, stackLimit, x, y, null, 0f);
    }

    public static ItemEntity spawn(Item item, float x, float y, @Nullable Vec2 presetVelocity, float drag, @Nullable Building originBuilding, @Nullable Unit originUnit){
        return spawn(item, 1, 1, x, y, presetVelocity, drag, originBuilding, originUnit);
    }

    public static ItemEntity spawn(Item item, float x, float y, @Nullable Vec2 presetVelocity, float drag){
        return spawn(item, x, y, presetVelocity, drag, null, null);
    }

    public static ItemEntity spawn(Item item, float x, float y){
        return spawn(item, x, y, null, 0f);
    }


}
