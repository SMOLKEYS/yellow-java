package yellow.video.desktop;

import arc.*;
import arc.audio.*;
import arc.files.*;
import arc.graphics.*;
import arc.util.*;
import yellow.video.*;
import yellow.video.VideoDecoder.*;

import java.io.*;
import java.nio.*;
import java.nio.channels.*;

/** Desktop implementation of the VideoPlayer
 *
 * @author Rob Bogie rob.bogie@codepoke.net */
abstract public class CommonVideoPlayerDesktop extends AbstractVideoPlayer{
    VideoDecoder decoder;
    Texture texture;
    Music audio;
    long startTime = 0;
    long lastFrameID = 0;
    float targetPosition = 0;

    boolean paused = false;
    boolean looping = false;
    boolean isFirstFrame = true;

    int currentVideoWidth, currentVideoHeight;
    int videoBufferWidth;
    VideoSizeListener sizeListener;
    CompletionListener completionListener;
    Fi currentFile;

    BufferedInputStream inputStream;
    ReadableByteChannel fileChannel;

    boolean playing = false;

    public CommonVideoPlayerDesktop () {
    }

    public abstract Music createMusic (VideoDecoder decoder, ByteBuffer audioBuffer, int audioChannels, int sampleRate);

    private int getTextureWidth () {
        return videoBufferWidth;
    }

    private int getTextureHeight () {
        return currentVideoHeight;
    }

    @Override
    public boolean load (Fi file){
        if (file == null) {
            return false;
        }
        if (!file.exists()) {
            throw new ArcRuntimeException("Could not find file: " + file.path());
        }

        if (decoder != null) {
            // Do all the cleanup
            stop();
        }

        currentFile = file;
        inputStream = file.read(256 * 1024);
        fileChannel = Channels.newChannel(inputStream);

        isFirstFrame = true;
        decoder = new VideoDecoder();
        VideoDecoderBuffers buffers;
        try {
            buffers = decoder.loadStream(this::readFileContents);

            if (buffers != null) {
                ByteBuffer audioBuffer = buffers.getAudioBuffer();
                if (audioBuffer != null) {
                    if (audio != null) audio.dispose();
                    audio = createMusic(decoder, audioBuffer, buffers.getAudioChannels(), buffers.getAudioSampleRate());
                }
                currentVideoWidth = buffers.getVideoWidth();
                currentVideoHeight = buffers.getVideoHeight();
                videoBufferWidth = buffers.getVideoBufferWidth();
                if (texture != null && (texture.width != getTextureWidth() || texture.height != getTextureHeight())) {
                    texture.dispose();
                    texture = null;
                }
            } else {
                return false;
            }
        } catch (Exception e) {
            Log.err("Error loading video", e);
            return false;
        }

        if (sizeListener != null) {
            sizeListener.onVideoSize(currentVideoWidth, currentVideoHeight);
        }
        return true;
    }

    @Override
    public void play () {
        playing = true;
        if (paused) {
            paused = false;
            if (audio != null) {
                audio.play();
            }
        }
        if (decoder.nextVideoFrame() == null && !isFirstFrame) {
            resetVideo();
        }
    }

