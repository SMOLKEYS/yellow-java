package yellow.ui.scene;

import arc.*;
import arc.flabel.*;
import arc.scene.ui.*;
import arc.scene.ui.Label.*;
import arc.util.*;

public class FLabelTextButton extends TextButton{
    protected final FLabel flabel;

    public FLabelTextButton(String text){
        this(text, Core.scene.getStyle(TextButtonStyle.class));
    }

    public FLabelTextButton(String text, TextButtonStyle style){
        super(text, style);
        setStyle(style);
        flabel = new FLabel(text);
        flabel.setStyle(new LabelStyle(style.font, style.fontColor));
        flabel.setAlignment(Align.center);
        add(flabel).expand().fill().wrap().minWidth(getMinWidth());
        setSize(getPrefWidth(), getPrefHeight());
        removeChild(label);
    }

    @Override
    public Label getLabel(){
        return getFLabel();
    }

    public FLabel getFLabel(){
        return flabel;
    }
}
