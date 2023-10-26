package yellow.math;

import arc.math.Interp.*;

public class YInterp{
    public static Exp[] exp = new Exp[10];

    static{
        for(int i = 1; i < exp.length; i++){
            exp[i] = new Exp(2, i);
        }
    }
}
