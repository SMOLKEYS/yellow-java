package yellow.maps.planet

import arc.graphics.Color
import arc.math.Mathf
import arc.math.geom.Vec3
import arc.util.Tmp
import arc.util.noise.Simplex
import mindustry.content.Blocks.*
import mindustry.maps.generators.PlanetGenerator
import mindustry.world.Block

open class AzenoPlanetGenerator : PlanetGenerator(){

    var scl = 4.7f
    var waterOffset = 0.13f
    private var v34 = Vec3()
    private var v35 = Vec3()
    private var csus = Color()

    var arr = arrayOf(
        arrayOf(stone, stone, redmat, redmat, yellowStone)
    )

    var water: Float = 2f / arr.get(0).size

    override fun getHeight(position: Vec3): Float{
        return Math.max(rawHeight(position), water)
    }

    override fun getColor(position: Vec3): Color{
        val block = getBlock(position)
        @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
        return if (block === salt) sand.mapColor else csus.set(block!!.mapColor).a(1f - block!!.albedo)
    }

    fun getBlock(position: Vec3): Block? {
        var tposition = position
        var height = rawHeight(tposition)
        Tmp.v31.set(tposition)
        tposition = v35.set(position).scl(scl)
        val rad = scl
        var temp = Mathf.clamp(Math.abs(tposition.y * 2f) / rad)
        val tnoise = Simplex.noise3d(
            seed,
            6.65,
            0.58,
            (1f / 3f).toDouble(),
            tposition.x.toDouble(),
            (tposition.y + 999f).toDouble(),
            tposition.z.toDouble()
        )
        temp = Mathf.lerp(temp, tnoise, 0.5f)
        height *= 1.3f
        height = Mathf.clamp(height)
        val res = arr[Mathf.clamp((temp * arr.size).toInt(), 0, arr[0].size - 1)][Mathf.clamp(
            (height * arr[0].size).toInt(),
            0,
            arr[0].size - 1
        )]
        return res
    }
    fun rawHeight(position: Vec3): Float {
        val tposition = v34.set(position).scl(scl)
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