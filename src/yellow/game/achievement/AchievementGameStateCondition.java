package yellow.game.achievement;

import arc.func.*;
import arc.struct.*;
import mindustry.core.*;

public class AchievementGameStateCondition{

    public static final Seq<AchievementGameStateCondition> all = new Seq<>();

    public static final AchievementGameStateCondition

    menu = new AchievementGameStateCondition(GameState::isMenu),
    campaign = new AchievementGameStateCondition(GameState::isCampaign),
    custom = new AchievementGameStateCondition(s -> s.isGame() && !s.isCampaign()),
    survival = new AchievementGameStateCondition(s -> s.isGame() && !s.isCampaign() && !s.rules.infiniteResources),
    sandbox = new AchievementGameStateCondition(s -> s.isGame() && !s.isCampaign() && s.rules.infiniteResources),
    any = new AchievementGameStateCondition(s -> true);

    public final int id;
    public final Boolf<GameState> condition;


    public AchievementGameStateCondition(Boolf<GameState> condition){
        this.condition = condition;
        this.id = all.size;
        all.add(this);
    }
}
