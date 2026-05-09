package yellow.util;

import arc.struct.*;
import arc.util.*;

public class Timekeeping{
    private static final Seq<Timekeeper> timekeepers = new Seq<>(Timekeeper.class);

    public static Timekeeper[] ofSeconds(float... times){
        timekeepers.clear();
        for(float time : times){
            timekeepers.add(Timekeeper.ofSeconds(time));
        }
        return timekeepers.toArray();
    }

    public static Timekeeper[] ofTicks(float... times){
        timekeepers.clear();
        for(float time : times){
            timekeepers.add(Timekeeper.ofTicks(time));
        }
        return timekeepers.toArray();
    }

    public static Timekeeper[] ofMillis(long... times){
        timekeepers.clear();
        for(long time : times){
            timekeepers.add(Timekeeper.ofMillis(time));
        }
        return timekeepers.toArray();
    }
}
