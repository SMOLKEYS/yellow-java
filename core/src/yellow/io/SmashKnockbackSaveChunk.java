package yellow.io;

import arc.struct.*;
import arc.util.io.*;
import mindustry.io.*;
import mindustry.io.SaveFileReader.*;

import java.io.*;

public class SmashKnockbackSaveChunk implements CustomChunk{
    private final IntMap<Float> set = new IntMap<>();

    public static void init(){
        SaveVersion.addCustomChunk("yellow-smash-knockback", new SmashKnockbackSaveChunk());
    }

    @Override
    public void write(DataOutput stream) throws IOException{
        Writes streamer = new Writes(stream);
        int size = set.size;
        streamer.i(size);

        set.keys().toArray().each(streamer::i);
        set.values().toArray().each(streamer::f);
        set.clear();
    }

    @Override
    public void read(DataInput stream) throws IOException{
        Reads streamer = new Reads(stream);
        int size = streamer.i();

        int[] ids = new int[size];
        float[] values = new float[size];

        for(int i = 0; i < size; i++){
            ids[i] = streamer.i();
        }

        for(int i = 0; i < size; i++){
            values[i] = streamer.f();
        }

        for(int i = 0; i < size; i++){
            set.put(ids[i], values[i]);
        }
    }
}
