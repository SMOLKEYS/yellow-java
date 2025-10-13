package yellow.cutscene.controllers;

import arc.func.*;
import arc.math.*;
import arc.util.*;
import mindustry.*;
import mindustry.core.*;

/** A controller for changing the camera zoom. */
public class CameraZoomController extends CutsceneController<CameraZoomController>{

    /** The base zoom to start on. -1 assumes {@link Renderer#getScale()}. */
    public float baseZoom = -1f;
    /** The target camera zoom. Clamped between zero and {@link Renderer#maxScale() the zoom limit}. Using -1 automatically sets it to the {@link Renderer#getScale() current camera zoom}. */
    public float zoom = -1f;
    /** The amount of time used when changing the camera zoom. */
    public float time;

    /** Interpolation used when zooming. */
    public Interp interp = Interp.linear;

    private float curTime, curZoom, finalZoom;

    private float clamp(float z){
        return Mathf.clamp(z, 0, Vars.renderer.maxScale());
    }

    @Override
    public CameraZoomController self(Cons<CameraZoomController> cons){
        cons.get(this);
        return this;
    }

    @Override
    public void init(){
        curZoom = baseZoom > 0 ? baseZoom : Vars.renderer.getScale();
        finalZoom = zoom != -1 ? clamp(zoom) : curZoom;
    }

    @Override
    public void onSkip(){
        //you truly are insane if your renderer scale limit dynamically changes.
        Vars.renderer.setScale(clamp(finalZoom));
    }

    @Override
    public void update(Cutscenes mainController){
        curTime += Time.delta;
        mainController.setCameraZoom(Mathf.lerp(curZoom, clamp(finalZoom), progress(interp)));
    }

    @Override
    public boolean isFinished(){
        return Vars.renderer.getScale() == finalZoom;
    }

    @Override
    public float progress(){
        return Mathf.clamp(curTime / time);
    }

    @Override
    public void reset(){
        super.reset();
        zoom = -1f;
        time = 0f;
        curTime = curZoom = finalZoom = 0f;
        interp = Interp.linear;
    }
}
