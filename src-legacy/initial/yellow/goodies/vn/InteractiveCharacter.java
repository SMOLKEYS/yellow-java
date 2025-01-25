package yellow.goodies.vn;

import arc.*;
import arc.graphics.*;
import arc.scene.ui.*;
import arc.struct.*;
import yellow.*;
import yellow.internal.*;
import yellow.util.*;

import java.util.*;

public class InteractiveCharacter implements Namec{

    public static final Seq<InteractiveCharacter> instances = new Seq<>();

    public String name, displayName;
    public String shorthand;
    public Color color = Color.white;

    public static InteractiveCharacter
            none = new InteractiveCharacter("none"){{
                displayName = "";
            }},
            smol = new InteractiveCharacter("smol"){{
                color = Color.blue;
            }
                @Override
                public void update(Label b){
                    b.setColor(YellowUtils.pulse(Color.white, Color.blue, 15f));
                }
            },
            yellow = new InteractiveCharacter("yellow", "y"){{
                color = Color.yellow;
            }},
            player = new InteractiveCharacter("player", "pl"){{
                color = Color.forest;
            }
                @Override
                public String nameLocalized(){
                    return YellowPermVars.INSTANCE.getStoryName();
                }
            };

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

    public void update(Label b){

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
