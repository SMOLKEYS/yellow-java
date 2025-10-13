package yellow.entities.units;

import arc.*;
import arc.graphics.*;
import arc.math.geom.*;
import arc.scene.ui.*;
import arc.scene.ui.layout.*;
import arc.util.*;
import mindustry.ai.types.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.ui.*;

public class GhostUnitType extends UnitType{
    /** Unit lifetime in ticks. */
    public float ghostLifetime = 900f;
    /** Despawn effect. */
    public Effect despawnEffect = YellowFx.ghostDespawnMulti;
    /** Despawn effect offset. */
    public Vec2 despawnEffectOffset = new Vec2();

    public GhostUnitType(String name){
        super(name);
        constructor = GhostUnitEntity::new;
    }

    @Override
    public void display(Unit unit, Table table){
        table.table(t -> {
           t.left();
           t.add(new Image(fullIcon)).size(8 * 4).scaling(Scaling.fit);
           t.labelWrap(localizedName).left().width(190f).padLeft(5);
        }).growX().left();
        table.row();

        table.table(bars -> {
            bars.defaults().growX().height(20f).pad(4);

            GhostUnitEntity ent = (GhostUnitEntity) unit;

            bars.add(new Bar("stat.health", Color.red, ent::healthf));
            bars.row();
            bars.add(new Bar("stat.lifetime", Pal.lancerLaser, ent::lifetimef));
            bars.row();
        }).growX();

        if(unit.controller() instanceof LogicAI){
            table.row();
            table.add(Blocks.microProcessor.emoji() + " " + Core.bundle.get("units.processorcontrol") + " (why?)").growX().wrap().left();
            table.row();
            table.label(() -> Iconc.settings + " " + (long) unit.flag).color(Color.lightGray).growX().wrap().left();
        }
    }
}
