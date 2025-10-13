package yellow.ui.fragments;

import arc.*;
import arc.flabel.*;
import arc.graphics.*;
import arc.scene.*;
import arc.scene.actions.*;
import arc.scene.event.*;
import arc.scene.ui.*;
import arc.scene.ui.layout.*;
import arc.struct.*;
import arc.util.*;
import mindustry.*;
import mindustry.graphics.*;
import mindustry.mod.*;
import mindustry.ui.*;

public class FirstLoadFragment implements CommonFragment{

    boolean cur = false;
    Seq<Runnable> queue = new Seq<>();

    @Override
    public void build(Group parent){
        cur = false;
        boolean b = Core.settings.getBoolOnce("yellow-first-load");
        if(b) return;

        parent.fill(s -> {
            s.name = "first load";
            s.visible(() -> true);
            s.touchable = Touchable.childrenOnly;
            s.background(Styles.black);
            s.center();

            s.update(s::toFront);

            Mods.ModMeta meta = Yellow.meta();

            s.table(v -> {
                v.touchable = Touchable.childrenOnly;

                Cell<FLabel> fcell = v.add(new FLabel(
                        Vars.tree.get("texts/notes.txt").readString().replace("-name-", Strings.stripColors(Vars.player.name != null ? Vars.player.name : Vars.steamPlayerName != null ? Vars.steamPlayerName : "Yeah, you") + "!")
                )).grow().left().pad(70);
                FLabel f = fcell.get();

                f.setWrap(true);

                v.row();
                v.add("Next").self(l -> {
                    Label ll = l.get();

                    ll.clicked(() -> {
                        if(f.hasEnded()){
                            if(cur){
                                s.actions(Actions.sequence(
                                        Actions.run(() -> {
                                            s.clearChildren();
                                            s.add(new FLabel("Good luck out there!"));
                                        }),
                                        Actions.delay(1),
                                        Actions.fadeOut(3),
                                        Actions.run(() -> queue.each(e -> Core.app.post(e))),
                                        Actions.remove()
                                ));
                            }else{
                                cur = true;
                                ll.setText("Proceed");
                                f.restart(Vars.tree.get("texts/notes2.txt").readString()
                                        .replace("-ver-", meta.version)
                                        .replace(
                                                "-type-",
                                                processMeta(meta)
                                        )
                                );
                            }
                        }
                    });
                    ll.update(() -> ll.setColor(f.hasEnded() ? Pal.accent : Color.white));
                });
            }).growX();
        });
    }

    public void query(Runnable entry){
        queue.add(entry);
    }

    public static String processMeta(Mods.ModMeta meta){
        int d = 0;

        for(int i = 0; i < meta.version.length(); i++){
            char ch = meta.version.charAt(i);
            if(Character.isDigit(ch) || ch == '.') d++;
        }

        String ver = meta.version.substring(d);

        switch(ver){
            case "S" -> {
                return "[accent]Stable[]";
            }
            case "B" -> {
                return "[cyan]Beta[]";
            }
            case "D" -> {
                return "[purple]Developer Build[]";
            }
        }

        return "[gray]Unknown[]";
    }
}
