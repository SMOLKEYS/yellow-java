package yellow.ai;

import mindustry.entities.units.AIController;
import mindustry.gen.Building;
import mindustry.gen.Groups;
import mindustry.gen.Unit;

public class PlayerFollowerAI extends AIController{

	protected Unit target;
	protected Building noTarget;
	protected boolean followingUnit = false;

	@Override
	public void updateMovement(){
		Groups.unit.each(unor -> {
			if(unor.isPlayer() && unor.team == unit.team && !followingUnit){
			    target = unor;
			    followingUnit = true;
			}
		});
		
		if(target != null){
			if(target.isShooting) unit.vel.set(target).add(unit);
		}
	}
}