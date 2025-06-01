package yellow.util;

import arc.*;

/** Wrapper class for {@link Settings} to prevent {@link ClassCastException}s when using the wrong methods to get values. */
public class SafeSettings{

    public static String getString(String key, String def){
        return getString(key, def, def);
    }

    public static String getString(String key, String def, String fallback){
        try{
            return Core.settings.getString(key, def);
        }catch(Exception e){
            return fallback;
        }
    }

    public static boolean getBool(String key, boolean def){
        return getBool(key, def, def);
    }

    public static boolean getBool(String key, boolean def, boolean fallback){
        try{
            return Core.settings.getBool(key, def);
        }catch(Exception e){
            return fallback;
        }
    }

    public static float getFloat(String key, float def){
        return getFloat(key, def, def);
    }

    public static float getFloat(String key, float def, float fallback){
        try{
            return Core.settings.getFloat(key, def);
        }catch(Exception e){
            return fallback;
        }
    }

    public static int getInt(String key, int def){
        return getInt(key, def, def);
    }

    public static int getInt(String key, int def, int fallback){
        try{
            return Core.settings.getInt(key, def);
        }catch(Exception e){
            return fallback;
        }
    }

    public static long getLong(String key, long def){
        return getLong(key, def, def);
    }

    public static long getLong(String key, long def, long fallback){
        try{
            return Core.settings.getLong(key, def);
        }catch(Exception e){
            return fallback;
        }
    }

    public static byte[] getBytes(String key, byte[] def){
        return getBytes(key, def, def);
    }

    public static byte[] getBytes(String key, byte[] def, byte[] fallback){
        try{
            return Core.settings.getBytes(key, def);
        }catch(Exception e){
            return fallback;
        }
    }
}
