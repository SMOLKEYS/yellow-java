package yellow;

import arc.*;
import arc.files.*;
import arc.math.*;
import arc.scene.style.*;
import arc.util.*;
import arc.util.serialization.*;
import java.util.*;
import mindustry.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.mod.*;
import mindustry.ui.dialogs.*;
import yellow.util.*;

public final class Autoupdater{

    private static BaseDialog updateFoundDialog, selectionDialog;

    public static void scan(boolean manual){
        if(!Core.settings.getBool("yellow-check-for-updates", true) && !manual) return;

        Mods.ModMeta meta = Yellow.meta();

        YellowNetworking.repoReleases(YellowVars.getUpdateServer(), res -> {
            String[] set = new String[res.size];
            for(int i = 0; i < res.size; i++) set[i] = res.get(i).getString("tag_name", "shit");

            String latest = res.get(res.size).getString("tag_name");
            String current = meta.version;

            float latestI = Stringy.handleNumber(latest);
            float currentI = Stringy.handleNumber(current);

            int distance = Structsy.distance(set, currentI, latestI);

            if(!Structs.contains(set, currentI)){
                YellowVars.ui.notifrag.showTintedNotification(((TextureRegionDrawable)Tex.whiteui).tint(Pal.gray.cpy().a(0.5f)), Icon.cancel, Core.bundle.format("yellow.unknownver", currentI), 70, true,
                        () -> Vars.ui.showInfo(Core.bundle.format("yellow.unknownver-dialog", meta.version, YellowVars.getUpdateServer(), Stringy.prettyPrint(set, false)))
                );
            }

            if(latestI > currentI){
                YellowVars.ui.notifrag.showPersistentNotification(Core.bundle.format("yellow.newver", distance));
            }

        }, e -> Core.app.post(() -> {
            Log.err(e);
            YellowVars.ui.notifrag.showErrorNotification("@yellow.failver", e);
        }));
    }

    /** @apiNote Holy. Shit. */
    public static void checkForUpdates(boolean manual){
        if(!Core.settings.getBool("yellow-check-for-updates", true) && !manual) return;

        Mods.ModMeta meta = Yellow.meta();

        YellowNetworking.repoReleases(YellowVars.getUpdateServer(), root -> {
            String[] versions = new String[root.size + 1];
            //tag_name cannot possibly be blank/null, as it is literally not allowed, but sometimes the edge cases matter
            for(int i = 0; i < root.size; i++) versions[i] = root.get(i).getString("tag_name", "0N");
            versions[versions.length - 1] = "Cancel";

            Structsy.eachIndexed((s, i) -> {
                if(s == null){
                    Log.warn("Autoupdater version collection found a null entry! Correcting...");
                    versions[i] = "[red]<broken entry>[]";
                }
            }, versions);

            String lastEntry = versions[0];
            int distance = Structsy.distance(versions, meta.version, lastEntry);
            int id = Mathf.random(90000);

            if(!Structs.contains(versions, meta.version)){
                YellowVars.ui.notifrag.showTintedNotification(((TextureRegionDrawable)Tex.whiteui).tint(Pal.gray.cpy().a(0.5f)), Icon.cancel, Core.bundle.format("yellow.unknownver", meta.version), 70, true,
                        () -> Vars.ui.showInfo(Core.bundle.format("yellow.unknownver-dialog", meta.version, YellowVars.getUpdateServer(), Stringy.prettyPrint(Structs.remove(versions, "Cancel"), false)))
                );
            }else if(!Objects.equals(meta.version, lastEntry)){
                YellowVars.ui.notifrag.showPersistentNotification(Core.bundle.format("yellow.newver", distance), () -> showMenu(id, meta, versions, root, distance));
            }
        }, e -> Core.app.post(() -> {
            Log.err(e);
            YellowVars.ui.notifrag.showErrorNotification("@yellow.failver", e);
        }));
    }

    private static void showMenu(int id, Mods.ModMeta meta, String[] versions, JsonValue root, int distance){
        String[][] availableVersions = new String[distance + 1][1];
        for(int i = 0; i < distance; i++){
            availableVersions[i][0] = versions[i];
        }
        availableVersions[availableVersions.length - 1][0] = "Cancel";

        Mods.LoadedMod mod = Yellow.mod();

        Vars.ui.showFollowUpMenu(
                id,
                "@yellow.newver-update",
                Core.bundle.format("yellow.newver-dialog", meta.version),
                availableVersions,
                r -> {
                    if(r == -1 || r == availableVersions.length - 1){
                        Vars.ui.hideFollowUpMenu(id);
                        return;
                    }

                    String ver = availableVersions[r][0];

                    JsonValue mainAsset = root.get(Structs.indexOf(versions, ver)).get("assets");
                    String[][] assets = new String[mainAsset.size + 1][1];
                    for(int i = 0; i < mainAsset.size; i++)
                        assets[i][0] = mainAsset.get(i).getString("name", "bruh???").replace("yellow-rewritten.jar", "[accent]yellow-rewritten.jar[]");
                    assets[assets.length - 1][0] = "Cancel";

                    Vars.ui.showFollowUpMenu(
                            id,
                            "@yellow.newver-update",
                            Core.bundle.format("yellow.newver-select", meta.version, ver),
                            assets,
                            r2 -> {
                                if(r2 == -1){
                                    Vars.ui.hideFollowUpMenu(id);
                                    return;
                                }

                                if(r2 == assets.length - 1){
                                    showMenu(id, meta, versions, root, distance);
                                    return;
                                }

                                Vars.ui.hideFollowUpMenu(id);
                                Vars.ui.loadfrag.show("[accent]Downloading...[]");

                                JsonValue mval = mainAsset.get(Structsy.indexOf(assets, Strings.stripColors(assets[r2][0])));
                                float size = mval.getFloat("size");

                                Http.get(mval.getString("browser_download_url"), res -> {
                                    byte[] out = res.getResult();
                                    Vars.ui.loadfrag.setProgress(out.length / size);

                                    Fi tg = Yellow.configDir().child("jars").child(Strings.stripColors(assets[r2][0]).replace(".jar", "") + ver + ".jar");
                                    tg.writeBytes(out);

                                    Vars.mods.importMod(tg);

                                    Vars.ui.loadfrag.hide();

                                    Vars.ui.showInfoOnHidden(Core.bundle.format("yellow.restart", mod.file.name(), tg.name()), Core.app::exit);
                                }, e -> Core.app.post(() -> {
                                    Log.err(e);
                                    Vars.ui.showException("Could not download update " + ver + " jar", e);
                                    Vars.ui.loadfrag.hide();
                                }));
                            }
                    );
                });

    }

}
