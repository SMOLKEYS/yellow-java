package yellow.util;

public class Validator{

    public static void notNull(Object param){
        if(param == null) throw new RuntimeException("what the FUCK are you doing");
    }
}
