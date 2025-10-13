package yellow.entities.units;

import arc.func.*;
import arc.struct.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.game.*;
import mindustry.gen.*;

public class MultiLifeUnitType extends CharacterUnitType{
    /** How many lives this unit has. */
    public int lives = 1;
    /** Invincibility frames this unit gets when losing a life. */
    public float invFrames = 1f;
    /** Multiplier that makes inv-frames last longer the more lives are lost. TODO unused! */
    public float invDeathMultiplier = 1f;
    /** Individual pieces of code ran when the unit's life count hits a specific number. */
    public ObjectMap<Integer, Cons<MultiLifeUnitEntity>> perDeath = new ObjectMap<>();
    /** Visual effect used when a life is consumed. */
    public Effect deathStopEffect = Fx.none;
    /** If true, this unit will only die if it has no lives and no health remaining. Auto-revived otherwise. */
    public boolean dieIfTrulyDead = false;

    public MultiLifeUnitType(GameCharacter character, String name){
        super(character, name);
        constructor = MultiLifeUnitEntity::new;
    }

    @Override
    public Unit create(Team team){
        MultiLifeUnitEntity unit;
        try{
            unit = (MultiLifeUnitEntity) super.create(team);
        }catch(Exception e){
            return super.create(team);
        }
        unit.lives(lives);
        return unit;
    }

    @Override
    public void setStats(){
        super.setStats();

        stats.add(YellowStats.lives, lives);
    }
}
