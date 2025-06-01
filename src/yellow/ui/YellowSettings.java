package yellow.ui;

import arc.*;
import arc.func.*;
import arc.graphics.*;
import arc.scene.event.*;
import arc.scene.style.*;
import arc.scene.ui.*;
import arc.scene.ui.layout.*;
import arc.util.*;
import mindustry.*;
import mindustry.gen.*;
import mindustry.ui.*;
import mindustry.ui.dialogs.*;
import yellow.*;
import yellow.util.*;

public class YellowSettings{

    static SettingsMenuDialog.SettingsTable table;
    static boolean updateBlock = false;

    public static void load(){
        Vars.ui.settings.addCategory("Yellow", Tex.alphaaaa, t -> {
            table = t;

            seperatorPref(t, "yellow-general-section", (TextureRegionDrawable) Tex.alphaaaa, Icon.settings);

            t.sliderPref("yellow-notification-time", 5, 3, 60, 1, s -> Core.bundle.format("setting.yellow-notification-time.text", s));

            t.sliderPref("yellow-notification-length", 25, 25, 100, 1, s -> s + "%");

            t.checkPref("yellow-equal-treatment", false);

            seperatorPref(t, "yellow-updating-section", Icon.box, Icon.settings);

            t.checkPref("yellow-check-for-updates", true);

            buttonPref(t, "yellow-check-for-updates-now", () -> {
                UpdateChecker.loadNotifier();
                updateBlock = true;
                Timer.schedule(() -> updateBlock = false, 10f);
            }, b -> b.update(() -> {
                b.touchable = updateBlock ? Touchable.disabled : Touchable.enabled;
                b.setText("[" + (updateBlock ? "gray" : "white") + "]" + Core.bundle.get("setting.yellow-check-for-updates-now.name") + (updateBlock ? "\n" + Core.bundle.get("setting.yellow-check-for-updates-now.halt") : "") + "[]");
            }));

            seperatorPref(t, "yellow-discord-section", Icon.discord, Icon.settings);

            if(!Vars.mobile){
                t.checkPref("yellow-enable-rpc", true);

                buttonPref(t, "yellow-restart-rpc", YellowRPC::restart, b -> b.update(() -> {
                    boolean status = SafeSettings.getBool("yellow-enable-rpc", true, true);

                    b.touchable = status ? Touchable.enabled : Touchable.disabled;
                    b.setText("[" + (status ? "white" : "gray") + "]" + Core.bundle.get("setting.yellow-restart-rpc.name") + "[]");
                }));
            }else{
                labelPref(t, "yellow-unavailable-mobile", null);
            }

            seperatorPref(t, "yellow-info-section", Icon.info, Icon.github);

            tablePref(t, "yellow-info-table", tb -> {
                tb.background(Styles.grayPanel);
                tb.margin(10f);
                tb.center();
                tb.defaults().center();

                tb.table(text -> {
                    text.label(() -> Core.bundle.format(
                            "yellow-version",
                            Yellow.meta().version,
                            UpdateChecker.updateQueued ? "Waiting..." : UpdateChecker.updateAvailable ? Core.bundle.get("yellow-update-available") : Core.bundle.get("yellow-latest")
                    )).row();
                    text.add(Core.bundle.format("yellow-install-date", YellowVars.installTimeAsDate())).row();
                    if(Yellow.debug) text.add(Core.bundle.get("yellow-debug-enabled")).row();
                }).growX().row();

                tb.table(buttons -> {
                    button(buttons, "yellow-source-code", () -> Core.app.openURI("https://github.com/SMOLKEYS/yellow-java")).growX().uniformX();
                    button(buttons, "yellow-issue-reports", () -> Core.app.openURI("https://github.com/SMOLKEYS/yellow-java/issues")).growX().uniformX().row();
                }).growX().padTop(10f).row();
            });

            if(Yellow.debug){
                seperatorPref(t, "yellow-debug-section", Icon.wrench, Icon.settings);

                YellowDebugSettings.build(t);
            }
        });
    }

    public static SettingsMenuDialog.SettingsTable table(){
        return table;
    }

    //button with corner-anchored tooltip instead of cursor

    public static Cell<TextButton> button(Table table, String text, Runnable post){
        return button(table, text, null, post);
    }

    public static Cell<TextButton> button(Table table, String text, @Nullable String description, Runnable post){
        Cell<TextButton> b = table.button(Core.bundle.get(text, text), post);
        Vars.ui.addDescTooltip(b.get(), Core.bundle.get(text + ".description", description));
        return b;
    }

    public static void addSection(String name, Cons<SettingsMenuDialog.SettingsTable> builder){
        seperatorPref(table, name);
        builder.get(table);
    }

