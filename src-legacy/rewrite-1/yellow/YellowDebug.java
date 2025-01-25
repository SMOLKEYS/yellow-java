package yellow;

import arc.util.*;

public class YellowDebug{
    private static final Object[] empty = {};

    public static void info(Object data){
        if(Yellow.debug) Log.info(data);
    }

    public static void info(String text, Object... replaces){
        if(Yellow.debug) Log.info(text, replaces);
    }

    public static void warn(String text, Object... replaces){
        if(Yellow.debug) Log.warn(text, replaces);
    }

    public static void err(String text, Object replaces){
        if(Yellow.debug) Log.err(text, replaces);
    }

    public static void err(Throwable th){
        if(Yellow.debug) Log.err(th);
    }

    public static void err(String text, Throwable th){
        if(Yellow.debug) Log.err(text, th);
    }
}
