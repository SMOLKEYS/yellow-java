package yellow.content;

import yellow.ui.Notification;

public class YellowNotifications{
    public static Notification

    hi;

    public static void load(){
        hi = new Notification(){{
            title = "Hello!";
            message = "A notification system is partway complete.";
        }};
    }
}
