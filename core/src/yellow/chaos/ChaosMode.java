package yellow.chaos;

import arc.func.*;
import arc.struct.*;
import mindustry.*;
import mindustry.ui.dialogs.SettingsMenuDialog.*;

public class ChaosMode{
    public static Seq<ChaosEvent> options = new Seq<>();

    public static void loadOptions(SettingsTable table){
        options.each(c -> c.buildSettings(table));
    }

    public static class ChaosEvent{
        private static final String namespace = "chaos-mode-setting-";

        //name
        public String name;
        //for instantaneous events
        public Runnable trigger = () -> {};
        //for continuous events; return 'true' to mark this event as finished
        //comes with an extra time variable
        public ChaosRunner update = time -> true;
        //where it can run
        public ChaosRuntime runtime = ChaosRuntime.any;
        //available settings
        public ObjectMap<String, SettingType> availableSettings = new ObjectMap<>();

        public ChaosEvent(){
            options.add(this);
        }

        public void buildSettings(SettingsTable table){
            availableSettings.each((name, type) -> {

            });
        }

        public interface ChaosRunner{
            boolean get(float time);
        }

        public static class ChaosRuntime{
            public static final ChaosRuntime

                menuOnly = new ChaosRuntime(() -> Vars.state.isMenu()),
                gameOnly = new ChaosRuntime(() -> Vars.state.isGame()),
                any = new ChaosRuntime(() -> true);

            public final Boolp run;

            public ChaosRuntime(Boolp run){
                this.run = run;
            }
        }

        public enum SettingType{
            booleanSetting, intSliderSetting, floatSliderSetting;
        }
    }
}
