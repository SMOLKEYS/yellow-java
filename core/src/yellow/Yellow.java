package yellow;

import arc.*;
import arc.files.*;
import arc.math.*;
import arc.util.*;
import mindustry.*;
import mindustry.game.*;
import mindustry.game.EventType.*;
import mindustry.gen.*;
import mindustry.mod.*;
import mindustry.world.blocks.units.*;
import mindustry.world.modules.*;
import yellow.YellowVars.YellowEventType.*;
import yellow.content.*;
import yellow.entities.*;
import yellow.gen.*;
import yellow.js.*;
import yellow.spec.*;
import yellow.ui.*;
import yellow.util.*;
import yellow.util.SettingBoundVariable.*;

import java.util.*;

import static yellow.YellowSettingValues.*;

//third rewrite note: the more i do this the more i wanna throw my laptop against a wall
@SuppressWarnings({
        "unused", //oh and shut the fuck up java
        "GrazieInspection" //you too intellij
})
public class Yellow extends Mod{

    static final LongSetting lastFileDate = new LongSetting("yellow-debug-lastfiledate", 1997L);

    public static final boolean debug = YellowJVM.hasParameter("yellow-debug", /*() -> Objects.equals(OS.username, "smol"),*/ str -> {
        Log.infoTag(str, "Yellow debug mode enabled.");

        Events.run(YellowVarsPostInit.class, () -> {
            Date d = new Date(lastFileDate.get());
            Date nd = new Date(mod().file.lastModified());

            YellowVars.notifrag.showPersistentNotification(
                    Icon.wrench,
                    Strings.format(
                            "Yellow debug info:\n\nPast file time: @\n\nLoaded file: [gold]@[]\nFile time: @",
                            d,
                            mod().file.name(),
                            nd
                    )
            );

            YellowVars.notifrag.showPersistentNotification(
                    Icon.wrench,
                    Strings.format(
                            "Chaos stage: [red]@[]\nActive class: [magenta]@[]",
                            Chaos.stageIndex(),
                            null //Chaos.stage()
                    )
            );

            YellowVars.notifrag.showPersistentNotification(
                    Icon.wrench,
                    Strings.format(
                            "Mod info:\n\nName: [gold]@[]\nMinimum version: [blue]@[]\nMod version: [green]@[]\nActive class: [magenta]@[]",
                            Yellow.meta().internalName,
                            Yellow.meta().minGameVersion,
                            Yellow.meta().version,
                            Yellow.mod().main
                    )
            );

            lastFileDate.set(nd.getTime());
        });
    });

    public Yellow(){
        if(Vars.clientLoaded) YellowVars.onImport();

        if(!Vars.clientLoaded){
            YellowGroups.init();
            YellowVars.preInit();
        }

        Events.run(EventType.ClientLoadEvent.class, () -> {
            YellowVars.init();
            if(!Vars.mobile && enableRpc.get()) YellowRPC.init();
            YellowSettings.load();
            Rhinor.importMainModPackages(this);
            YellowVars.initNatives();

            if(enableAutoupdate.get()) UpdateChecker.loadNotifier();
        });
    }

    public static Mods.ModMeta meta(){
        return Vars.mods.getMod(Yellow.class).meta;
    }

    public static Mods.LoadedMod mod(){
        return Vars.mods.getMod(Yellow.class);
    }

    public static Fi configDir(){
        Fi f = Core.settings.getDataDirectory().child("smol_common").child("yellow");
        f.mkdirs();
        return f;
    }

    @Override
    public void loadContent(){
        EntityRegistry.register();
        YellowWeapons.load();
        YellowUnitTypes.load();
        YellowWeapons.afterLoad();
        Events.run(Trigger.update, YellowGroups::update);

        Events.on(UnitDestroyEvent.class, e -> {
            if(!enableUnitDrops.get()) return;

            UnitFactory blk = (UnitFactory) Vars.content.blocks().find(bl -> bl instanceof UnitFactory ul && ul.plans.contains(up -> up.unit == e.unit.type));
            if(blk != null){
                Structs.each(req -> {
                    for(int i = 0; i < req.amount*0.5f; i++){
                        ItemEntity en = ItemEntity.create();
                        en.x = e.unit.x + Mathf.range(e.unit.hitSize);
                        en.y = e.unit.y + Mathf.range(e.unit.hitSize);
                        en.stack.item = req.item;
                        en.stack.amount = 1;
                        en.vel.set(Mathf.range(5.5f + (e.unit.hitSize/9f)), Mathf.range(5.5f + (e.unit.hitSize/9f)));
                        en.drag = Mathf.random(0.05f, 0.1f);
                        en.add();
                    }
                }, blk.plans.find(ul -> ul.unit == e.unit.type).requirements);
            }else{
                Reconstructor rec = (Reconstructor) Vars.content.blocks().find(bl -> bl instanceof Reconstructor ul && ul.upgrades.contains(erk -> Structs.contains(erk, e.unit.type)));
                if(rec != null){
                    Structs.each(itm -> {
                        for(int i = 0; i < itm.amount*0.5f; i++){
                            ItemEntity en = ItemEntity.create();
                            en.x = e.unit.x + Mathf.range(e.unit.hitSize);
                            en.y = e.unit.y + Mathf.range(e.unit.hitSize);
                            en.stack.item = itm.item;
                            en.stack.amount = 1;
                            en.vel.set(Mathf.range(5.5f + (e.unit.hitSize/9f)), Mathf.range(5.5f + (e.unit.hitSize/9f)));
                            en.drag = Mathf.random(0.05f, 0.1f);
                            en.add();
                        }
                    }, rec.requirements);
                }
            }
        });

        Events.on(BlockDestroyEvent.class, e -> {
            ItemModule mod = e.tile.build.items;
            if(mod != null && !mod.empty() && enableBuildInventoryDrops.get()){
                mod.each((item, amount) -> {
                    for(int i = 0; i < amount; i++){
                        ItemEntity en = ItemEntity.create();
                        en.x = e.tile.x*8 + Mathf.range(e.tile.block().size*8f);
                        en.y = e.tile.y*8 + Mathf.range(e.tile.block().size*8f);
                        en.stack.item = item;
                        en.stack.amount = 1;
                        en.vel.set(Mathf.range(5.5f + e.tile.block().size), Mathf.range(5.5f + e.tile.block().size));
                        en.drag = Mathf.random(0.05f, 0.1f);
                        en.add();
                    }
                });
            }

            if(enableBuildDrops.get()) Structs.each(stk -> {
                for(int i = 0; i < stk.amount*0.2f; i++){
                    ItemEntity en = ItemEntity.create();
                    en.x = e.tile.x*8 + Mathf.range(e.tile.block().size*8f);
                    en.y = e.tile.y*8 + Mathf.range(e.tile.block().size*8f);
                    en.stack.item = stk.item;
                    en.stack.amount = 1;
                    en.vel.set(Mathf.range(5.5f + e.tile.block().size), Mathf.range(5.5f + e.tile.block().size));
                    en.drag = Mathf.random(0.05f, 0.1f);
                    en.add();
                }
            }, e.tile.block().requirements);
        });
    }
}
