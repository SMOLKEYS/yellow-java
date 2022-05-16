package yellow.game;

import mindustry.*;

import mindustry.Vars.*;
/** TODO */
public class BullethellLevel{
    /** X and Y positions of level playing field. */
    public float areaX = 0f, areaY = 0f;
    /** Size of level playing field. */
    public float areaRad = 0f;
    /** Width and height of level playing field. */
    public float areaWidth = 0f, areaHeight = 0f;
    /** Time needed to finish the entire bullethell level in ticks. */
    public float levelTime = 60f;
    /** Whether or not to use the entire map as the playing field. Disables all enemy spawns and makes areaX, areaY, areaRad, areaWidth and areaHeight get ignored. */
    public boolean useWholeMap = false;
    
    public BullethellLevel(){
        
    }
    
    /** Starts the level. Override this method and place all attack pattern code here. */
    public void start(){
        Vars.disableUI = true;
    }
}
