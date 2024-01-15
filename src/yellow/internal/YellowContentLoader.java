package yellow.internal;

import arc.struct.*;
import mindustry.type.*;
import yellow.content.*;
import yellow.game.*;
import yellow.type.*;

import static yellow.content.YellowWeapons.*;

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
        
        DisableableWeapon.mirror(new Weapon[]{meltdownBurstAttack, antiMothSpray, decimation, airstrikeFlareLauncher, ghostCall, ghostRain, dualSpeedEngine, igneous}, true, true, true, YellowUnitTypes.yellow);
    }
}
