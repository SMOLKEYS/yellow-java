package yellow.world.meta;

import arc.*;
import arc.graphics.*;
import mindustry.game.*;

/** The base class for characters. Comes with basic values, like location and location locking. */
public class GameCharacter{
    public static final GameCharacter empty = new GameCharacter("empty"){
        @Override
        public String displayed(){
            return "";
        }

        @Override
        public boolean isRevealed(){
            return false;
        }

        @Override
        public void setLocationLock(Team team, boolean lock){
        }

        @Override
        public boolean locationLock(Team team){
            return false;
        }

        @Override
        public void setLocation(Team team, String loc){
        }

        @Override
        public String location(Team team){
            return "<nowhere>";
        }
    };

    /** The internal name of this character. */
    public String name;
    public Color color = new Color();

    public GameCharacter(String name){
        this.name = name;
        YellowContent.characters.add(this);
    }

    /** Returns the special ID of the save this character was last found in. */
    public String location(Team team){
        return Core.settings.getString("character-" + name + "-location-t" + team.id, "<none>");
    }

    public void setLocation(Team team, String loc){
        Core.settings.put("character-" + name + "-location-t" + team.id, loc);
    }

    /** Returns true if the location lock for this character is enabled.
     * If true, this character's location cannot be changed. */
    public boolean locationLock(Team team){
        return Core.settings.getBool("character-" + name + "-location-lock-t" + team.id, false);
    }

    public void setLocationLock(Team team, boolean lock){
        Core.settings.put("character-" + name + "-location-lock-" + "t" + team.id, lock);
    }

    /** Returns the proper name of this character IF said character has been revealed. "???" otherwise. */
    public String displayed(){
        return isRevealed() ? Core.bundle.get("character." + name + ".name") : "???";
    }

    /** Sets the "revealed" status of this character. */
    public void setRevealed(boolean rev){
        Core.settings.put("character-" + name + "-revealed", rev);
    }

    /** Returns the "revealed" status of this character. */
    public boolean isRevealed(){
        return Core.settings.getBool("character-" + name + "-revealed", false);
    }
}
