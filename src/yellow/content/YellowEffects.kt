package yellow.content

import arc.util.*
import arc.struct.*
import arc.graphics.Color
import arc.graphics.g2d.*
import arc.math.*
import mindustry.content.*
import mindustry.graphics.*
import yellow.entities.units.entity.*

object YellowEffects{
    
    private var shootOpacity = 0f
    private var targetSize = 23f
    private val rand = Rand()
    
    val effects = Seq.with(
    DrawEffect("the origin"){
        val s = Mathf.absin(Time.time, 16f, 1f)
        val r1 = s * 25f

        Draw.z(Layer.effect)
        Draw.color(Color.yellow)

        Lines.circle(it.x, it.y, 20f + r1)
        Lines.square(it.x, it.y, 20f + r1, Time.time)
        Lines.square(it.x, it.y, 20f + r1, -Time.time)

        Draw.alpha(it.elevation)
        rand.setSeed(4)
        Angles.circleVectors(5, 70f, Time.time){cx, cy ->
            val rotational = Time.time * rand.random(2f, 4f)
            if(it.lives <= 2) Draw.color(Pal.remove)
            Lines.circle(cx + it.x, cy + it.y, 20f)
            Lines.poly(cx + it.x, cy + it.y, it.lives, 16f, rotational)
            Fill.circle(cx + it.x, cy + it.y, 3f)
            Angles.circleVectors(it.lives, 17f, rotational){c2x, c2y ->
                Fill.circle(c2x + cx + it.x, c2y + cy + it.y, 4f)
            }
            Draw.color(Color.yellow)
        }

        shootOpacity = Mathf.approachDelta(shootOpacity,  if(it.isShooting()) 1f else 0f, 0.2f)
        targetSize = Mathf.approachDelta(targetSize, if(it.isShooting()) 12f else 23f, 0.2f)

        Drawf.target(it.aimX, it.aimY, targetSize, shootOpacity, Color.yellow)

        when{
            it.lives <= 3 -> {
                if(Mathf.chance(0.1)) Fx.smoke.at(it.x, it.y)
            }
        }
    },
    DrawEffect("calm and collected"){},
    DrawEffect("ballad of lines and blooms"){
        val space = 20f
        val size = 120f

        Draw.z(Layer.bullet)
        for(b in 0..10){
            for(i in 0..30){
                Draw.color(Pal.lancerLaser, Pal.accent, Mathf.absin(Time.time + i * 5f, 10f, 1f))
                Lines.line(it.x + Angles.trnsx(Time.time + i * space, size), it.y + Angles.trnsy(Time.time + i * space * 3f, size), it.x + Angles.trnsx(Time.time * 1.3f + i * space + space, size + b * 15f), it.y + Angles.trnsy(Time.time + i * space + space, size + b * 9f))
            }
        }
    }
    )
    
    var activeEffect = effects[0]
}

open class DrawEffect(var name: String, var drawCode: (YellowUnitEntity) -> Unit)