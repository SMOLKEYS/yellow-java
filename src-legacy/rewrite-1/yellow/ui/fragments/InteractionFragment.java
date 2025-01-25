package yellow.ui.fragments;

import arc.graphics.*;
import arc.scene.*;
import arc.util.*;
import yellow.ui.*;

//TODO
@SuppressWarnings({"GrazieInspection", "CodeBlock2Expr"}) //no grazie and no expr lamb
public class InteractionFragment implements CommonFragment{


    @Override
    public void build(Group parent){
        //background display, below everything
        //you could consider this complete, maybe lmao
        parent.fill(background -> {
            background.name = "interactions/background";
            //single image that uses the entire screen
            background.image().color(Color.clear).scaling(Scaling.fill).grow();
        });

        //character display
        //this is gonna suck
        parent.fill(characters -> {
            characters.name = "interactions/characters";
            characters.bottom().left();
        });

        //cutscene/dialogue curtains, comprised of 2 different tables (top and bottom)
        //should be easy enough
        parent.fill(cTop -> {
            cTop.name = "interactions/upper curtain";
            cTop.top();
        });

        parent.fill(cBottom -> {
            cBottom.name = "interactions/lower curtain";
            cBottom.bottom();
        });

        //choices/options menu
        //confusing, since its dynamic (can be resized and repositioned)
        parent.fill(choices -> {
            choices.name = "interactions/choices";
        });

        //dialogue box, above everything
        //should be easy enough, hopefully
        parent.fill(dialogue -> {
            dialogue.name = "interactions/dialogue box";
        });
    }
}
