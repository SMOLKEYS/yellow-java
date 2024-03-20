package yellow.ui.fragments;

import arc.*;
import arc.flabel.*;
import arc.graphics.*;
import arc.input.*;
import arc.scene.*;
import arc.scene.ui.*;
import arc.util.*;
import mindustry.*;
import mindustry.graphics.*;
import mindustry.ui.*;
import yellow.game.*;
import yellow.goodies.vn.*;
import yellow.util.*;

//TODO
public class YellowDialogueBoxFragment{

    private boolean visible = false,
    playing = false,
    auto = false,
    hasEnded = false;

    private float autoTime = 0f, autoskipTimer = -1f;

    private Label nameText;
    private FLabel dialogue;

    private Dialogue activeDialogue;

    public YellowDialogueBoxFragment(){
        Vars.control.input.addLock(() -> playing);
    }

    public void build(Group parent){
        parent.fill(t -> {
            t.name = "dialogue box";
            t.bottom();
            t.visible(() -> visible);

            t.table(name -> {
                name.name = "name";
                name.background(Styles.black8);
                name.center();

                nameText = name.add("[cyan]Smol[]").grow().labelAlign(Align.center).get();
            }).growX().height(50).row();
            t.table(main -> {
                main.name = "main";
                main.background(Styles.black8);
                main.center().top().marginTop(60);

                dialogue = main.add(new FLabel("Now, why are you here?")).labelAlign(Align.center).get();

                main.clicked(() -> {
                    auto = false;
                    prog();
                });

                main.update(() -> {
                    if(activeDialogue != null){
                        if(dialogue.hasEnded() && !hasEnded){
                            hasEnded = true;
                            activeDialogue.currentData().getEndSfx().play(activeDialogue.currentData().getSfxVol());
                        }

                        if(dialogue.hasEnded() && activeDialogue.currentData().getAutoskip()) autoskipTimer += Time.delta;

                        if(autoskipTimer > activeDialogue.currentData().getAutoskipDelay()){
                            autoskipTimer = -1f;
                            prog();
                        }
                    }

                    if(auto){
                        if(dialogue.hasEnded()) autoTime += Time.delta;
                        if(autoTime > 240f){
                            autoTime = 0f;
                            prog();
                        }
                    }

                    if(Core.input.keyTap(KeyCode.enter)){
                    	auto = false;
                    	prog();
                    }
                });
            }).growX().height(300).row();
            t.table(b -> {
                b.name = "buttons";
                b.background(Styles.black8);
                b.defaults().width(150).pad(20);

                b.button("Auto", () -> auto = !auto).update(buh -> {
                    if(auto){
                        buh.getLabel().setColor(YellowUtils.pulse(Pal.accent, 10f));
                    }else{
                        buh.getLabel().setColor(Color.white);
                    }
                });
                //b.button("Skip", () -> {});
                b.button("Previous", this::unprog);
            }).growX().height(50).row();
        });
    }

    public void update(){
        if(activeDialogue != null){
            InteractiveCharacter cc = activeDialogue.currentCharacter();
            cc.update(nameText);
        }
    }

    public void show(Dialogue dialogu){
        activeDialogue = dialogu;
        autoTime = 0f;
        autoskipTimer = -1f;
        hasEnded = false;
        activeDialogue.currentData().getStartSfx().play(activeDialogue.currentData().getSfxVol());
        nameText.setText(activeDialogue.currentCharacter().nameLocalized());
        dialogue.restart(activeDialogue.currentString());
        visible = true;
        playing = true;
        Events.fire(new YEventType.DialogueStartEvent(activeDialogue));
    }

    public void hide(){
        activeDialogue = null;
        nameText.setText("<nothing>");
        dialogue.restart("nothing to see here...");
        visible = false;
        playing = false;
        auto = false;
        hasEnded = false;
    }

    private void prog(){
    	if(activeDialogue != null){
            hasEnded = false;
            if(dialogue.isPaused()){
                dialogue.resume();
                return;
            }
    		if(!dialogue.hasEnded()){
    			dialogue.skipToTheEnd();
                return;
    		}else{
                dialogue.cancelSkipping();
            }
    		activeDialogue.next();
    		if(activeDialogue.completed()){
                Events.fire(new YEventType.DialogueEndEvent(activeDialogue));
    			hide();
    			return;
    		}
            activeDialogue.currentData().getStartSfx().play(activeDialogue.currentData().getSfxVol());
    		nameText.setText(activeDialogue.currentCharacter().nameLocalized());
    		nameText.setColor(activeDialogue.currentCharacter().color);
    		dialogue.restart(activeDialogue.currentString());
    	}
    }

    private void unprog(){
    	if(activeDialogue != null){
    	    auto = false;
            hasEnded = false;
    		activeDialogue.prev();
    		nameText.setText(activeDialogue.currentCharacter().nameLocalized());
    		nameText.setColor(activeDialogue.currentCharacter().color);
    		dialogue.restart(activeDialogue.currentString());
    	}
    }

}
