package yellow.world.meta;

import arc.struct.*;
import java.util.*;
import mindustry.*;
import mindustry.game.*;
import mindustry.type.*;

public class SaveIDAssigner{

    /**
     * Assigns a randomly generated 16 character string to the save. May freeze the game momentarily.
     * Sectors are given an extended ID. ({@code "planet-sectorid-baseid"})
     * @return {@code true} if the operation succeeds or if the save already has an ID. {@code false} otherwise.
     */
    public static boolean assignId(Saves.SaveSlot save){
        if(save.meta.rules.tags.containsKey("yellow-save-id")) return true;

        Saves.SaveSlot cur = Vars.control.saves.getCurrent();
        Rules crules = Vars.state.rules;

        if(save != cur){
            try{
                StringBuilder builder = new StringBuilder();
                Sector sector = save.meta.rules.sector;
                Planet planet = sector != null ? sector.planet : null;

                if(planet != null) builder.append(planet.name).append("-");
                if(sector != null) builder.append(sector.id).append("-");
                builder.append(Stringy.generateId(16));

                save.load();
                Vars.state.rules = save.meta.rules;
                save.meta.rules.tags.put("yellow-save-id", builder.toString());
                save.save();
                Vars.state.rules = crules;

                return true;
            }catch(Exception e){
                return false;
            }
        }else{
            try{
                StringBuilder builder = new StringBuilder();
                Sector sector = Vars.state.rules.sector;
                Planet planet = sector != null ? sector.planet : null;

                if(planet != null) builder.append(planet.name).append("-");
                if(sector != null) builder.append(sector.id).append("-");
                builder.append(Stringy.generateId(16));

                Vars.state.rules.tags.put("yellow-save-id", builder.toString());

                return true;
            }catch(Exception e){
                return false;
            }
        }
    }

    public static void update(){
        if(Vars.state.isPlaying()){
            StringMap map = Vars.state.rules.tags;

            if(!map.containsKey("yellow-save-id")){
                StringBuilder builder = new StringBuilder();
                Sector sector = Vars.state.rules.sector;
                Planet planet = sector != null ? sector.planet : null;

                if(planet != null) builder.append(planet.name).append("-");
                if(sector != null) builder.append(sector.id).append("-");
                builder.append(Stringy.generateId(16));

                map.put("yellow-save-id", builder.toString());
            }
        }
    }

    public static Saves.SaveSlot getSave(String id){
        return Vars.control.saves.getSaveSlots().find(s -> {
            if(!s.meta.rules.tags.containsKey("yellow-save-id")) return false;
            return Objects.equals(s.meta.rules.tags.get("yellow-save-id"), id);
        });
    }
}
