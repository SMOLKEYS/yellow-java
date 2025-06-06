package yellow.compat;

import arc.flabel.*;

//what the FUCK android
public class AndroidCompatibilityClasses{

    public static class AFListener implements FListener{
        @Override
        public void end(){
            // prevents the following error:
            // AbstractMethodError: abstract method "void arc.flabel.FListener.end()"
        }
    }
}
