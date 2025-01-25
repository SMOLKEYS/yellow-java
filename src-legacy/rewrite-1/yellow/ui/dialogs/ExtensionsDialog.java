package yellow.ui.dialogs;

import arc.*;
import arc.scene.ui.*;
import mindustry.gen.*;
import mindustry.ui.dialogs.*;
import yellow.mods.*;
import yellow.util.*;

public class ExtensionsDialog extends BaseDialog{

    public ExtensionsDialog(){
        super("@yellow.extensions");
        addCloseButton();
    }

    public Dialog show(){
        cont.clear();

        cont.defaults().pad(10);
        ExtensionCore.extensions.each(s -> {
            ExtensionCore.ExtensionMeta meta = s.meta;
            cont.table(Tex.pane, item -> {
                item.defaults().pad(10);
                item.table(labeld -> {
                    labeld.labelWrap(Core.bundle.format("yellow.extension-info",
                            Stringy.alternative(meta.displayName, meta.name),
                            Stringy.alternative(meta.description, Core.bundle.get("yellow.extension-nodesc")),
                            Stringy.alternative(meta.author, Core.bundle.get("yellow.extension-noauth")),
                            Stringy.alternative(meta.version, Core.bundle.get("yellow.extension-nover"))
                    )).left();
                }).growX().uniformX();
                item.table(controld -> {
                    controld.button("@yellow.extension-enable", () -> s.meta.enabled(!s.meta.enabled())).update(up -> {
                        up.getLabel().setText(s.meta.enabled() ? "@yellow.extension-disable" : "@yellow.extension-enable");
                    }).growX().height(50);
                }).growX().uniformX();
            }).growX().height(120).row();
        });

        return super.show();
    }
}
