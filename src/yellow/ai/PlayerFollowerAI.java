package yellow.ai;

import arc.math.Angles;
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
			}else{
				followingUnit = false;
			}
		});
		
		if(target != null){
			if(target.isShooting){
				unit.vel.trns(Angles.angle(unit.x, unit.y, target.aimX, target.aimY), unit.speed());
			}else{
				circle(target, target.hitSize * 5f);
			}
		}
	}
}
