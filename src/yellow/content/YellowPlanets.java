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
            orbitTime = 60*10;
            startSector = 27;
            
            generator = new SerpuloPlanetGenerator();
            landCloudColor = Color.yellow.cpy().a(0.3f);
        }};
    }
}