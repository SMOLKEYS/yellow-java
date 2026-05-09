package yellow.spec.phases;

import arc.*;
import arc.math.*;
import arc.struct.*;
import arc.util.*;
import mindustry.*;
import mindustry.entities.units.*;
import mindustry.game.EventType.*;
import mindustry.gen.*;
import yellow.spec.*;
import yellow.spec.Chaos.*;
import yellow.util.*;

import java.util.concurrent.atomic.*;

public class Phase1 extends Phase{

    private static final Timekeeper[] times = Timekeeping.ofSeconds(
            60f*5f,
            60f,
            60f
    );

    private static final float[] decrements = {60f, 60f};

    private static final boolean[] phases = {false, false};

    private static final Seq<Unit> units = new Seq<>(Unit.class);
    private static ObjectMap<Unit, Seq<WeaponMount>> mounts = new ObjectMap<>();
    private static final Seq<StackTraceElement> stk = new Seq<>(StackTraceElement.class);


    public Phase1(){
        for(Timekeeper time : times){
            time.reset();
        }

        Events.run(Trigger.update, () -> {
            if(times[0].poll()){
                for(Timekeeper time : times){
                    time.reset();
                }
                phases[0] = true;

                Unit player = Vars.player.unit();
                Chaos.eraseUI();
                Chaos.addInputLock();
                Chaos.stopAudioBus();
                Chaos.stopSoundControl();
                Chaos.hideAllDialogs(true);
                if(player != null) Chaos.moveCamera(player);

                return;
            }

            if(phases[0]) return;

            if(times[1].poll()){
                if(decrements[0] > 5f) times[1] = Timekeeper.ofSeconds(Math.max(decrements[0] -= 10f, 5f));

                Groups.unit.each(unit -> {

                });
            }
        });
    }

    public static void crash(){
        AtomicReference<Exception> exp = new AtomicReference<>();
        try{
            //gaslighting errors :teehee:
//            for(int i = 0; i < 50; i++){
//                Mod m = Vars.mods.list().select(filt -> filt.main != null && filt.main.getClass() != Yellow.class).random().main;
//                m.init();
//                m.loadContent();
//                m.packSprites(null);
//            }
            //whichever breaks first
            Core.atlas.getTextures().get(null);
        }catch(Exception e){
            exp.set(e);
        }

        Timer.schedule(() -> Core.app.post(() -> {
            stk.add(new StackTraceElement("i.cant.remember", "<init>", "remember.java", 1023));

            for(int i = 0; i < Mathf.random(15, 100); i++){
                stk.add(new StackTraceElement(Stringy.generateId(Mathf.random(20, 80)), Stringy.generateId(Mathf.random(5, 10)), Mathf.chance(0.2) ? null : Stringy.generateId(Mathf.random(10, 25)), Mathf.chance(0.2) ? -2 : Mathf.random(99999)));
            }

            exp.get().setStackTrace(stk.toArray(StackTraceElement.class));
            SafeReflect.set(Throwable.class, exp.get(), "cause", null);
            SafeReflect.set(Throwable.class, exp.get(), "backtrace", null);
            SafeReflect.set(Throwable.class, exp.get(), "detailMessage", "something happened");

            Core.app.post(() -> Threads.throwAppException(exp.get()));
        }), 0.4f);
    }
}
