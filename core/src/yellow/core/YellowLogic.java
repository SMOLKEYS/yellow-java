package yellow.core;

import arc.*;
import arc.math.*;
import arc.util.*;
import mindustry.*;
import mindustry.game.EventType.*;
import mindustry.world.blocks.units.*;
import mindustry.world.modules.*;
import yellow.core.YellowEventType.*;
import yellow.entities.*;
import yellow.gen.*;

import static yellow.YellowSettingValues.*;
import static yellow.YellowSettingValues.enableBuildDrops;
import static yellow.YellowSettingValues.enableBuildInventoryDrops;

public class YellowLogic{

    public YellowLogic(){

        //groups
        Events.run(Trigger.afterGameUpdate, YellowGroups::update);

        //item drops
        Events.on(UnitDestroyEvent.class, e -> {
            if(!enableUnitDrops.get()) return;

            UnitFactory blk = (UnitFactory) Vars.content.blocks().find(bl -> bl instanceof UnitFactory ul && ul.plans.contains(up -> up.unit == e.unit.type));
            if(blk != null){
                Structs.each(req -> {
                    for(int i = 0; i < req.amount*0.5f; i++){
                        float x = e.unit.x + Mathf.range(e.unit.hitSize);
                        float y = e.unit.y + Mathf.range(e.unit.hitSize);

                        ItemEntitySpawner.spawn(
                                req.item,
                                1,
                                1,
                                x,
                                y,
                                Tmp.v1.set(Mathf.range(5.5f + (e.unit.hitSize/9f)), Mathf.range(5.5f + (e.unit.hitSize/9f))),
                                Mathf.random(0.05f, 0.1f)
                        );
                    }
                }, blk.plans.find(ul -> ul.unit == e.unit.type).requirements);
            }else{
                Reconstructor rec = (Reconstructor) Vars.content.blocks().find(bl -> bl instanceof Reconstructor ul && ul.upgrades.contains(erk -> Structs.contains(erk, e.unit.type)));
                if(rec != null){
                    Structs.each(itm -> {
                        for(int i = 0; i < itm.amount*0.5f; i++){
                            float x = e.unit.x + Mathf.range(e.unit.hitSize);
                            float y = e.unit.y + Mathf.range(e.unit.hitSize);

                            ItemEntitySpawner.spawn(
                                    itm.item,
                                    1,
                                    1,
                                    x,
                                    y,
                                    Tmp.v1.set(Mathf.range(5.5f + (e.unit.hitSize/9f)), Mathf.range(5.5f + (e.unit.hitSize/9f))),
                                    Mathf.random(0.05f, 0.1f)
                            );
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
                        float x = e.tile.x*8 + Mathf.range(e.tile.block().size*8f);
                        float y = e.tile.y*8 + Mathf.range(e.tile.block().size*8f);
                        ItemEntitySpawner.spawn(
                                item,
                                1,
                                1,
                                x,
                                y,
                                Tmp.v1.set(5.5f + e.tile.block().size, Mathf.range(5.5f + e.tile.block().size)),
                                Mathf.random(0.05f, 0.1f),
                                e.tile.build,
                                null
                        );
                    }
                });
            }

            if(enableBuildDrops.get()) Structs.each(stk -> {
                for(int i = 0; i < stk.amount*0.2f; i++){
                    float x = e.tile.x*8 + Mathf.range(e.tile.block().size*8f);
                    float y = e.tile.y*8 + Mathf.range(e.tile.block().size*8f);
                    ItemEntitySpawner.spawn(
                            stk.item,
                            1,
                            1,
                            x,
                            y,
                            Tmp.v1.set(5.5f + e.tile.block().size, Mathf.range(5.5f + e.tile.block().size)),
                            Mathf.random(0.05f, 0.1f),
                            e.tile.build,
                            null
                    );
                }
            }, e.tile.block().requirements);
        });
    }
}
