package yellow.goodies.vn;

import arc.struct.*;

public class Dialogue{
    public String name;
    public String[] dialogues;
    public ObjectMap<Integer, DialogueParser.DialogueIndex> indexes;

    public int increment = 0;

    public Dialogue(String name, String[] dialogues, ObjectMap<Integer, DialogueParser.DialogueIndex> indexes){
        this.name = name;
        this.dialogues = dialogues;
        this.indexes = indexes;
    }


    public DialogueParser.DialogueIndex currentData(){
        return indexes.get(increment);
    }

    public InteractiveCharacter currentCharacter(){
        return indexes.get(increment).getCharacter();
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
