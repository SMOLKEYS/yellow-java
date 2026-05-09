package yellow.content;

import arc.*;
import arc.struct.*;
import mindustry.game.EventType.*;
import yellow.*;
import yellow.util.*;
import yellow.util.Inputs.*;

public class YellowCodes{

    public static CodeSequence eternalWhimsy;

    public static void load(){
        eternalWhimsy = new CodeSequence(Inputs.keySequenceFromString(45f, true, "in my eternal whimsy"), () -> {
            YellowVars.notifrag.showNotification("i will always be a silly fella");
        });

        Events.run(Trigger.update, () -> CodeSequence.sequences.each(CodeSequence::update));
    }

    public static class CodeSequence{
        public static final Seq<CodeSequence> sequences = new Seq<>();

        public InputSequence inputs;
        public Runnable success;

        public CodeSequence(InputSequence inputs, Runnable success){
            this.inputs = inputs;
            this.success = success;

            sequences.add(this);
        }

        public void update(){
            if(inputs.poll()) success.run();
        }
    }
}
