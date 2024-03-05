package yellow.goodies.vn;

import arc.struct.*;

public class Dialogue{
    public String[] dialogues;
    public ObjectMap<Integer, DialogueParser.DialogueIndex> characterSwitches;

    public int increment = 0;
    private InteractiveCharacter curr;

    public Dialogue(String[] dialogues, ObjectMap<Integer, DialogueParser.DialogueIndex> characterSwitches){
        this.dialogues = dialogues;
        this.characterSwitches = characterSwitches;

        if(characterSwitches.get(0) == null) throw new IllegalArgumentException("index 0 of characterSwitches must have an InteractiveCharacter.");

        curr = characterSwitches.get(0).getCharacter();
    }


    public DialogueParser.DialogueIndex currentData(){
        return characterSwitches.get(increment);
    }

    public InteractiveCharacter currentCharacter(){
        return characterSwitches.get(increment).getCharacter();
    }

    public String currentString(){
        return dialogues[increment];
    }

    public void next(){
        increment++;
    }

    public void prev(){
        if(increment == 0) return;
        increment--;
    }

    public boolean completed(){
        return increment >= dialogues.length;
    }
}
