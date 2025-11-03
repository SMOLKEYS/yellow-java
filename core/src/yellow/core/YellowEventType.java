package yellow.core;

import arc.util.*;
import mindustry.gen.*;
import yellow.gen.*;

public class YellowEventType{
    public static class YellowPostInit{}
    public static class YellowVarsPreInit{}
    public static class YellowVarsPostInit{}

    public static class ItemEntityCreateEvent{
        public final ItemEntity item;
        //possible for both to be null
        //not possible for both to be not null
        public final @Nullable Building building;
        public final @Nullable Unit unit;

        public ItemEntityCreateEvent(ItemEntity item){
            this(item, null, null);
        }

        public ItemEntityCreateEvent(ItemEntity item, Building building){
            this(item, building, null);
        }

        public ItemEntityCreateEvent(ItemEntity item, Unit unit){
            this(item, null, unit);
        }

        public ItemEntityCreateEvent(ItemEntity item, Building building, Unit unit){
            this.item = item;
            this.building = building;
            this.unit = unit;
        }
    }
}
