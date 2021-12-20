package yellow.content;

import arc.graphics.*;
import mindustry.ctype.*;
import mindustry.graphics.*;
import mindustry.graphics.g3d.*;
import mindustry.maps.planet.*;
import mindustry.type.*;

import static mindustry.content.Planets.*;

public class YellowPlanets implements ContentList{
    public static Planet
    
    azeno;
    
    @Override
    public void load(){
        azeno = new Planet("azeno", sun, 3.2f, 3){{
            accessible = true;
            alwaysUnlocked = true;
            atmosphereColor = Color.orange;
            atmosphereRadIn = 0.01f;
            atmosphereRadOut = 0.28f;
            orbitTime = 60 * 10;
            startSector = 27;
            
            generator = new SerpuloPlanetGenerator();
            landCloudColor = Color.yellow.cpy().a(0.3f);
            //TODO copied from serpulo planet code
            meshLoader = () -> new HexMesh(this, 6);
            cloudMeshLoader = () -> new MultiMesh(
                new HexSkyMesh(this, 11, 0.15f, 0.13f, 5, new Color().set(Pal.spore).mul(0.9f).a(0.75f), 2, 0.45f, 0.9f, 0.38f),
                new HexSkyMesh(this, 1, 0.6f, 0.16f, 5, Color.white.cpy().lerp(Pal.spore, 0.55f).a(0.75f), 2, 0.45f, 1f, 0.41f)
            );
        }};
    }
}