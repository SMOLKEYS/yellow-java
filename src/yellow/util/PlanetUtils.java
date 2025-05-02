package yellow.util;

import arc.*;
import mindustry.*;
import mindustry.game.*;
import mindustry.type.*;

public class PlanetUtils{

    public static void sectorLoad(SectorPreset pres, Runnable exec){
        sectorLoad(pres.planet, pres.id, exec);
    }

    public static void sectorLoad(Planet planet, int id, Runnable exec){
        Events.run(EventType.WorldLoadEvent.class, () -> {
            if(playerSector(planet, id)) exec.run();
        });
    }

    public static void sectorLoad(Sector sector, Runnable exec){
        Events.run(EventType.WorldLoadEvent.class, () -> {
            if(Vars.state.getSector() == sector) exec.run();
        });
    }

    public static void sectorCapture(SectorPreset pres, Runnable exec){
        sectorCapture(pres.planet, pres.id, exec);
    }

    public static void sectorCapture(Planet planet, int id, Runnable exec){
        Events.on(EventType.SectorCaptureEvent.class, sect -> {
            if(sect.sector.planet == planet && sect.sector.id == id) exec.run();
        });
    }

    public static void sectorCapture(Sector sector, Runnable exec){
        Events.on(EventType.SectorCaptureEvent.class, sect -> {
            if(sect.sector == sector) exec.run();
        });
    }

    public static boolean playerSector(SectorPreset pres){
        return playerSector(pres.planet, pres.sector.id);
    }

    public static boolean playerSector(Planet planet, int id){
        Sector sect = Vars.state.getSector();

        if(sect == null) return false;
        return sect.planet == planet && sect.id == id;
    }
}
