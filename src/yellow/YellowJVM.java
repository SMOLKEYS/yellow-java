package yellow;

import arc.func.*;
import mindustry.*;

import java.util.*;

@SuppressWarnings("SameParameterValue")
public final class YellowJVM{

    private static String source = "sun.java.command";

    static void setSource(String newSource){
        source = newSource;
    }

    static boolean hasParameter(String argument){
        if(Vars.mobile) return false; //ah, mobile

        //ah, java
        if(Objects.equals(System.getProperty(source, "none"), "none")) return false;

        return System.getProperty(source).contains("--" + argument);
    }

    static boolean hasParameter(String argument, Cons<String> ifDetected){
        boolean s = hasParameter(argument);
        if(s) ifDetected.get(argument);
        return s;
    }
}
