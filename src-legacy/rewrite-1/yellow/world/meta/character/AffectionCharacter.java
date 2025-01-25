package yellow.world.meta.character;

import arc.*;
import yellow.world.meta.*;

/** A character with special data related to affection. */
public class AffectionCharacter extends GameCharacter{
    /** Various relationship ranks the player can achieve with this character. */
    public RelationshipRank[] relationshipRanks;
    /** If true, this character's affinity points are final. */
    public boolean finalAffinity;
    /** Starting affinity points of this character. */
    public int startingAffinity;

    public AffectionCharacter(String name){
        super(name);
    }

    @SuppressWarnings("ClassCanBeRecord") //shut the fuck up java
    public static class RelationshipRank{
        public final String name;
        public final int requiredAffinity;

        public RelationshipRank(String name, int requiredAffinity){
            this.name = name;
            this.requiredAffinity = requiredAffinity;
        }
        
        public String localized(){
            return Core.bundle.get("rank." + name + ".name");
        }
    }
}
