package yellow.ui.fragments;

import arc.*;
import arc.func.*;
import arc.input.*;
import arc.math.*;
import arc.scene.*;
import arc.scene.actions.*;
import arc.scene.style.*;
import arc.scene.ui.*;
import arc.scene.ui.layout.*;
import arc.util.*;
import mindustry.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.ui.*;
import yellow.*;
import yellow.ui.*;
import yellow.util.*;

public class NotificationFragment implements CommonFragment{
    
    public float enterExitTime = 1.0f, flickTime = 0.2f;
    
    public Interp enterInterp = Interp.pow3Out,
            exitInterp = Interp.pow3In,
            flickInterp = Interp.fastSlow;

    private Table table;
    private Drawable persistent, error;
    private Label.LabelStyle lstyle;

    @Override
    public void build(Group parent){
        parent.fill(s -> {
            table = s;
            s.name = "notifications";
            s.visible(() -> true);
            s.top().right();
            s.y = -100;
        });
        persistent = ((TextureRegionDrawable) Tex.whiteui).tint(Pal.accent.cpy());
        error = ((TextureRegionDrawable) Tex.whiteui).tint(Pal.remove.cpy());
        lstyle = Styles.outlineLabel;
    }

    public void showNotification(String message){
        showNotification(Icon.info, message);
    }

    public void showNotification(String message, Runnable clicked){
        showNotification(Icon.info, message, clicked);
    }

    public void showNotification(Drawable icon, String message){
        showNotification(icon, message, () -> {});
    }

    public void showNotification(Drawable icon, String message, Runnable clicked){
        showNotification(icon, message, 70, clicked);
    }

    public void showNotification(Drawable icon, String message, float minHeight, Runnable clicked){
        showTintedNotification(null, icon, message, minHeight, false, clicked);
    }

    public void showPersistentNotification(String message){
        showPersistentNotification(Icon.info, message);
    }

    public void showPersistentNotification(String message, Runnable clicked){
        showPersistentNotification(Icon.info, message, clicked);
    }

    public void showPersistentNotification(Drawable icon, String message){
        showPersistentNotification(icon, message, () -> {});
    }

    public void showPersistentNotification(Drawable icon, String message, Runnable clicked){
        showPersistentNotification(icon, message, 70, clicked);
    }

    public void showPersistentNotification(Drawable icon, String message, float minHeight, Runnable clicked){
        showTintedNotification(persistent, icon, message, minHeight, true, clicked);
    }

    public void showErrorNotification(String message){
        showErrorNotification(Icon.warning, message);
    }

    public void showErrorNotification(String message, Throwable throwable){
        showErrorNotification(Icon.warning, throwable, message);
    }

    public void showErrorNotification(String message, Runnable clicked){
        showErrorNotification(Icon.warning, message, clicked);
    }

    public void showErrorNotification(String message, Throwable throwable, Runnable clicked){
        showErrorNotification(Icon.warning, message, () -> {
            Vars.ui.showException(message, throwable);
            clicked.run();
        });
    }

    public void showErrorNotification(Drawable icon, String message){
        showErrorNotification(icon, message, () -> {});
    }

    public void showErrorNotification(Drawable icon, Throwable throwable, String message){
        showErrorNotification(icon, message, () -> Vars.ui.showException(message, throwable));
    }

    public void showErrorNotification(Drawable icon, String message, Runnable clicked){
        showErrorNotification(icon, message, 70, clicked);
    }

    public void showErrorNotification(Drawable icon, Throwable throwable, String message, Runnable clicked){
        showErrorNotification(icon, message, 70, () -> {
            Vars.ui.showException(message, throwable);
            clicked.run();
        });
    }

    public void showErrorNotification(Drawable icon, String message, float minHeight, Runnable clicked){
        showTintedNotification(error, icon, message, minHeight, true, clicked);
    }