    public static void addSection(String name, TextureRegionDrawable leftIcon, TextureRegionDrawable rightIcon, Cons<SettingsMenuDialog.SettingsTable> builder){
        seperatorPref(table, name, leftIcon, rightIcon);
        builder.get(table);
    }

    public static void buttonPref(SettingsMenuDialog.SettingsTable target, String name, Runnable clicked){
        target.pref(new ButtonSetting(name, clicked));
    }

    public static void buttonPref(SettingsMenuDialog.SettingsTable target, String name, Runnable clicked, Cons<TextButton> button){
        target.pref(new ButtonSetting(name, clicked){{
            buttonCons = button;
        }});
    }

    public static void labelPref(SettingsMenuDialog.SettingsTable target, String name, @Nullable Func<SettingsMenuDialog.SettingsTable.Setting, CharSequence> supplier){
        target.pref(new LabelSetting(name, supplier));
    }

    public static void labelPref(SettingsMenuDialog.SettingsTable target, String name, boolean wrap, @Nullable Func<SettingsMenuDialog.SettingsTable.Setting, CharSequence> supplier){
        target.pref(new LabelSetting(name, wrap, supplier));
    }

    public static void seperatorPref(SettingsMenuDialog.SettingsTable target, String name){
        target.pref(new NamedSeperatorSetting(name));
    }

    public static void seperatorPref(SettingsMenuDialog.SettingsTable target, String name, TextureRegionDrawable leftIcon, TextureRegionDrawable rightIcon){
        target.pref(new NamedSeperatorSetting(name, leftIcon, rightIcon));
    }

    public static void tablePref(SettingsMenuDialog.SettingsTable target, String name, Cons<Table> builder){
        target.pref(new TableSetting(name, builder));
    }

    public static class ButtonSetting extends SettingsMenuDialog.SettingsTable.Setting{
        public Runnable clicked;
        public Cons<TextButton> buttonCons;

        public ButtonSetting(String name){
            super(name);
        }

        public ButtonSetting(String name, Runnable clicked){
            super(name);
            this.clicked = clicked;
        }

        @Override
        public void add(SettingsMenuDialog.SettingsTable table){
            TextButton b = new TextButton(title);
            b.clicked(() -> {
                if(clicked != null) clicked.run();
            });
            if(buttonCons != null) buttonCons.get(b);
            table.add(b).minHeight(30).growX().row();

            addDesc(b);
        }
    }

    public static class LabelSetting extends SettingsMenuDialog.SettingsTable.Setting{
        @Nullable
        public Func<SettingsMenuDialog.SettingsTable.Setting, CharSequence> supplier;
        public boolean wrap = true;

        public LabelSetting(String name, @Nullable Func<SettingsMenuDialog.SettingsTable.Setting, CharSequence> supplier){
            super(name);
            this.supplier = supplier;
        }

        public LabelSetting(String name, boolean wrap, @Nullable Func<SettingsMenuDialog.SettingsTable.Setting, CharSequence> supplier){
            super(name);
            this.supplier = supplier;
            this.wrap = wrap;
        }

        @Override
        public void add(SettingsMenuDialog.SettingsTable table){
            Label l = new Label(title);
            l.setWrap(wrap);
            if(supplier != null) l.update(() -> {
                l.setText(supplier.get(this));
            });

            table.table(tb -> {
                tb.center();
                tb.add(l).center();
            }).growX().row();
        }
    }


    public static class NamedSeperatorSetting extends SettingsMenuDialog.SettingsTable.Setting{
        public TextureRegionDrawable leftIcon, rightIcon;

        public NamedSeperatorSetting(String name){
            super(name);
        }

        public NamedSeperatorSetting(String name, TextureRegionDrawable leftIcon, TextureRegionDrawable rightIcon){
            super(name);
            this.leftIcon = leftIcon;
            this.rightIcon = rightIcon;
        }

        @Override
        public void add(SettingsMenuDialog.SettingsTable table){
            table.table(t -> {
                t.center();
                if(leftIcon != null) t.image(() -> leftIcon.getRegion()).size(32f).scaling(Scaling.fit);
                t.add(title).growX().labelAlign(Align.center);
                if(rightIcon != null){
                    t.image(() -> rightIcon.getRegion()).size(32f).scaling(Scaling.fit);
                }else{
                    t.row();
                }
            }).growX().padTop(35f).row();
            table.image().color(Color.gray).height(5f).padTop(5f).padBottom(6f).growX().row();
        }
    }

    public static class TableSetting extends SettingsMenuDialog.SettingsTable.Setting{
        public Cons<Table> builder;

        public TableSetting(String name, Cons<Table> builder){
            super(name);
            this.builder = builder;
        }

        @Override
        public void add(SettingsMenuDialog.SettingsTable table){
            table.table(builder).growX().padTop(10f).row();
        }
    }

}
