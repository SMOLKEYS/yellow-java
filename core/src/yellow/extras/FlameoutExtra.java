package yellow.extras;

import arc.*;
import arc.audio.*;
import arc.math.*;
import arc.scene.*;
import arc.scene.actions.*;
import arc.scene.style.*;
import arc.scene.ui.*;
import arc.scene.ui.layout.*;
import arc.struct.*;
import arc.util.*;
import mindustry.*;
import mindustry.gen.*;
import yellow.*;
import yellow.Yellow.*;
import yellow.content.*;
import yellow.spec.*;
import yellow.ui.fragments.DialogFragment.*;
import yellow.ui.scene.*;
import yellow.util.*;

import java.util.concurrent.atomic.*;

import static yellow.YellowVars.*;

public class FlameoutExtra extends ExtraAddition{

    @Override
    public void varsInit(){
        if(Vars.mods.getMod("flameout") != null && !YellowJVM.hasParameter("no-salvation")){
            int id = ltfrag.add("@yellow.salvation-check", Icon.add, true);
            Time.runTask(60*3f, () -> {
                String name = Vars.ui.menufrag.getClass().getCanonicalName();
                if(Stringy.contains(name, "flame", "AltMenuFragment", "Stage2")){
                    ltfrag.reconfigure(id, "@yellow.salvation-pass", Icon.warning, false);
                    ltfrag.hide(id, 300f);

                    dialogfrag.initiate(true, TextEntry.fromSimpleStrings(BundleUtils.getSequence("yellow.flame-extra-")));

                    Seq<Element> tbl = new Seq<>();

                    overlayGroup.fill(salva -> {
                        tbl.add(salva);
                        FLabelTextButton button = new FLabelTextButton(Core.bundle.get("yellow.salvation-option-1"));
                        Drawable ico = Icon.warning;
                        float size = ico.imageSize() / Scl.scl(1f);
                        button.add(new Image(ico)).size(size);
                        button.getCells().reverse();
                        button.getCell(button.getFLabel()).padLeft(size *= 4);

                        button.clicked(() -> {
                            dialogfrag.stop();
                            Chaos.stopAudioBus();
                            Chaos.stopSoundControl();
                            Chaos.eraseMenu();
                            salva.clearChildren();
                            select(tbl, Mathf.chance(0.3) || Yellow.debug);
                        });
                        salva.add(button).width(Core.graphics.getWidth() / 2f).center();

                        button.actions(Actions.alpha(0f), Actions.alpha(1f, 1.2f, Interp.fade));
                        salva.row();
                        TextButton other = salva.button("@yellow.salvation-option-2", Icon.bookOpen, () -> {
                            dialogfrag.reinitiate(false, TextEntry.fromSimpleStrings(BundleUtils.getSequence("yellow.flame-extra-ignore-")));

                            tbl.each(e -> {
                                Vars.ui.menuGroup.removeChild(e);
                                overlayGroup.removeChild(e);
                            });
                        }).width(Core.graphics.getWidth() / 2f).center().get();
                        other.actions(Actions.alpha(0f), Actions.alpha(1f, 1.2f, Interp.fade));
                    });
                }else{
                    ltfrag.reconfigure(id, "@yellow.salvation-skip", Icon.admin, false);
                    ltfrag.hide(id, 300f);
                }
            });
        }
    }

    private void select(Seq<Element> tbl, boolean chance){
        AudioBus bus = new AudioBus();
        if(chance){
            Time.runTask(60f, () -> {
                YellowSounds.chicken.setBus(bus);
                YellowSounds.chicken.play(2f);

                AtomicReference<Table> cont = new AtomicReference<>();
                Vars.ui.menuGroup.fill(e -> {
                    tbl.add(e);
                    cont.set(e);
                    e.defaults().grow().fill();
                    Image chicken = e.image(Core.atlas.find("yellow-java-chicken")).grow().fill().get();
                    chicken.color.a = 0;
                    chicken.actions(Actions.color(Tmp.c1.set(1, 1, 1, 1), 2.3f));
                });

                Time.runTask(60*5f, () -> {
                    dialogfrag.initiate(true, TextEntry.with("Ah, wait, wrong one. My apologies!"));
                });

                Time.runTask(60*6.12f, () -> {
                    tbl.remove(cont.get());
                    salvation(tbl, bus);
                });
            });
        }else{
            salvation(tbl, bus);
        }
    }

    private void salvation(Seq<Element> tbl, AudioBus bus){
        dialogfrag.stop();
        Chaos.eraseMenu();
        YellowSounds.DAMN.setBus(bus);
        YellowSounds.DAMN.play(2f);
        Sound snd = new RandomSound(Sounds.wind, Sounds.wind2, Sounds.windHowl);
        for(int i = 0; i < 5; i++){
            snd.setBus(bus);
            snd.loop(1f, i, 0f);
        }
        overlayGroup.clear();

        Vars.ui.menuGroup.fill(e -> {
            tbl.add(e);
            e.defaults().grow().fill();
            Stack s = e.stack(new Image(Core.atlas.white()), new Image(Core.atlas.find("yellow-java-salvation"))).grow().fill().get();
            Image salvation = (Image) s.getChildren().peek();
            salvation.color.a = 0f;
            salvation.actions(Actions.color(Tmp.c1.set(1, 1, 1, 1), 2f));
        });

        Time.runTask(60*4.3f, () -> {
            Time.runTask(20f, () -> {
                Core.settings.remove("flame-special");
                Core.app.exit();
            });
        });
    }
}
