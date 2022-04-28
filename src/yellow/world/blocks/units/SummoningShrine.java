package yellow.world.blocks.units;

import arc.util.*;
import arc.math.*;
import arc.scene.ui.layout.*;
import arc.flabel.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.content.*;
import mindustry.entities.*;

import static mindustry.Vars.*;

public class SummoningShrine extends Block{
    /** What unit to summon. */
    public UnitType unit;
    /** Time required for the unit to be summoned. */
    public float summonTime = 60f;
    /** What effect to call on summon request. */
    public Effect requestEffect = Fx.none;
    /** What effect to call once the unit gets successfully summoned. */
    public Effect summonEffect = Fx.none;
    
    public SummoningShrine(UnitType unit){
        super(unit.name + "-shrine");
        this.unit = unit;
        configurable = true; //set this to false and you basically just have a Block lmao
        solid = true;
        update = true;
    }
    
    public class SummoningShrineBuild extends Building{
        
        @Override
        public void buildConfiguration(Table table){
            table.table(t -> {
                t.add("Summoning Shrine (" + unit.localizedName + ")").row();
                t.button("Summon Unit", () -> { /** TODO button wrappinģ screwery*/
                    requestEffect.at(this);
                    Time.run(summonTime, () -> {
                        unit.spawn(team, this);
                        summonEffect.at(this);
                    });
                });
            });
        }
    }
}
