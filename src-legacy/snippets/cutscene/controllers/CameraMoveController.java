package yellow.cutscene.controllers;

import arc.*;
import arc.func.*;
import arc.math.*;
import arc.math.geom.*;
import arc.util.*;

/** A controller for moving the camera. */
@SuppressWarnings("unused")
public class CameraMoveController extends CutsceneController<CameraMoveController>{

    /** The target position to move the camera to. Can be null. */
    public Prov<Position> target;
    /** The target x/y to move the camera to.
     * Automatically set if {@link #target} is set. Using {@link MoveMode#by} will move the camera BY the specified x/y amount instead. */
    public float x, y;
    /** The amount of time used when moving the camera. */
    public float time;

    /** Interpolation used by the controller. */
    public Interp interp = Interp.linear;
    /** Move mode used by the camera movement controller. */
    public MoveMode moveMode = MoveMode.to;

    private float initX, initY, finalX, finalY;
    private float curTime;

    @Override
    public CameraMoveController self(Cons<CameraMoveController> cons){
        cons.get(this);
        return this;
    }

    @Override
    public void init(){
        initX = Core.camera.position.x;
        initY = Core.camera.position.y;

        if(target != null){
            x = target.get().getX();
            y = target.get().getY();
        }

        switch(moveMode){
            case by -> {
                finalX = Core.camera.position.x + x;
                finalY = Core.camera.position.y + y;
            }
            case to -> {
                finalX = x;
                finalY = y;
            }
        }
    }

    @Override
    public void onSkip(){
        Core.camera.position.set(finalX, finalY);
    }

    @Override
    public void update(Cutscenes mainController){
        curTime += Time.delta;
        Core.camera.position.x = Mathf.lerp(initX, finalX, progress(interp));
        Core.camera.position.y = Mathf.lerp(initY, finalY, progress(interp));
    }

    @Override
    public boolean isFinished(){
        return Core.camera.position.within(finalX, finalY, 3f);
    }

    @Override
    public float progress(){
        return Mathf.clamp(curTime / time);
    }

    @Override
    public Object data(){
        return target;
    }

    @Override
    public void reset(){
        super.reset();
        target = null;
        x = y = 0f;
        time = 0f;
        interp = Interp.linear;
        moveMode = MoveMode.to;
        initX = initY = finalX = finalY = 0f;
        curTime = 0f;
        pool = null;
    }

    public enum MoveMode{
        /** Move the camera TO the target position. */
        to,
        /** Move the camera BY the specified amount. */
        by
    }
}
