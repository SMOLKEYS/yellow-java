package yellow.maps.planet

import arc.graphics.Color
import arc.math.geom.Vec3
import mindustry.maps.generators.PlanetGenerator

open class AzenoPlanetGenerator : PlanetGenerator(){

    override fun getHeight(position: Vec3?): Float{
        return 2f
    }

    override fun getColor(position: Vec3?): Color{
        return Color.yellow
    }

}