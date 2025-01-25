package yellow.game;

import mindustry.gen.*;
import yellow.goodies.vn.*;

public class YEventType{

    public static class DeathInvalidationEvent{
        public final Unit unit;
        
        public DeathInvalidationEvent(Unit unit){
            this.unit = unit;
        }
    }

    public static class NotificationCallEvent{
        public final Notification notification;

        public NotificationCallEvent(Notification notification){
            this.notification = notification;
        }
    }

    public static class AchievementUnlockEvent{
        public final YAchievement achievement;

        public AchievementUnlockEvent(YAchievement YAchievement){
            this.achievement = YAchievement;
        }
    }

    public static class DialogueStartEvent{
        public final Dialogue dialogue;

        public DialogueStartEvent(Dialogue dialogue){
            this.dialogue = dialogue;
        }
    }

    public static class DialogueEndEvent{
        public final Dialogue dialogue;

        public DialogueEndEvent(Dialogue dialogue){
            this.dialogue = dialogue;
        }
    }
}
