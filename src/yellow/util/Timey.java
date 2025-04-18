package yellow.util;

import arc.util.*;

@SuppressWarnings("unused")
public class Timey{
    public static long second = 1000L,
    minute = 60*1000L,
    hour = 60*60*1000L,
    day = 60*60*24*1000L,
    febLeapMonth = 60*60*24*29*1000L,
    standardMonth = 60*60*24*30*1000L,
    longMonth = 60*60*24*31*1000L;

    public static void runLoop(Runnable r, float intervalSeconds){
        Timer.schedule(r, 0, intervalSeconds, -1);
    }
}
