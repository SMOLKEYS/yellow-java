package yellow.game;

import arc.*;
import arc.func.*;
import arc.graphics.*;
import arc.scene.style.*;
import arc.struct.*;
import mindustry.gen.*;
import mindustry.graphics.*;


@SuppressWarnings("ALL")
public class YellowSpecialNotifications{

    public static Seq<Trio> nots = Seq.with(
            Trio.with(Icon.download.tint(Pal.accent), Core.bundle.get("yellow.specnof-fos-notif"), () -> {
                /*
                TODO to be redone

                BaseDialog d = new BaseDialog("@yellow.specnof-fos-title");
                d.addCloseButton();

                d.cont.table(v -> {
                    v.add(new FLabel("@yellow.specnof-fos-1")).row();
                    v.add(new FLabel("@yellow.specnof-fos-2")).row();
                    v.add(new FLabel("@yellow.specnof-fos-3")).row();
                }).growX().pad(40f).row();
                d.cont.add("@yellow.specnof-fos-tryout").row();
                d.cont.button("@yellow.specnof-fos-repo", () -> Core.app.openURI("https://github.com/TeamOct/fictional-octo-system")).wrapLabel(false).row();

                d.show();
                 */
            }, () -> !Core.settings.getBool("mod-fos-enabled", false))
    );

    public static void launchNotif(){
        if(!Core.settings.getBool("yellow-enable-special-notifications", true)) return;
        Trio t = nots.random();

        if(t.conditions.get()) YellowVars.ui.notifrag.showTintedNotification(((TextureRegionDrawable)Tex.whiteui).tint(new Color().rand().a(0.5f)),t.icon, t.text, 70, true, t.clicked);
    }


    public static class Trio{
        public Drawable icon;
        public String text;
        public Runnable clicked;
        public Boolp conditions = () -> true;

        public Trio(Drawable icon, String text, Runnable clicked){
            this.icon = icon;
            this.text = text;
            this.clicked = clicked;
        }

        public static Trio with(Drawable a, String b, Runnable c){
            return new Trio(a, b, c);
        }

        public static Trio with(Drawable a, String b, Runnable c, Boolp d){
            Trio t = new Trio(a, b, c);
            t.conditions = d;
            return t;
        }
    }
}