    public void showTintedNotification(@Nullable Drawable bg, Drawable icon, String message, float minHeight, boolean persist, @Nullable Runnable clicked){
        Cell<Table> t = table.table(bg != null ? bg : Styles.black5).minHeight(minHeight).width((Core.graphics.getWidth() * (SafeSettings.getInt("yellow-notification-length", 20, 20) / 100f)) / Scl.scl());
        Table tr = t.get();
        t.right();
        tr.right();
        tr.image(icon).size(32).scaling(Scaling.fit).pad(15).padLeft(20);
        tr.labelWrap(message).style(lstyle).grow().pad(10).padRight(8);
        float width = Math.max(tr.getMinWidth(), (Core.graphics.getWidth() * (SafeSettings.getInt("yellow-notification-length", 20, 20) / 100f)) / Scl.scl());
        tr.setTranslation(width, 0);

        tr.clicked(KeyCode.mouseLeft, () -> {
            tr.actions(Actions.sequence(
                    Actions.translateBy(width, 0, enterExitTime, exitInterp),
                    Actions.run(() -> {
                        table.getCells().remove(t);
                        if(clicked != null) clicked.run();
                    }),
                    Actions.remove()
            ));
        });

        //flick out notification
        tr.clicked(KeyCode.mouseRight, () -> {
            tr.actions(Actions.sequence(
                    Actions.translateBy(width, 0, flickTime, flickInterp),
                    Actions.run(() -> table.getCells().remove(t)),
                    Actions.remove()
            ));
        });

        tr.dragged((dx, dy) -> {
            float len = Mathf.len(dx, dy);
            //Log.info("len @, dx @, dy @", len, dx, dy);
            if(len >= 10f) tr.actions(Actions.sequence(
                    Actions.translateBy(width, 0, flickTime, flickInterp),
                    Actions.run(() -> table.getCells().remove(t)),
                    Actions.remove()
            ));
        });

        if(persist){
            t.tooltip("@yellow-persnotif-info", true);
            tr.actions(Actions.translateBy(-width, 0, enterExitTime, enterInterp));
        }else{
            t.tooltip("@yellow-notif-info", true);
            tr.hovered(() -> {
                if(tr.getActions().peek() instanceof SequenceAction s && s.getActions().peek() instanceof DelayAction) tr.getActions().clear();
            });
            tr.exited(() -> {
                if(tr.getActions().isEmpty()) tr.actions(Actions.sequence(
                        Actions.delay(YellowVars.getNotificationTime()),
                        Actions.translateBy(width, 0, enterExitTime, exitInterp),
                        Actions.run(() -> table.getCells().remove(t)),
                        Actions.remove()
                ));
            });
            tr.actions(Actions.sequence(
                    Actions.translateBy(-width, 0, enterExitTime, enterInterp),
                    Actions.delay(YellowVars.getNotificationTime()),
                    Actions.translateBy(width, 0, enterExitTime, exitInterp),
                    Actions.run(() -> table.getCells().remove(t)),
                    Actions.remove()
            ));
        }

        t.row();
    }

    public void showCustomNotification(boolean persist, Cons<Table> cons){
        Cell<Table> t = table.table(Styles.black5).minHeight(70).minWidth((Core.graphics.getWidth() * (SafeSettings.getInt("yellow-notification-length", 20, 20) / 100f)) / Scl.scl());
        Table tr = t.get();
        t.right();
        try{
            cons.get(tr);
        }catch(Exception e){
            //man
        }
        float width = Math.max(tr.getMinWidth(), (Core.graphics.getWidth() * (SafeSettings.getInt("yellow-notification-length", 20, 20) / 100f)) / Scl.scl());
        tr.clicked(() -> {
            tr.hovered(() -> {});
            tr.exited(() -> {});
            tr.clicked(() -> {});
            tr.actions(Actions.sequence(
                    Actions.translateBy(width, 0, enterExitTime, exitInterp),
                    Actions.run(() -> table.getCells().remove(t)),
                    Actions.remove()
            ));
        });

        if(persist){
            t.tooltip("@yellow-persnotif-info", true);
            tr.actions(Actions.translateBy(-width, 0, enterExitTime, enterInterp));
        }else{
            t.tooltip("@yellow-notif-info", true);
            tr.hovered(() -> {
                if(tr.getActions().peek() instanceof SequenceAction s && s.getActions().peek() instanceof DelayAction) tr.getActions().clear();
            });
            tr.exited(() -> {
                if(tr.getActions().isEmpty()) tr.actions(Actions.sequence(
                        Actions.delay(YellowVars.getNotificationTime()),
                        Actions.translateBy(width, 0, enterExitTime, exitInterp),
                        Actions.run(() -> table.getCells().remove(t)),
                        Actions.remove()
                ));
            });
            tr.actions(Actions.sequence(
                    Actions.translateBy(-width, 0, enterExitTime, enterInterp),
                    Actions.delay(YellowVars.getNotificationTime()),
                    Actions.translateBy(width, 0, enterExitTime, exitInterp),
                    Actions.run(() -> table.getCells().remove(t)),
                    Actions.remove()
            ));
        }

        t.row();
    }
}
