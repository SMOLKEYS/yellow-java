package yellow.game;

import arc.graphics.*;
import mindustry.game.*;

public class YellowTeam extends Team{
    public static YellowTeam azmar;
    
    protected YellowTeam(int id, String name, Color color){
        super(id, name, color);
    }
    
    public static void load(){
        azmar = new YellowTeam(7, "azmar", Color.yellow); //7 is mine
    }
    
    @Override
    public boolean isEnemy(Team other){
        return this != other && other != Team.sharded;
    }
}
