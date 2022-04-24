global.resetPrepared = () => Core.settings.put("yellow-java-prepared", false);

//setup mod desc
let meta = Vars.mods.getMod("yellow-java").meta;
meta.description = "A random piece of chaos. [scarlet]Android/PC only.";

//import all mod classes
const classes = [
    "yellow.Yellow",
    "yellow.content.YellowUnitTypes",
    "yellow.content.YellowBlocks",
    "yellow.content.YellowBullets",
    "yellow.content.YellowPlanets",
    "yellow.content.YellowFx",
    "yellow.content.YellowAchievements",
    "yellow.content.YellowStatusEffects",
    "yellow.entities.abilities.RespawnAbility",
    "yellow.entities.units.GhostUnitType",
    "yellow.entities.units.DisableableWeaponMount",
    "yellow.entities.units.entity.GhostUnitEntity",
    "yellow.entities.bullet.AirstrikeFlare",
    "yellow.interactions.Responses",
    "yellow.interactions.YellowState",
    "yellow.interactions.ui.ChatBubble",
    "yellow.interactions.ui.DialogueBox",
    "yellow.type.DisableableWeapon",
    "yellow.type.NameableWeapon",
    "yellow.ui.buttons.YellowWeaponSwitch",
    "yellow.ui.buttons.dialogs.YellowWeaponSwitchDialog",
    "yellow.ui.dialogs.DialogueBoxEditorDialog",
    "yellow.util.YellowUtils",
    "yellow.weapons.YellowWeapons",
    "yellow.world.WorldVars"
];

Events.on(ClientLoadEvent, () => {
    classes.forEach(cls => {
        Vars.mods.getScripts().runConsole("importClass(new NativeJavaClass(Vars.mods.getScripts().scope, Class.forName(" + cls + ", true, Vars.mods.mainLoader())));");
    });
});