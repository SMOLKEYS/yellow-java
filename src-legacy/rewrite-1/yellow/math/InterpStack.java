package yellow.math;

import arc.math.*;

/** An interp class for easily stacking multiple interpolation types. Note that placement order matters. */
public class InterpStack implements Interp{
    public static final InterpStack pow10OutSlope = new InterpStack(Interp.slope, Interp.pow10Out);

    public Interp[] interps;

    public InterpStack(Interp... interps){
        this.interps = interps;
    }

    @Override
    public float apply(float a){
        for(Interp i: interps) a = i.apply(a);

        return a;
    }
}
