package yellow.content;

import arc.graphics.*;
import mindustry.graphics.*;
import mindustry.graphics.g3d.*;
import mindustry.type.*;
import yellow.maps.planet.*;

import static mindustry.content.Planets.*;

public class YellowPlanets{
    public static Planet
    
    azeno;
    

    public static void load(){
        azeno = new Planet("azeno", sun, 1.3f, 3){{
            accessible = true;
            alwaysUnlocked = true;
            atmosphereColor = Color.gray;
            atmosphereRadIn = 0.01f;
            atmosphereRadOut = 0.28f;
            orbitTime = 60 * 10;
            startSector = 27;
            
            generator = new AzenoPlanetGenerator();
            landCloudColor = Color.yellow.cpy().a(0.3f);
            /* TODO copied from serpulo planet code */
            meshLoader = () -> new HexMesh(this, 5);
            cloudMeshLoader = () -> new MultiMesh(
                new HexSkyMesh(this, 11, 0.15f, 0.13f, 5, new Color().set(Pal.accent).mul(0.8f).a(0.65f), 2, 0.45f, 0.9f, 0.38f),
                new HexSkyMesh(this, 1, 0.6f, 0.16f, 5, Color.white.cpy().lerp(Pal.accent, 0.55f).a(0.65f), 3, 0.46f, 1f, 0.41f)
            );
        }};
    }
}
