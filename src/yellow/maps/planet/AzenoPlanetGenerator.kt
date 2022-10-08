package yellow.maps.planet

import arc.graphics.Color
import arc.math.Mathf
import arc.math.geom.Vec3
import arc.util.noise.Simplex
import mindustry.content.Blocks.*
import mindustry.maps.generators.PlanetGenerator

open class AzenoPlanetGenerator : PlanetGenerator(){

    var scl = 4.7f
    var waterOffset = 0.13f
    private var v34 = Vec3()

    var arr = arrayOf(
        arrayOf(yellowStone, yellowStone, redmat, redmat, yellowStone, yellowStone)
    )

    var water: Float = 2f / arr.get(0).size

    override fun getHeight(position: Vec3): Float{
        return Math.max(rawHeight(position), water)
    }

    override fun getColor(position: Vec3?): Color{
        return Color.yellow
    }

    fun rawHeight(position: Vec3): Float {
        var tposition = v34.set(position).scl(scl)
        return (Mathf.pow(
            Simplex.noise3d(
                seed,
                6.65,
                0.5,
                (1f / 3f).toDouble(),
                tposition.x.toDouble(),
                tposition.y.toDouble(),
                tposition.z.toDouble()
            ), 2.3f
        ) + waterOffset) / (1f + waterOffset)
    }
}