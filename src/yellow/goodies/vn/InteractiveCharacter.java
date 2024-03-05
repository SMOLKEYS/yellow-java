package yellow.goodies.vn;

import arc.*;
import arc.struct.*;
import yellow.internal.*;

import java.util.*;

public class InteractiveCharacter implements Namec{

    private static final Seq<InteractiveCharacter> instances = new Seq<>();

    public String name, displayName;
    public String shorthand;

    public static InteractiveCharacter
            narrator = new InteractiveCharacter("narrator"){{
                displayName = "";
            }},
            smol = new InteractiveCharacter("smol", "s"),
            yellow = new InteractiveCharacter("yellow", "y");

    public InteractiveCharacter(String name, String shorthand){
        this.name = name;
        this.shorthand = shorthand;

        nameLocalized();

        if(getByShorthand(shorthand) != null) throw new IllegalArgumentException("Attempted to make an InteractiveCharacter with an occupied shorthand: " + shorthand);
        instances.add(this);
    }

    public InteractiveCharacter(String name){
        this(name, name);
    }

    public static InteractiveCharacter getByShorthand(String s){
        return instances.find(e -> Objects.equals(e.shorthand, s));
    }

    @Override
    public String toString(){
        return name;
    }

    @Override
    public String nameLocalized(){
        if(displayName != null) return displayName;
        return displayName = Core.bundle.get("character." + name + ".name");
    }

    @Override
    public String descriptionLocalized(){
        return "mooh~ what are you doing here?";
    }
}
