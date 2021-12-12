package yellow.content;

import arc.graphics.*;
import mindustry.ctype.*;
import mindustry.graphics.*;
import mindustry.graphics.g3d.*;
import mindustry.maps.planet.*;
import mindustry.type.*;
import mindustry.content.Planets.*;

public class YellowPlanets implements ContentList{
    public static Planet
    
    azeno;
    
    @Override
    public void load(){
        azeno = new Planet("azeno", Planets.sun, 3.2f, 3){{
            
        }};
    }
}