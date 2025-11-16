package yellow.ui;

import arc.audio.*;
import arc.func.*;
import arc.graphics.*;
import arc.scene.event.*;
import arc.scene.style.*;
import arc.scene.ui.*;
import arc.scene.ui.Label.*;
import arc.scene.ui.layout.*;
import arc.util.*;
import mindustry.*;
import mindustry.gen.*;
import mindustry.ui.*;
import mindustry.ui.dialogs.SettingsMenuDialog.*;
import mindustry.ui.dialogs.SettingsMenuDialog.SettingsTable.*;
import yellow.*;
import yellow.YellowVars.*;
import yellow.spec.*;
import yellow.util.*;

import java.util.concurrent.atomic.*;

import static arc.Core.*;
import static yellow.YellowSettingValues.*;

public class YellowSettings{

    private static String header;

    static SettingsTable table;
    static boolean updateBlock = false;

    public static void load(){
        Vars.ui.settings.addCategory("Yellow", Tex.alphaaaa, t -> {
            table = t;

            seperatorPref(t, "yellow-general-section", (TextureRegionDrawable) Tex.alphaaaa, Icon.settings);

            floatSliderPref(t, "yellow-notification-time", 5, 3, 60, 1, s -> bundle.format("setting.yellow-notification-time.text", s));

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
                    b.setText("[gray]" + bundle.get("setting.yellow-check-for-updates-now.disabled-rdb") + "[]");
                    return;
                }

                b.setText("[" + (updateBlock ? "gray" : "white") + "]" + bundle.get("setting.yellow-check-for-updates-now.name") + (updateBlock ? "\n" + bundle.get("setting.yellow-check-for-updates-now.halt") : "") + "[]");
            }));

            seperatorPref(t, "yellow-discord-section", Icon.discord, Icon.settings);

            if(!Vars.mobile){
                t.checkPref("yellow-enable-rpc", true);

                buttonPref(t, "yellow-restart-rpc", YellowRPC::restart, b -> b.update(() -> {
                    boolean status = enableRpc.get(true);

                    b.touchable = status ? Touchable.enabled : Touchable.disabled;
                    b.setText("[" + (status ? "white" : "gray") + "]" + bundle.get("setting.yellow-restart-rpc.name") + "[]");
                }));
            }else{
                labelPref(t, "yellow-unavailable-mobile", false, null);
            }

            setHeader("yellow-chaos-renderer");

            seperatorPref(t, "yellow-chaos-renderer-section", Icon.warning, Icon.settings);

            buttonPref(t, form("shuffle-seed", false), YellowSettingValues::shuffleChaosRenderer);

            t.sliderPref(form("tex-count", false), 500, 100, 2000, 100, proc -> bundle.format(form("tex-count", true), proc));

            //we must divide by ten NOW

            floatSliderPref(t, form("outbound", false), 25, 10, 25, 1, proc -> bundle.format(form("outbound", true), proc / 10f));

            floatSliderPref(t, form("min-speed", false), 5, 0, 150, 1, proc -> bundle.format(form("min-speed", true), proc / 10f));

            floatSliderPref(t, form("max-speed", false), 25, 0, 150, 1, proc -> bundle.format(form("max-speed", true), proc / 10f));

            floatSliderPref(t, form("min-rot-speed", false), 5, 0, 150, 1, proc -> bundle.format(form("min-rot-speed", true), proc / 10f));

            floatSliderPref(t, form("max-rot-speed", false), 20, 0, 150, 1, proc -> bundle.format(form("max-rot-speed", true), proc / 10f));

            t.checkPref(form("reverse", false), false);

            t.checkPref(form("reverse-rot", false), false);

            t.checkPref(form("render-original-renderer", false), false);

            seperatorPref(t, "yellow-extras-section", Icon.add, Icon.settings);

            t.checkPref("yellow-enable-load-renderer", false);

            t.checkPref("yellow-enable-unit-drops", false);

            t.checkPref("yellow-enable-unit-inv-drops", false);

            t.checkPref("yellow-enable-build-drops", false);

            t.checkPref("yellow-enable-build-inv-drops", false);

            t.checkPref("yellow-gravitate-on-empty-inventory", false);

            t.checkPref("yellow-gravitate-items", true);

            seperatorPref(t, "yellow-info-section", Icon.info, Icon.github);

            tablePref(t, "yellow-info-table", tb -> {
                tb.background(Styles.grayPanel);
                tb.margin(10f);
                tb.center();
                tb.defaults().center();

                tb.table(text -> {
                    text.label(() -> bundle.format(
                            "yellow-version",
                            Yellow.meta().version,
                            UpdateChecker.updateQueued ? "Waiting..." : UpdateChecker.updateAvailable ? bundle.get("yellow-update-available") : bundle.get("yellow-latest")
                    )).row();
                    text.add(bundle.format("yellow-install-date", YellowVars.installedAt())).row();
                    if(Yellow.debug) text.add(bundle.get("yellow-debug-enabled")).row();
                }).growX().row();

                tb.table(buttons -> {
                    float[] fl = {0};
                    MiscUtils.apply(button(buttons, "yellow-source-code", () -> app.openURI("https://github.com/SMOLKEYS/yellow-java")).growX().uniformX().get(), cb -> {
                        boolean[] ticked = {false};

                        cb.update(() -> {
                            if(ticked[0]) return;

                            if(cb.isPressed()){
                                fl[0] += Time.delta;
                            }else{
                                fl[0] = 0f;
                            }

                            if(fl[0] >= (Yellow.debug ? 35 : 60*10)){
                                fl[0] = -99999f;
                                Chaos.kickFromSave();
                                YellowVars.blankfrag.show();
                                Chaos.hideAllDialogs(true);
                                Chaos.stopAudioBus();
                                AtomicReference<String> s = new AtomicReference<>("my brother in christ");
                                AtomicReference<Table> tbl = new AtomicReference<>();
                                YellowVars.blankfrag.table.fill(p -> {
                                    p.center();
                                    p.defaults().center();
                                    p.label(s::get).fontScale(4).get().setStyle(new LabelStyle(YellowFonts.gothic, Color.white));

                                    tbl.set(p);
                                });
                                Time.run(60, () -> {
                                    s.set("boo");
                                    Chaos.startAudioBus();
                                    Sounds.wind3.setBus(new AudioBus());
                                    Sounds.wind3.play(25);
                                    Time.run(20, () -> {
                                        tbl.get().remove();
                                        Sounds.wind3.stop();
                                        Sounds.wind3.setBus(audio.soundBus);
                                        YellowVars.blankfrag.hide();
                                        YellowVars.notifrag.showNotification(Icon.star, "youer are di d it");
                                        ticked[0] = true;
                                    });
                                });
                            }
                        });
                    });
                    button(buttons, "yellow-issue-reports", () -> app.openURI("https://github.com/SMOLKEYS/yellow-java/issues")).growX().uniformX().row();
                }).growX().padTop(10f).row();
            });

            if(Yellow.debug){
                seperatorPref(t, "yellow-debug-section", Icon.wrench, Icon.settings);

                YellowDebugSettings.build(t);
            }
        });
    }

    public static SettingsTable table(){
        return table;
    }

    //button with corner-anchored tooltip instead of cursor

    public static Cell<TextButton> button(Table table, String text, Runnable post){
        return button(table, text, null, post);
    }

    public static Cell<TextButton> button(Table table, String text, @Nullable String description, Runnable post){
        Cell<TextButton> b = table.button(bundle.get(text, text), post);
        Vars.ui.addDescTooltip(b.get(), bundle.get(text + ".description", description));
        return b;
    }

    public static void addSection(String name, Cons<SettingsTable> builder){
        seperatorPref(table, name);
        builder.get(table);
    }

    public static void addSection(String name, TextureRegionDrawable leftIcon, TextureRegionDrawable rightIcon, Cons<SettingsTable> builder){
        seperatorPref(table, name, leftIcon, rightIcon);
        builder.get(table);
    }

    public static void buttonPref(SettingsTable target, String name, Runnable clicked){
        target.pref(new ButtonSetting(name, clicked));
    }

    public static void buttonPref(SettingsTable target, String name, Runnable clicked, Cons<TextButton> button){
        target.pref(new ButtonSetting(name, clicked){{
            buttonCons = button;
        }});
    }

    public static void labelPref(SettingsTable target, String name, @Nullable Func<Setting, CharSequence> supplier){
        target.pref(new LabelSetting(name, supplier));
    }

    public static void labelPref(SettingsTable target, String name, boolean wrap, @Nullable Func<Setting, CharSequence> supplier){
        target.pref(new LabelSetting(name, wrap, supplier));
    }

    public static void seperatorPref(SettingsTable target, String name){
        target.pref(new NamedSeperatorSetting(name));
    }

    public static void seperatorPref(SettingsTable target, String name, TextureRegionDrawable leftIcon, TextureRegionDrawable rightIcon){
        target.pref(new NamedSeperatorSetting(name, leftIcon, rightIcon));
    }

    public static void tablePref(SettingsTable target, String name, Cons<Table> builder){
        target.pref(new TableSetting(name, builder));
    }

    public static void floatSliderPref(SettingsTable target, String name, float def, float min, float max, float step, TypedStringProcessor<Float> s){
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

    public static class ButtonSetting extends Setting{
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
        public void add(SettingsTable table){
            TextButton b = new TextButton(title);
            b.clicked(() -> {
                if(clicked != null) clicked.run();
            });
            if(buttonCons != null) buttonCons.get(b);
            table.add(b).minHeight(30).growX().row();

            addDesc(b);
        }
    }

    public static class LabelSetting extends Setting{
        @Nullable
        public Func<Setting, CharSequence> supplier;
        public boolean wrap = true;

        public LabelSetting(String name, @Nullable Func<Setting, CharSequence> supplier){
            super(name);
            this.supplier = supplier;
        }

        public LabelSetting(String name, boolean wrap, @Nullable Func<Setting, CharSequence> supplier){
            super(name);
            this.supplier = supplier;
            this.wrap = wrap;
        }

        @Override
        public void add(SettingsTable table){
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


    public static class NamedSeperatorSetting extends Setting{
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
        public void add(SettingsTable table){
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

    public static class TableSetting extends Setting{
        public Cons<Table> builder;

        public TableSetting(String name, Cons<Table> builder){
            super(name);
            this.builder = builder;
        }

        @Override
        public void add(SettingsTable table){
            table.table(builder).growX().padTop(10f).row();
        }
    }

    public static class FloatSliderSetting extends Setting{
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
        public void add(SettingsTable table){
            Slider slider = new Slider(min, max, step, false);

            slider.setValue(SafeSettings.getFloat(name, def));

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

            addDesc(table.stack(slider, content).width(Math.min(graphics.getWidth() / 1.2f, 460f)).left().padTop(4f).get());
            table.row();
        }
    }


    public interface TypedStringProcessor<T>{
        String get(T t);
    }
}
