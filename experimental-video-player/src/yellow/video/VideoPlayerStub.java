package yellow.video;

import arc.files.*;
import arc.graphics.*;
import arc.graphics.Texture.*;
import arc.util.*;

class VideoPlayerStub extends AbstractVideoPlayer{

    @Override
    public boolean load (Fi file){
        return false;
    }

    @Override
    public void play () {

    }

    @Override
    public boolean update () {
        return false;
    }

    @Override
    @Nullable
    public Texture getTexture () {
        return null;
    }

    @Override
    public boolean isBuffered () {
        return true;
    }

    @Override
    public void pause () {
    }

    @Override
    public void resume () {
    }

    @Override
    public void stop () {
    }

    @Override
    public void setOnVideoSizeListener (VideoSizeListener listener) {
    }

    @Override
    public void setOnCompletionListener (CompletionListener listener) {
    }

    @Override
    public int getVideoWidth () {
        return 0;
    }

    @Override
    public int getVideoHeight () {
        return 0;
    }

    @Override
    public boolean isPlaying () {
        return false;
    }

    @Override
    public int getCurrentTimestamp () {
        return 0;
    }

    @Override
    public void dispose () {
    }

    @Override
    public void setVolume (float volume) {
    }

    @Override
    public float getVolume () {
        return 0;
    }

    @Override
    public void setLooping (boolean looping) {

    }

    @Override
    public boolean isLooping () {
        return false;
    }

    @Override
    public void setFilter (TextureFilter minFilter, TextureFilter magFilter) {

    }
}
