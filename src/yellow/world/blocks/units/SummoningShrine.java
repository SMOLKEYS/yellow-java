package yellow.world.blocks.units;

import arc.*;
import arc.util.*;
import arc.util.io.*;
import arc.math.*;
import arc.scene.ui.layout.*;
import arc.scene.event.*;
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
import static mindustry.game.EventType.*;

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
        
        private boolean currentlySummoning = false, placed = false;
        private float a = 0f, size = 0f;
        
        
        public void fireListener(){
            Events.on(WorldLoadEvent.class, w -> {
                currentlySummoning = false;
            });
        }
        
        @Override
        public void buildConfiguration(Table table){
            table.table(t -> {
                t.add("Summoning Shrine (" + unit.localizedName + ")").row();
                t.button("Summon Unit", () -> {
                    t.getChildren().get(1).touchable = Touchable.disabled;
                    requestEffect.at(this);
                    currentlySummoning = true;
                    Time.run(summonTime, () -> {
                        unit.spawn(team, this);
                        summonEffect.at(this);
                        currentlySummoning = false;
                        t.getChildren().get(1).touchable = Touchable.enabled;
                    });
                }).get().getLabel().setWrap(false);
                
                if(currentlySummoning){
                    t.getChildren().get(1).touchable = Touchable.disabled;
                };
            });
        }
        
        @Override
        public void placed(){
            super.placed();
            
            placed = true;
        }
        
        @Override
        public void draw(){
            super.draw();
            
            float lerpA = currentlySummoning ? 1f : 0f;
            float sus = Mathf.absin(10f, 10f);
            a = Mathf.lerp(a, lerpA, 0.04f);
            
            float lerpSize = placed ? 20f : 0f;
            size = Mathf.lerp(size, lerpSize, 0.043f);
            
            Draw.z(Layer.effect);
            Draw.color(Tmp.c1.set(Color.yellow).lerp(Color.cyan, Mathf.absin(10f, 1f)));
            Fill.circle(this.x, this.y, size - 15f + Mathf.absin(10f, 2f));
            Lines.circle(this.x, this.y, size);
            Lines.square(this.x, this.y, size - 1f, Time.time);
            Lines.square(this.x, this.y, size - 1f, -Time.time);
            Draw.alpha(a);
            Lines.circle(this.x, this.y, 25f + sus);
            Lines.square(this.x, this.y, 25f + sus, Time.time);
            Lines.square(this.x, this.y, 25f + sus, -Time.time);
        }
        
        @Override
        public void write(Writes write){
            super.write(write);
            
            write.bool(placed);
        }
        
        @Override
        public void read(Reads read){
            super.read(read);
            
            placed = read.bool();
        }
    }
}
