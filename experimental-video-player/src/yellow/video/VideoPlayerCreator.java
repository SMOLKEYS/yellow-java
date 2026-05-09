package yellow.video;

import arc.Application.*;
import arc.*;
import arc.audio.*;
import arc.struct.*;
import arc.util.*;
import mindustry.*;
import yellow.video.desktop.*;

import java.nio.*;

public class VideoPlayerCreator {
    private static Class<? extends VideoPlayer> videoPlayerClass;
    private static final Seq<VideoPlayer> players = new Seq<>();

    static {
        Runtime.getRuntime().addShutdownHook(new Thread(VideoPlayerCreator::disposeAllPlayers));
    }

    /** Creates a VideoPlayer.
     *
     * @return A new instance of VideoPlayer */
    @Nullable
    public static VideoPlayer createVideoPlayer () {
        initialize();
        if (videoPlayerClass == null) return new VideoPlayerStub();
        VideoPlayer pl = new CommonVideoPlayerDesktop(){
            @Override
            public Music createMusic(VideoDecoder decoder, ByteBuffer audioBuffer, int audioChannels, int sampleRate){
                return null;
            }
        };
        players.add(pl);
        return pl;
    }

    private static void disposeAllPlayers(){
        players.each(VideoPlayer::dispose);
    }

    @SuppressWarnings("unchecked")
    private static void initialize () {
        if (videoPlayerClass != null) return;

        String className = null;
        ApplicationType type = Core.app.getType();

        if (type == ApplicationType.android) {
            if (Core.app.getVersion() >= 12) {
                //className = "com.badlogic.gdx.video.VideoPlayerAndroid";
            } else {
                Log.err("VideoPlayer can't be used on android < API level 12");
            }
        } else if (type == ApplicationType.iOS) {
            if (Core.app.getVersion() >= 15) {
               //className = "com.badlogic.gdx.video.VideoPlayerIos";
            } else {
                Log.err("VideoPlayer can't be used on iOS < 15");
            }
        } else if (type == ApplicationType.desktop) {
            className = "yellow.video.desktop.CommonVideoPlayerDesktop";
        } else {
            Log.err("Platform is not supported by the Arc Video Extension");
        }

        try{
            videoPlayerClass = (Class<? extends VideoPlayer>) Class.forName(className, true, Vars.mods.mainLoader());
        }catch(ClassNotFoundException e){
        }
    }

    /*JNI
    #include "VideoDecoder.h"
     */

    public static native int test(); /*
            VideoDecoder* pointer = new VideoDecoder();

            return 323;
    */
}