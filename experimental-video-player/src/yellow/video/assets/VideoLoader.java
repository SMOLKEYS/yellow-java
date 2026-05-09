package yellow.video.assets;

import arc.assets.*;
import arc.assets.loaders.*;
import arc.files.*;
import arc.graphics.Texture.*;
import arc.struct.*;
import arc.util.*;
import yellow.video.*;

import java.io.*;

public class VideoLoader extends AsynchronousAssetLoader<VideoPlayer, VideoLoader.VideoParameter> {

    public VideoLoader (FileHandleResolver resolver) {
        super(resolver);
    }

    @Override
    public Seq<AssetDescriptor> getDependencies(String fileName, Fi file, VideoParameter parameter){
        return null;
    }

    private VideoPlayer videoPlayer;

    @Override
    public void loadAsync (AssetManager manager, String fileName, Fi file, VideoParameter parameter) {
        videoPlayer = null;
        videoPlayer = VideoPlayerCreator.createVideoPlayer();
        videoPlayer.setFilter(parameter.minFilter, parameter.magFilter);
        videoPlayer.setLooping(parameter.looping);
        videoPlayer.setVolume(parameter.volume);
        videoPlayer.load(file);
    }

    @Override
    public VideoPlayer loadSync(AssetManager manager, String fileName, Fi file, VideoParameter parameter) {
        VideoPlayer player = this.videoPlayer;
        this.videoPlayer = null;
        return player;
    }


    public static class VideoParameter extends AssetLoaderParameters<VideoPlayer> {
        public TextureFilter minFilter, magFilter;
        public boolean looping;
        public float volume = 1;
    }

}
