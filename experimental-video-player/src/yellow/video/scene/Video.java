package yellow.video.scene;

import arc.*;
import arc.files.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.scene.style.*;
import arc.scene.ui.*;
import arc.util.*;
import mindustry.*;
import yellow.video.*;

public class Video extends Image{
    private final TextureRegionDrawable videoTexture;
    private final VideoPlayer player;

    private boolean hasResized;

    public Video(VideoPlayer player){
        this(player, null);
    }

    public Video(VideoPlayer player, @Nullable Fi file){
        super(new TextureRegionDrawable(Core.atlas.white()));
        videoTexture = (TextureRegionDrawable) getDrawable();
        this.player = player;
        if(file != null){
            player.load(file);
            // try loading one frame as preview frame
            try{
                player.play();
                Texture tex = player.getTexture();
                if(tex != null) videoTexture.getRegion().set(tex);
                player.pause();
            }catch(Exception ignored){

            }
        }
    }

    @Override
    public void act(float delta){
        super.act(delta);
        if(player == null) return;
        Texture tex = null;
        if(player.update()) tex = player.getTexture();
        if(tex != null){
            videoTexture.getRegion().set(tex);

            if(!hasResized){
                hasResized = true;
                setSize(player.getVideoWidth(), player.getVideoHeight());
            }
        }
    }

    public VideoPlayer getPlayer(){
        return player;
    }


}
