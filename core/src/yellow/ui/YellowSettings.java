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
import yellow.YellowVars.*;
import yellow.util.*;

import static arc.Core.*;

public class YellowSettings{

    private static String header;

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

            boolean rapid = YellowVars.build() == BuildType.rapid;
            buttonPref(t, "yellow-check-for-updates-now", () -> {
                UpdateChecker.loadNotifier();
                updateBlock = true;
                Timer.schedule(() -> updateBlock = false, 10f);
            }, b -> b.update(() -> {
                b.touchable = rapid ? Touchable.disabled : updateBlock ? Touchable.disabled : Touchable.enabled;
                // what the fuck why
                if(rapid){
                    b.setText("[gray]" + Core.bundle.get("setting.yellow-check-for-updates-now.disabled-rdb") + "[]");
                    return;
                }

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
                labelPref(t, "yellow-unavailable-mobile", false, null);
            }

            setHeader("yellow-chaos-renderer");

            seperatorPref(t, "yellow-chaos-renderer-section", Icon.warning, Icon.settings);

            buttonPref(t, form("shuffle-seed", false), YellowVars.menuRenderer.config::shuffle);

            t.sliderPref(form("tex-count", false), 500, 100, 2000, 100, proc -> Core.bundle.format(form("tex-count", true), proc));

            //we must divide by ten NOW

            floatSliderPref(t, form("outbound", false), 25, 10, 25, 1, proc -> Core.bundle.format(form("outbound", true), proc / 10f));

            floatSliderPref(t, form("min-speed", false), 5, 0, 150, 1, proc -> Core.bundle.format(form("min-speed", true), proc / 10f));

            floatSliderPref(t, form("max-speed", false), 25, 0, 150, 1, proc -> Core.bundle.format(form("max-speed", true), proc / 10f));

            floatSliderPref(t, form("min-rot-speed", false), 5, 0, 150, 1, proc -> Core.bundle.format(form("min-rot-speed", true), proc / 10f));

            floatSliderPref(t, form("max-rot-speed", false), 20, 0, 150, 1, proc -> Core.bundle.format(form("max-rot-speed", true), proc / 10f));

            t.checkPref(form("reverse", false), false);

            t.checkPref(form("reverse-rot", false), false);

            t.checkPref(form("render-original-renderer", false), false);

            seperatorPref(t, "yellow-extras-section", Icon.add, Icon.settings);

            t.checkPref("yellow-enable-load-renderer", false);

            if(!SafeSettings.getBool("yellow-enable-special-stages", false)) t.checkPref("yellow-enable-special-stages", false);

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

    public static void floatSliderPref(SettingsMenuDialog.SettingsTable target, String name, float def, float min, float max, float step, TypedStringProcessor<Float> s){
        target.pref(new FloatSliderSetting(name, def, min, max, step, s));
    }

    private static void setHeader(String h){
        header = h;
    }

    private static String form(String h, boolean wl){
        return wl ? "setting." + header + "-" + h + ".item" : header + "-" + h;
    }

    private static void multiples(Cons<String> builder, String... values){
        for(String s : values){
            builder.get(s);
        }
    }

    public static class ButtonSetting extends SettingsMenuDialog.SettingsTable.Setting{
        public Runnable clicked;
        public Cons<TextButton> buttonCons;

        protected boolean time;

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

    public static class FloatSliderSetting extends SettingsMenuDialog.SettingsTable.Setting{
        public float def, min, max, step;
        public TypedStringProcessor<Float> sp;

        public FloatSliderSetting(String name, float def, float min, float max, float step, TypedStringProcessor<Float> sp){
            super(name);
            this.def = def;
            this.min = min;
            this.max = max;
            this.step = step;
            this.sp = sp;
        }

        @Override
        public void add(SettingsMenuDialog.SettingsTable table){
            Slider slider = new Slider(min, max, step, false);

            slider.setValue(settings.getFloat(name));

            Label value = new Label("", Styles.outlineLabel);
            Table content = new Table();
            content.add(title, Styles.outlineLabel).left().growX().wrap();
            content.add(value).padLeft(10f).right();
            content.margin(3f, 33f, 3f, 33f);
            content.touchable = Touchable.disabled;

            slider.changed(() -> {
                settings.put(name, slider.getValue());
                value.setText(sp.get(slider.getValue()));
            });

            slider.change();

            addDesc(table.stack(slider, content).width(Math.min(Core.graphics.getWidth() / 1.2f, 460f)).left().padTop(4f).get());
            table.row();
        }
    }


    public interface TypedStringProcessor<T>{
        String get(T t);
    }
}
