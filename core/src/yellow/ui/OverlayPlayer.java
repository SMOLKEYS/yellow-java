package yellow.ui;

import arc.*;
import arc.audio.*;
import arc.files.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.scene.*;
import arc.scene.style.*;
import arc.scene.ui.layout.*;

public class OverlayPlayer{

    public static OverlayFragment make(Group parent){
        OverlayFragment frag = new OverlayFragment();
        frag.build(parent);
        return frag;
    }

    public static class OverlayFragment implements CommonFragment{

        private Table tb;
        private final Music sfxprov = new Music();

        @Override
        public void build(Group parent){
            parent.fill(s -> {
                tb = s;
            });
        }

        public void annoy(ZipFi zip){
            annoy(zip.child("main.png"), zip.child("main.mp3"));
        }

        public void annoy(Fi img, Fi sfx){
            if(sfxprov.isPlaying()) return;
            try{
                sfxprov.load(sfx);
            }catch(Exception e){
                throw new RuntimeException(e);
            }
            sfxprov.play();

            tb.image(new TextureRegion(new Texture(img))).grow().fill().update(i -> {
                if(!sfxprov.isPlaying()) tb.clear();
            });
        }
    }
}
