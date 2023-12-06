package yellow.game;

import mindustry.gen.*;

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
        public final Achievement achievement;

        public AchievementUnlockEvent(Achievement achievement){
            this.achievement = achievement;
        }
    }
}
