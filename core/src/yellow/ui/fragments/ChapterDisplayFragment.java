package yellow.ui.fragments;

import arc.Core;
import arc.flabel.FLabel;
import arc.graphics.g2d.Draw;
import arc.math.Interp;
import arc.math.Mathf;
import arc.scene.Element;
import arc.scene.Group;
import arc.scene.actions.Actions;
import arc.scene.event.Touchable;
import arc.scene.ui.Label;
import arc.scene.ui.layout.Table;
import arc.util.Align;
import arc.util.Time;
import mindustry.ui.Styles;
import mindustry.ui.WarningBar;
import yellow.math.Mathy;
import yellow.ui.CommonFragment;
import yellow.util.Timey;

public class ChapterDisplayFragment implements CommonFragment {

    private float bgA = 0f, bgOA = 0f, bgTA = 0f;
    private Table main;
    private Label titleLabel;
    private FLabel textLabel;
    private WarningBar topBar, bottomBar;

    private final Element[] all = new Element[4];

    @Override
    public void build(Group parent) {
        parent.fill(t -> {
            main = t;
            t.rect((x, y, w, h) -> {
                bgA = Mathf.approach(bgOA, bgTA, 1f);
                Draw.alpha(bgA);
                Styles.black.draw(0f, 0f, Core.graphics.getWidth(), Core.graphics.getHeight());
            });
            t.visible = false;
            t.touchable = Touchable.enabled;
            topBar = new WarningBar();
            bottomBar = new WarningBar();
            titleLabel = new Label("");
            textLabel = new FLabel("");

            t.stack(
                    new Table(bars -> {
                        bars.center();
                        bars.add(topBar).growX().height(23f).row();
                        bars.add().height(190f).row();
                        bars.add(bottomBar).growX().height(23f).row();
                    }),
                    new Table(texts -> {
                        texts.center();
                        texts.add(titleLabel).fontScale(0.9f).row();
                        texts.add(textLabel).fontScale(2.5f).row();
                    })
            ).fill().grow();
            t.row();

            all[0] = topBar;
            all[1] = bottomBar;
            all[2] = titleLabel;
            all[3] = textLabel;

            reset();
        });
    }

    private void reset(){
        for (Element element : all) {
            element.color.a(0f);
            element.visible = false;
        }
        textLabel.color.a(1f);
        textLabel.restart("");
        main.visible = false;
    }

    private void updateBars(float a, float at){
        a = a == -1 ? topBar.color.a : a;

        topBar.visible = bottomBar.visible = true;
        topBar.actions(Actions.alpha(a, at, Interp.fastSlow));
        bottomBar.actions(Actions.alpha(a, at, Interp.fastSlow));
    }

    private void updateTitle(String t){
        titleLabel.visible = true;
        titleLabel.setText(t);
        titleLabel.actions(Actions.alpha(1f, 1f, Interp.smooth));
    }

    private void updateText(String t){
        textLabel.visible = true;
        textLabel.restart(t);
    }

    private void updateBG(float a){
        bgOA = bgA;
        bgTA = a;
    }

    public void show(String title, String text) {
        reset();
        main.visible = true;
        //bg fade-in
        Time.run(0f, () -> updateBG(1f));
        //bar fade-in
        Time.run(75f, () -> updateBars(1f, 0.9f));
        //text fade-in
        Time.run(95f, () -> updateTitle(title));
        //title type
        Time.run(165f, () -> updateText(text));
        //fade all
        Time.run(290f, () -> {
            for (Element element : all) {
                element.actions(Actions.alpha(0f, 2f));
            }
            updateBars(0f, 0.8f);
        });
        //clear
        Time.run(500f, this::reset);
    }
}
