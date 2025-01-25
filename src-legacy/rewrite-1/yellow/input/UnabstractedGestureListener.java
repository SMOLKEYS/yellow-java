package yellow.input;

import arc.input.*;
import arc.math.geom.*;

/** A {@link arc.input.GestureDetector.GestureListener} with all abstract methods overridden to accommodate for Android compatibility. */
public class UnabstractedGestureListener implements GestureDetector.GestureListener{

    @Override
    public boolean touchDown(float x, float y, int pointer, KeyCode button){
        return false; //why
    }

    @Override
    public boolean fling(float velocityX, float velocityY, KeyCode button){
        return false; //whY
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY){
        return false; //wHY
    }

    @Override
    public boolean panStop(float x, float y, int pointer, KeyCode button){
        return false; //WHY
    }

    @Override
    public boolean zoom(float initialDistance, float distance){
        return false; //ARE YOU FUCKING WITH ME RIGHT NOW
    }

    @Override
    public boolean pinch(Vec2 initialPointer1, Vec2 initialPointer2, Vec2 pointer1, Vec2 pointer2){
        return false; //WHY IS THIS NEEDED
    }

    @Override
    public void pinchStop(){
        //ARE YOU SERIOUS
    }

    @Override
    public boolean tap(float x, float y, int count, KeyCode button){
        return false; //I AM GONNA
    }

    @Override
    public boolean longPress(float x, float y){
        return false; //AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
    }
}
