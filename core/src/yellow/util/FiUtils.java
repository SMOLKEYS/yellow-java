package yellow.util;

import arc.files.*;
import arc.func.*;

public class FiUtils{
    public static void walkAll(Fi input, Cons<Fi> cons){
        if(input.isDirectory()){
            for(Fi file : input.list()){
                walkAll(file, cons);
                cons.get(file);
            }
        }
    }
}
