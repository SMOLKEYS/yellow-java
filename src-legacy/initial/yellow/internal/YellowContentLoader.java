package yellow.internal;

import arc.*;
import arc.struct.*;
import mindustry.*;
import mindustry.game.*;
import mindustry.type.*;

public class YellowContentLoader{
    public static final Seq<YContent> all = new Seq<>();
    public static final Seq<Spell> spells = new Seq<>();
    public static final Seq<NameableWeapon> nameableWeapons = new Seq<>();

    public static void load(){
        YellowBullets.load();
        YellowWeapons.load();
        YellowSpells.load();
        YellowUnitTypes.load();
        YellowWeapons.afterLoad();
        YellowStatusEffects.load();
        YellowBlocks.load();
        if(!Vars.headless) KeyCheats.load();

        Events.run(EventType.Trigger.update, YellowUpdateCore::update);
        
        DisableableWeapon.mirror(new Weapon[]{meltdownBurstAttack, antiMothSpray, decimation, airstrikeFlareLauncher, ghostCall, ghostRain, dualSpeedEngine, igneous}, true, true, true, YellowUnitTypes.yellow);
    }
}
