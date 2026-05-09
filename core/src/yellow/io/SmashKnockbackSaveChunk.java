package yellow.io;

import arc.*;
import arc.struct.*;
import arc.util.io.*;
import mindustry.game.EventType.*;
import mindustry.gen.*;
import mindustry.io.SaveFileReader.*;
import mindustry.io.*;

import java.io.*;

public class SmashKnockbackSaveChunk implements CustomChunk{
    private final ObjectMap<Unit, Float> set = new ObjectMap<>();

    public static void init(){
        SmashKnockbackSaveChunk chunk = new SmashKnockbackSaveChunk();
        SaveVersion.addCustomChunk("yellow-smash-knockback", chunk);

        Events.run(Trigger.update, chunk::update);
    }

    public void put(Unit unit, float knockback){
        set.put(unit, knockback);
    }

    public void add(Unit unit, float amount){
        if(!set.containsKey(unit)) return;
        set.put(unit, set.get(unit) + amount);
    }

    public ObjectMap<Unit, Float> set(){
        return set;
    }

    public void update(){
        Groups.unit.each(e -> {
            if(set.containsKey(e)) return;
            set.put(e, 1f);
        });

        set.each((u, f) -> {
            if(u.dead || !u.isValid()) set.remove(u);
        });
    }

    @Override
    public void write(DataOutput stream) throws IOException{
        Writes streamer = new Writes(stream);
        int size = set.size;
        streamer.i(size);

        set.keys().toSeq().each(un -> TypeIO.writeUnit(streamer, un));
        set.values().toSeq().each(streamer::f);
        set.clear();
        streamer.close();
    }

    @Override
    public void read(DataInput stream) throws IOException{
        Reads streamer = new Reads(stream);
        int size = streamer.i();

        Unit[] ids = new Unit[size];
        float[] values = new float[size];

        for(int i = 0; i < size; i++){
            ids[i] = TypeIO.readUnit(streamer);
        }

        for(int i = 0; i < size; i++){
            values[i] = streamer.f();
        }

        for(int i = 0; i < size; i++){
            set.put(ids[i], values[i]);
        }

        streamer.close();
    }
}
