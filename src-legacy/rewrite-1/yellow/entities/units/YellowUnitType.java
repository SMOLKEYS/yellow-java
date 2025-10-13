package yellow.entities.units;

public class YellowUnitType extends MagicSpecialistUnitType{

    public YellowUnitType(GameCharacter character, String name){
        super(character, name);
        constructor = YellowUnitEntity::new;
    }
}
