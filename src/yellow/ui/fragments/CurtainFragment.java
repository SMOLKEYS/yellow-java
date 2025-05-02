package yellow.ui.fragments;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.scene.*;
import arc.scene.event.*;
import arc.scene.ui.layout.*;
import arc.util.*;
import yellow.math.*;
import yellow.ui.*;

public class CurtainFragment implements CommonFragment{

    public float transitionValue = 0f;
    public float curtainSpan = 0.22f;
    public float curtainSpanTime = 60f;
    public Interp enterExitInterp = Interp.pow3Out;

    private float curtainScl, curtainTimer = curtainSpanTime, lastCurtainSpan;

    @Override
    public void build(Group parent){
        parent.addChild(new WidgetGroup(){{
            name = "cutscene curtains";
            color.a = 0;
            fillParent = true;
            touchable = Touchable.disabled;
        }
            @Override
            public void draw(){
                super.draw();

                if(curtainSpan != lastCurtainSpan){
                    lastCurtainSpan = curtainSpan;
                    curtainTimer = 0f;
                }

                curtainTimer += Time.delta;
                curtainScl = Mathy.lerpc(curtainScl, curtainSpan / 2f, curtainTimer, curtainSpanTime);

                //ref: https://github.com/Yuria-Shikibe/NewHorizonMod/blob/main/src/newhorizon/expand/cutscene/components/CutsceneUI.java#L129
                float heightC = height * curtainScl * enterExitInterp.apply(transitionValue);

                Draw.color(Color.black);
                Fill.quad(0,  0, 0, heightC, width, heightC, width, 0);
                Fill.quad(0, height, 0, height - heightC, width, height - heightC, width, height);
            }
        });
    }

    public void resizeCurtains(float span){
        curtainSpan = span;
    }

    public void resizeCurtainsInstant(float span){
        curtainSpan = span;
        curtainScl = span;
        lastCurtainSpan = span;
        curtainTimer = curtainSpanTime;
    }
}
