package yellow.world.blocks.units;

import arc.util.*;
import arc.math.*;
import arc.scene.ui.layout.*;
import arc.flabel.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.graphics.*;

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
        
        private boolean currentlySummoning = false;
        private float a = 0f;
        
        public boolean summoning(){
            return currentlySummoning;
        }
        
        @Override
        public void buildConfiguration(Table table){
            table.table(t -> {
                t.add("Summoning Shrine (" + unit.localizedName + ")").row();
                t.button("Summon Unit", () -> { /** TODO button wrappinģ screwery*/
                    requestEffect.at(this);
                    currentlySummoning = true;
                    Time.run(summonTime, () -> {
                        unit.spawn(team, this);
                        summonEffect.at(this);
                        currentlySummoning = false;
                    });
                });
            });
        
        
        @Override
        public void draw(){
            super.draw();=
            float lerpA = summoning() ? 1f : 0f;
            float sus = Mathf.absin(10f, 10f);
            a = Mathf.lerp(a, lerpA, 0.04f);
            
            Draw.z(Layer.effect);
            Draw.color(Tmp.c1.set(Color.yellow).lerp(Color.cyan, Mathf.absin(10f, 1f)));
            Fill.circle(this, 5f + Mathf.absin(10f, 2f));
            Lines.circle(this, 20f);
            Lines.square(this, 19f, Time.time);
            Lines.square(this, 19f, -Time.time);
            Draw.alpha(a);
            Lines.circle(this, 25f + sus);
            Lines.square(this, 25f + sus, Time.time);
            Lines.square(this, 25f + sus, -Time.time);
        }
    }
}
