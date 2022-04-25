global.resetPrepared = () => Core.settings.put("yellow-java-prepared", false);

//setup mod desc
let meta = Vars.mods.getMod("yellow-java").meta;
meta.description = "A random piece of chaos. [scarlet]Android/PC only.";
