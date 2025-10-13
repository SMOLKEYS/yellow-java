package yellow.ui;

import arc.*;
import arc.scene.event.*;
import arc.scene.ui.layout.*;
import mindustry.gen.*;
import mindustry.ui.fragments.*;

import static mindustry.Vars.*;

public class YellowUI{

    public WeaponManagerDialog weaponManager;
    public SpellManagerDialog spellManager;
    public ExtensionsDialog extensions;
    public AchievementListDialog achievements;

    public WidgetGroup multiGroup;

    public NotificationFragment notifrag;
    public FirstLoadFragment firstfrag;
    public BlankFragment blankfrag;
    public ManagerFragment managefrag;
    public InteractionFragment intfrag;
    public LivesFragment lifefrag;

    public void init(){
        weaponManager = new WeaponManagerDialog();
        spellManager = new SpellManagerDialog();
        extensions = new ExtensionsDialog();
        achievements = new AchievementListDialog();

        notifrag = new NotificationFragment();
        firstfrag = new FirstLoadFragment();
        blankfrag = new BlankFragment();
        managefrag = new ManagerFragment();
        lifefrag = new LivesFragment();

        intfrag = new InteractionFragment();

        multiGroup = new WidgetGroup();

        multiGroup.setFillParent(true);
        multiGroup.touchable = Touchable.childrenOnly;
        multiGroup.visible(() -> true);

        Core.scene.add(multiGroup);

        notifrag.build(multiGroup);
        firstfrag.build(ui.menuGroup);
        blankfrag.build(multiGroup);
        managefrag.build(ui.hudGroup);
        lifefrag.build(ui.hudGroup);


        MenuFragment.MenuButton[] buttons = new MenuFragment.MenuButton[]{
                new MenuFragment.MenuButton("@yellow.achievements", Icon.tree, achievements::show)
        };

        MenuFragment.MenuButton pcButton = new MenuFragment.MenuButton("@yellow", Tex.alphaaaa, () -> {}, buttons);

        if(mobile){
            for(MenuFragment.MenuButton b: buttons) ui.menufrag.addButton(b);
        }else{
            ui.menufrag.addButton(pcButton);
        }
    }
}
