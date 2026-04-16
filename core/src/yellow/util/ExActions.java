package yellow.util;

import arc.scene.*;
import arc.scene.actions.*;
import arc.struct.*;

public class ExActions{

    private static final Seq<Action> tmpSeq = new Seq<>();

    public static RepeatAction loopSequence(int count, Action... actions){
        return Actions.repeat(count, Actions.sequence(actions));
    }

    public static RepeatAction foreverSequence(Action... actions){
        return Actions.forever(Actions.sequence(actions));
    }
}
