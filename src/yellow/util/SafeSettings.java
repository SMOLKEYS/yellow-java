package yellow.util;

import arc.*;

/** Wrapper class for {@link Settings} to prevent class cast exceptions when using the wrong methods to get values. */
public class SafeSettings{

    public static String getString(String key, String def, String fallback){
        try{
            return Core.settings.getString(key, def);
        }catch(Exception e){
            return fallback;
        }
    }

    public static boolean getBool(String key, boolean def, boolean fallback){
        try{
            return Core.settings.getBool(key, def);
        }catch(Exception e){
            return fallback;
        }
    }

    public static float getFloat(String key, float def, float fallback){
        try{
            return Core.settings.getFloat(key, def);
        }catch(Exception e){
            return fallback;
        }
    }

    public static int getInt(String key, int def, int fallback){
        try{
            return Core.settings.getInt(key, def);
        }catch(Exception e){
            return fallback;
        }
    }

    public static long getLong(String key, long def, long fallback){
        try{
            return Core.settings.getLong(key, def);
        }catch(Exception e){
            return fallback;
        }
    }

    public static byte[] getBytes(String key, byte[] def, byte[] fallback){
        try{
            return Core.settings.getBytes(key, def);
        }catch(Exception e){
            return fallback;
        }
    }
}
