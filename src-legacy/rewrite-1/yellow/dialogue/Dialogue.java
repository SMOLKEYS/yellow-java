package yellow.dialogue;

import arc.scene.*;
import arc.struct.*;
import arc.util.*;
import yellow.world.meta.*;

//TODO
public class Dialogue{
    /** Dialogue entry array. */
    public DEntry[] entries;
    /** If not null, when this dialogue reaches its end, the player is given choices. */
    public @Nullable ChoiceMap choices;

    /** A dialogue entry. Each entry can change text, focus character, character state, etc. */
    public static class DEntry{
        /** Text to be typed out. If null, previous entry text is used. */
        public @Nullable String text;
        /** Focus character. If null, the focus character is unchanged. */
        public @Nullable GameCharacter character;
        /** Sprite action to be performed by the focus character. May be null. Does nothing if the focus character is hidden. */
        public @Nullable SpriteAction focusCharacterAction;
        /** Sprite actions to be performed by other visible characters. */
        public @Nullable ObjectMap<GameCharacter, SpriteAction> characterActions;

        /** A wrapper class for a set of {@link Action}s to be performed sequentially or in parallel. */
        public static class SpriteAction{

            public String name;
            public Action[] actions;

            public SpriteAction(String name){
                this.name = name;
            }
        }
    }

    public static class ChoiceMap extends ObjectMap<String, Dialogue>{

    }
}
