package yellow.compat;

import arc.flabel.*;
import arc.input.GestureDetector.*;
import arc.input.*;
import arc.math.geom.*;

//what the FUCK android
public class AndroidCompatibilityClasses{

    public static class AFListener implements FListener{
        @Override
        public void end(){
            // prevents the following error:
            // AbstractMethodError: abstract method "void arc.flabel.FListener.end()"
        }
    }

    @SuppressWarnings("RedundantMethodOverride")
    public static class AGestureListener implements GestureListener{
        @Override
        public boolean touchDown(float x, float y, int pointer, KeyCode button){
            return false;
        }

        @Override
        public boolean fling(float velocityX, float velocityY, KeyCode button){
            return false;
        }

        @Override
        public boolean pan(float x, float y, float deltaX, float deltaY){
            return false;
        }

        @Override
        public boolean panStop(float x, float y, int pointer, KeyCode button){
            return false;
        }

        @Override
        public boolean zoom(float initialDistance, float distance){
            return false;
        }

        @Override
        public boolean pinch(Vec2 initialPointer1, Vec2 initialPointer2, Vec2 pointer1, Vec2 pointer2){
            return false;
        }

        @Override
        public void pinchStop(){

        }

        @Override
        public boolean tap(float x, float y, int count, KeyCode button){
            return false;
        }

        @Override
        public boolean longPress(float x, float y){
            return false;
        }
    }

    public static class AInputProcessor implements InputProcessor{
        @Override
        public void connected(InputDevice device){

        }

        @Override
        public void disconnected(InputDevice device){

        }

        @Override
        public boolean keyDown(KeyCode keycode){
            return false;
        }

        @Override
        public boolean keyUp(KeyCode keycode){
            return false;
        }

        @Override
        public boolean keyTyped(char character){
            return false;
        }

        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, KeyCode button){
            return false;
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, KeyCode button){
            return false;
        }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer){
            return false;
        }

        @Override
        public boolean mouseMoved(int screenX, int screenY){
            return false;
        }

        @Override
        public boolean scrolled(float amountX, float amountY){
            return false;
        }
    }
}
