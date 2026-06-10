package yellow;

import arc.*;
import arc.func.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.struct.*;
import arc.util.*;
import mindustry.*;
import mindustry.ai.*;
import mindustry.ai.Astar.*;
import mindustry.entities.*;
import mindustry.game.EventType.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.mod.*;
import mindustry.world.*;
import rhino.*;
import yellow.core.YellowEventType.*;
import yellow.spec.*;
import yellow.util.*;
import yellow.util.variable.SettingBoundVariable.*;

import java.util.*;

import static yellow.Yellow.*;

public class YellowDebug{

    static final LongSetting lastFileDate = new LongSetting("yellow-debug-lastfiledate", 1997L);

    static void loadDebug(){
        Events.run(YellowVarsPostInit.class, () -> {
            Date d = new Date(lastFileDate.get());
            Date nd = new Date(mod().file.lastModified());

            YellowVars.notifrag.showPersistentNotification(
                    Icon.wrench,
                    Strings.format(
                            "Yellow debug info:\n\nPast file time: @\n\nLoaded file: [gold]@[]\nFile time: @",
                            d,
                            mod().file.name(),
                            nd
                    )
            );

            YellowVars.notifrag.showPersistentNotification(
                    Icon.wrench,
                    Strings.format(
                            "Chaos stage: [red]@[]\nActive class: [magenta]@[]",
                            Chaos.stageIndex(),
                            null //Chaos.stage()
                    )
            );

            YellowVars.notifrag.showPersistentNotification(
                    Icon.wrench,
                    Strings.format(
                            "Mod info:\n\nName: [gold]@[]\nMinimum version: [blue]@[]\nMod version: [green]@[]\nActive class: [magenta]@[]",
                            Yellow.meta().internalName,
                            Yellow.meta().minGameVersion,
                            Yellow.meta().version,
                            mod().main
                    )
            );

            lastFileDate.set(nd.getTime());
        });
    }

    static Seq<Tile> cpath;
    static boolean initdraw, path;
    static Rand rand = new Rand();

    static Effect fly = new Effect(120f, e -> {
        try{
            Seq<Tile> st = e.data();
            int prog = Mathf.floor(Mathf.lerp(0, st.size, e.fin()));
            for(int i = 0; i < prog; i++){
                Tile tl = st.get(i);
                rand.setSeed(tl.x + tl.y + System.identityHashCode(tl));
                Draw.z(Layer.effect);
                Draw.color(Tmp.c1.set(rand.nextFloat(), rand.nextFloat(), rand.nextFloat(), 1f));
                Fill.circle(tl.x * 8, tl.y * 8, 5f);
            }
        }catch(Exception ignored){
        }
    });

    public static void drawPath(boolean persistPath, boolean copy, TileHeuristic th, Boolf<Tile> passable){
        path = persistPath;
        cpath = Astar.pathfind(Vars.player.unit().tileOn(), Vars.player.unit().team.core().tileOn(), th != null ? th : t -> 1f, passable != null ? passable : t -> true);
        if(copy) cpath = cpath.copy();
        fly.at(Vars.player.unit().x, Vars.player.unit().y, 0, cpath);
        if(!initdraw){
            Events.run(Trigger.draw, () -> {
                if(path) cpath.each(t -> {
                    rand.setSeed(t.x + t.y + System.identityHashCode(t));
                    Draw.color(Tmp.c1.set(rand.nextFloat(), rand.nextFloat(), rand.nextFloat(), 1f));
                    Fill.square(t.x * 8, t.y * 8, 4, Time.time * rand.nextFloat());
                });
            });
            initdraw = true;
        }
    }

    static String[] whitelist = {"yellow", "jdk", "java", "mindustry", "arc"};

    public static String printTrace(){
        // if this method is called from another method not part of the whitelist, throw an exception
        for(StackTraceElement stackTraceElement : Thread.currentThread().getStackTrace()){
            String conc = stackTraceElement.getClassName().split("\\.")[0];
            if(!Structs.contains(whitelist, conc)) throw new RuntimeException(Strings.format("Attempt to execute method by '@.@' not allowed", stackTraceElement.getClassName(), stackTraceElement.getMethodName()));
        }
        return "good";
    }
}
