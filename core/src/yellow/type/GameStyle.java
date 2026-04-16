package yellow.type;

import arc.*;
import arc.graphics.*;
import arc.struct.*;
import arc.util.*;
import yellow.*;
import yellow.util.*;

public class GameStyle{
    public static final Seq<GameStyle> styles = new Seq<>();

    private final String name;
    public Color[] colors = {Color.white};
    public float onScreenTime = 60*3.4f;

    private final String[] entries;

    public GameStyle(String name){
        this.name = name;
        String[] en = BundleUtils.getSequence("style." + name);
        this.entries = Structs.add(en, Core.bundle.get("style." + name));

        styles.add(this);
    }

    public GameStyle(String name, Color... colors){
        this(name);
        this.colors = colors;
    }

    public GameStyle(String name, Color[] colors, float onScreenTime){
        this(name, colors);
        this.onScreenTime = onScreenTime;
    }

    public String localized(){
        if(colors.length == 1) return "[#" + colors[0].toString().substring(0, 6) + "]" + Structs.random(entries) + "[]";
        return Stringy.gradient(Structs.random(entries), colors);
    }

    @Nullable
    public String description(){
        return Core.bundle.getOrNull("style." + name + ".description");
    }

    public void spawn(@Nullable String modifier){
        YellowVars.stylefrag.entry("+" + localized() + (modifier != null ? modifier : ""), onScreenTime);
    }

    public void spawn(int count){
        spawn(" x" + count);
    }
}
