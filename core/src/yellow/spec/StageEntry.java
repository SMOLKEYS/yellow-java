package yellow.spec;

import arc.func.*;
import arc.struct.*;
import arc.util.*;
import yellow.*;

public class StageEntry{

    public enum ProgressionType{
        // timer progression, events occur in a generally fixed order at specific times
        timer,
        // level progression, events progress after completing certain requirements
        levels
    }

    public static class ProgressPoint{
        // conditions required to complete this progress point
        public Boolp conditions;
        // persistence - progress point stays completed when conditions are met once
        public boolean persistent;
        // parent progress point - if defined, this progress point will be locked behind it
        private @Nullable ProgressPoint parent;
        // child progress point (DO NOT MODIFY)
        private @Nullable ProgressPoint child;

        private boolean wasUnlocked;

        public ProgressPoint(Boolp conditions, boolean persistent){
            this(conditions, null, persistent);
        }

        public ProgressPoint(Boolp conditions, @Nullable ProgressPoint parent, boolean persistent){
            this.conditions = conditions;
            this.parent = parent;
            this.persistent = persistent;

            if(parent != null) parent.child = this;
        }

        public boolean canBeUnlocked(){
            return parent == null || parent.unlocked();
        }

        public boolean unlocked(){
            boolean c = conditions.get();
            if(persistent && c) return wasUnlocked = true;
            return (wasUnlocked && persistent) || c;
        }

        public ProgressPoint traceRoot(){
            if(parent == null) return null;
            ProgressPoint tmp = parent;

            while(tmp.parent != null) tmp = tmp.parent;
            return tmp;
        }
    }
}
