package yellow.internal.util;

import arc.util.*;

public class YLog{

    public static <T> T info(T log){
        Log.info(log);
        return log;
    }
}
