package yellow.content;

import yellow.ui.*;

public class YellowNotifications{
    public static Notification

    hi;

    public static void load(){
        hi = new Notification(){{
            title = "Hello!";
            message = "En voyage!";
        }};
    }
}
