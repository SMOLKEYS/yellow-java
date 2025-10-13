package yellow.ui.dialogs;

import arc.scene.style.*;
import arc.scene.ui.*;
import arc.util.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.ui.dialogs.*;

public class AchievementListDialog extends BaseDialog{

    private static final String lockName = "[gray]???[]";
    private static final String lockDesc = "[gray]< locked >[]";

    public AchievementListDialog(){
        super("@yellow.achievements");
        addCloseButton();
    }

    @Override
    public Dialog show(){
        cont.clear();

        cont.pane(paneTable -> {
            YellowAchievement.instances.each(achievement -> {
                paneTable.table(Tex.pane, achievementInfo -> {
                    achievementInfo.table(Tex.pane, iconspace -> {
                        iconspace.image(() -> ((TextureRegionDrawable) (achievement.isUnlocked() ? achievement.unlockIcon : achievement.lockIcon)).getRegion()).scaling(Scaling.bounded);
                    }).left().size(140f);

                    achievementInfo.table(infospace -> {
                        infospace.center();
                        infospace.defaults().center();
                        infospace.labelWrap(() -> achievement.isUnlocked() ? achievement.displayName : lockName).labelAlign(Align.center).padBottom(4f).row();
                        infospace.image().color(Pal.accent).height(5f).growX().row();

                        infospace.pane(description -> {
                            description.center();
                            description.defaults().center();
                            description.labelWrap(
                                    () -> achievement.isUnlocked() ? achievement.description : (lockDesc + "\n" + achievement.hint)
                            ).labelAlign(Align.center);
                        }).grow();
                    }).grow().padLeft(5f);
                }).growX().height(170f).row();
            });
        }).grow();

        return super.show();
    }
}
