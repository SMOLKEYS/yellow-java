package yellow.video;

import arc.files.*;
import arc.graphics.*;
import arc.graphics.Texture.*;

import java.io.*;

public abstract class AbstractVideoPlayer implements VideoPlayer{
    protected TextureFilter minFilter = TextureFilter.linear;
    protected TextureFilter magFilter = TextureFilter.linear;

    @Override
    public void setFilter (TextureFilter minFilter, TextureFilter magFilter) {
        if (this.minFilter == minFilter && this.magFilter == magFilter) return;
        this.minFilter = minFilter;
        this.magFilter = magFilter;
        Texture texture = getTexture();
        if (texture == null) return;
        texture.setFilter(minFilter, magFilter);
    }

    @Override
    @Deprecated
    public final boolean play(Fi file) throws FileNotFoundException{
        boolean loaded = load(file);
        if (loaded) {
            play();
        }
        return loaded;
    }
}