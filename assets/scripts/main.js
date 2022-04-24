/*
@author GlennFolker
*/
function iclass(name){
    importClass(new NativeJavaClass(Vars.mods.getScripts().scope, Class.forName(name, true, Vars.mods.mainLoader())));
}

global.resetPrepared = () => Core.settings.put("yellow-java-prepared", false);

//setup mod desc
let meta = Vars.mods.getMod("yellow-java").meta;
meta.description = "A random piece of chaos. [scarlet]Android/PC only.";

//import all mod classes

iclass("yellow.Yellow");
iclass("yellow.content.YellowUnitTypes");
iclass("yellow.content.YellowBlocks");
iclass("yellow.content.YellowBullets");
iclass("yellow.content.YellowPlanets");
iclass("yellow.content.YellowFx");
iclass("yellow.content.YellowAchievements");
iclass("yellow.content.YellowStatusEffects");
iclass("yellow.entities.abilities.RespawnAbility");
iclass("yellow.entities.units.GhostUnitType");
iclass("yellow.entities.units.DisableableWeaponMount");
iclass("yellow.entities.units.entity.GhostUnitEntity");
iclass("yellow.entities.bullet.AirstrikeFlare");
iclass("yellow.interactions.Responses");
iclass("yellow.interactions.YellowState");
iclass("yellow.interactions.ui.ChatBubble");
iclass("yellow.interactions.ui.DialogueBox");
iclass("yellow.type.DisableableWeapon");
iclass("yellow.type.NameableWeapon");
iclass("yellow.ui.buttons.YellowWeaponSwitch");
iclass("yellow.ui.buttons.dialogs.YellowWeaponSwitchDialog");
iclass("yellow.ui.dialogs.DialogueBoxEditorDialog");
iclass("yellow.util.YellowUtils");
iclass("yellow.weapons.YellowWeapons");
iclass("yellow.world.WorldVars");
