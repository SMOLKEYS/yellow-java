package yellow.world.blocks.storage;

import mindustry.gen.*;
import mindustry.type.*;
import mindustry.world.blocks.storage.*;

public class FalseCore extends StorageBlock{

    public FalseCore(String name){
        super(name);
        coreMerge = false;
    }

    public class FalseCoreBuild extends StorageBuild{

        @Override
        public boolean acceptItem(Building source, Item item){
            return false;
        }

        @Override
        public boolean canUnload(){
            return false;
        }

        @Override
        public int getMaximumAccepted(Item item){
            return Integer.MAX_VALUE;
        }

        @Override
        public int removeStack(Item item, int amount){
            return 0;
        }
    }
}
