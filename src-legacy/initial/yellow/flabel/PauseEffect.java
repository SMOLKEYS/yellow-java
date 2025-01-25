package yellow.flabel;

import arc.util.*;
import arc.flabel.*;

public class PauseEffect extends FEffect{

    public boolean finished = false;

    @Override
    protected void onApply(FLabel label, FGlyph glyph, int localIndex, float delta){
        //pauses at a char after the pause token, which is not intended
        //current use workaround would be "wor{pause}d" instead of "word{pause}"
        if(localIndex == 0 && !finished){
            finished = true;
            label.pause();
        }
    }
}
