package yellow.flabel;

import arc.flabel.*;

public class FRegistry{

    public static void load(){
        FConfig.registerEffect("pause", PauseEffect::new);
    }
}
