package yellow;

import arc.func.*;
import mindustry.*;
import yellow.util.*;

import java.util.*;

@SuppressWarnings("SameParameterValue")
public final class YellowJVM{

    private static String source = "sun.java.command";

    static void setSource(String newSource){
        source = newSource;
    }

    static boolean hasParameter(String argument){
        if(Vars.mobile) return false; //ah, mobile

        try{
            String[] args = SafeReflect.get(Vars.platform, "args");
            if(args != null) for(String arg : args){
                return Objects.equals(arg, "--" + argument);
            }
        }catch(Exception e){
            return System.getProperty(source, "none").contains("--" + argument);
        }

        return System.getProperty(source, "none").contains("--" + argument);
    }

    static boolean hasParameter(String argument, Cons<String> ifDetected){
        boolean s = hasParameter(argument);
        if(s) ifDetected.get(argument);
        return s;
    }

    static boolean hasParameter(String argument, Boolp alt, Cons<String> ifDetected){
        boolean s = hasParameter(argument);
        if(s) ifDetected.get(argument);
        if(alt.get()) ifDetected.get("alt");
        return s || alt.get();
    }
}
