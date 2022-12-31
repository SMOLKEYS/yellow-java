package yellow.game;

import arc.graphics.*;
import mindustry.game.*;

public class YellowTeam extends Team{
    public static final YellowTeam azmar = new YellowTeam(1000, "azmar", Color.yellow); //ill be taking id 1000
    
    protected YellowTeam(int id, String name, Color color){
        super(id, name, color);
    }
}