    /** Called by jni to fill in the file buffer.
     *
     * @param buffer The buffer that needs to be filled
     * @return The amount that has been filled into the buffer. */
    private int readFileContents (ByteBuffer buffer) {
        try {
            buffer.rewind();
            return fileChannel.read(buffer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update () {
        if (decoder != null && (!paused || isFirstFrame) && playing) {
            if (!paused && startTime == 0) {
                // Since startTime is 0, this means that we should now display the first frame of the video, and set the
                // time.
                startTime = System.currentTimeMillis();
                targetPosition = 0;
                if (audio != null) {
                    audio.play();
                }
            }

            boolean newFrame = false;

            long currentFrameID = Core.graphics.getFrameId();
            if (currentFrameID != lastFrameID) {
                lastFrameID = Core.graphics.getFrameId();
                // Update video position
                if (audio != null) {
                    targetPosition = audio.getPosition();
                } else {
                    float delta = Core.graphics.getDeltaTime();
                    if (delta < 0.25f) {
                        targetPosition += delta;
                    }
                }
            }

            float currentPosition = isFirstFrame ? -1 : (float)decoder.getCurrentFrameTimestamp();

            while (currentPosition <= targetPosition) {
                ByteBuffer videoData = decoder.nextVideoFrame();
                if (videoData != null) {
                    float newPosition = (float)decoder.getCurrentFrameTimestamp();
                    if (newPosition == currentPosition) {
                        // A frame was repeated (not loaded fast enough)
                        break;
                    }
                    currentPosition = newPosition;
                    if (texture == null) {
                        texture = new Texture(getTextureWidth(), getTextureHeight());
                        texture.setFilter(minFilter, magFilter);
                    }
                    texture.bind();
                    Core.gl.glTexImage2D(GL20.GL_TEXTURE_2D, 0, GL20.GL_RGB, getTextureWidth(), getTextureHeight(), 0, GL20.GL_RGB,
                            GL20.GL_UNSIGNED_BYTE, videoData);
                    newFrame = true;
                } else if (isFirstFrame) {
                    return false;
                } else if (looping) {
                    resetVideo();
                    return false;
                } else {
                    playing = false;
                    if (completionListener != null) {
                        completionListener.onCompletionListener(currentFile);
                    }
                    return false;
                }
            }

            isFirstFrame = false;
            return newFrame;
        }
        return false;
    }

    private void resetVideo () {
        try {
            // NOTE: this just creates a new decoder instead of reusing the existing one.
            float volume = getVolume();
            load(currentFile);
            play();
            setVolume(volume);
        } catch (ArcRuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Nullable
    public Texture getTexture () {
        return texture;
    }

    /** Will return whether the buffer is filled. At the time of writing, the buffer used can store 10 frames of video. You can
     * find the value in jni/VideoDecoder.h
     *
     * @return whether buffer is filled. */
    @Override
    public boolean isBuffered () {
        if (decoder != null) {
            return decoder.isBuffered();
        }
        return false;
    }

    @Override
    public void stop () {
        playing = false;

        if (audio != null) {
            audio.dispose();
            audio = null;
        }
        if (decoder != null) {
            decoder.dispose();
            decoder = null;
        }
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
                throw new ArcRuntimeException("Error closing input stream", e);
            }
            inputStream = null;
        }

        startTime = 0;
        isFirstFrame = true;
    }

    @Override
    public void pause () {
        if (!paused) {
            paused = true;
            if (audio != null) {
                audio.pause(true);
            }
        }
    }

    @Override
    public void resume () {
        if (decoder != null) {
            play();
        }
    }

    @Override
    public void dispose () {
        stop();
        if (texture != null) {
            texture.dispose();
            texture = null;
        }
    }

    @Override
    public void setOnVideoSizeListener (VideoSizeListener listener) {
        sizeListener = listener;
    }

    @Override
    public void setOnCompletionListener (CompletionListener listener) {
        completionListener = listener;
    }

    @Override
    public int getVideoWidth () {
        return currentVideoWidth;
    }

    @Override
    public int getVideoHeight () {
        return currentVideoHeight;
    }

    @Override
    public boolean isPlaying () {
        return playing && !paused;
    }

    @Override
    public void setVolume (float volume) {
        if (audio != null) audio.setVolume(volume);
    }

    @Override
    public float getVolume () {
        if (audio == null) return 0;
        return audio.getVolume();
    }

    @Override
    public void setLooping (boolean looping) {
        this.looping = looping;
    }

    @Override
    public boolean isLooping () {
        return looping;
    }

    @Override
    public int getCurrentTimestamp () {
        return (int)(decoder.getCurrentFrameTimestamp() * 1000);
    }

}