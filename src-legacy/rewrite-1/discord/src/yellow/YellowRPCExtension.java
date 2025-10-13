package yellow;

import arc.discord.*;
import arc.util.*;
import mindustry.*;
import mindustry.gen.*;

@SuppressWarnings("unused")
public class YellowRPCExtension extends YellowExtension{

    public static DiscordRPC.RichPresence presence = new DiscordRPC.RichPresence();
    public static String[] arrYellow = {
            "This. Is. Yellow.",
            "A new look!",
            "*eating chips*",
            "Operates in real-time!",
            "(Hopefully) NOT broken!",
            "Something (nothing)",
            "Everything (something)",
            "Nothing"
    };
    public static String[] arrEditor = {
            "Editing A Map",
            "Editing A Map?",
            "In The Canvas",
            "Immersed In Map Editing",
            "Not In Editor",
            "Not In Editor!",
            "In Editor (Maybe)",
            "In Editor",
    };
    public static String[] arrPlanet = {
            "Choosing A Planet",
            "Not In Launch Selection",
            "Probably In Launch Selection?",
            "Selecting Planets",
            "Can't Decide On A Planet",
            "In Launch Selection (Maybe)",
            "In Launch Selection"
    };
    public static String[] arrMenu = {
            "Inside The Menu",
            "Outside The Menu",
            "Was Menu",
            "Isn't In Menu",
            "Not In Menu",
            "Playing (Menu)",
            "Doing Nothing",
            "In Menu"
    };
    public static String[] arrSandbox = {
            "Unrestricted",
            "NT AUTHORITY\\SYSTEM",
            "uid=0(root)",
            "Glassbox",
            "Dirtbox",
            "Sand Box",
            "Not Sandbox",
            "Chaos Haver",
            "Sandbox"
    };
    public static String[] arrSurvival = {
            "Untrusted",
            "uid=1(daemon)",
            "Creative",
            "Deathly",
            "Not Survival",
            "Trying Their Best",
            "Survival"
    };
    public static String[] arrPvp = {
            "Against Others",
            "Co-Op",
            "Not PvP",
            "Skill-issuing",
            "Skill-issued",
            "Sweating(?)",
            "No More Geneva",
            "PvP"
    };
    public static String[] arrAttack = {
            "Against A.S",
            "Against A.I",
            "Un-Op",
            "Not Attack",
            "Killin' Cores",
            "Killin' Units",
            "NO MORE GENEVA",
            "Attack"
    };
    public static String[] arrWave = {
            "Level",
            "Wave",
            "Type",
            "Map",
            "Session",
            "Call"
    };
    public static String[] arrPlayers = {
            "Players",
            "Creatures",
            "Apes",
            "Monkehs",
            "Goofy Ahhs"
    };
    public static String[] arrUnknownMaps = {
            "Unknown Map",
            "Knownn't Map",
            "Known Map",
            "Map",
            "You aren't supposed to see this, but here you are."
    };

    public YellowRPCExtension(){

    }

    @Override
    public void clientLoad(){
        presence.startTimestamp = System.currentTimeMillis();

        try{
            DiscordRPC.close();
            Log.info("Vanilla RPC closed.");
            DiscordRPC.connect(1204425960037027900L);
            Log.info("Yellow RPC initialized.");
            send();
            YellowVars.ui.notifrag.showNotification("Yellow RPC backend initialized.");
        }catch(Exception e){
            YellowVars.ui.notifrag.showErrorNotification("Yellow RPC backend failed to initialize.", e);
        }

        Timey.runLoop(YellowRPCExtension::send, 7);
    }

    public static void send(){
        String mapww = Structs.random(arrUnknownMaps), gm = "", gps = "", state = "";

        try{
            if(Vars.state.isGame()){
                mapww = Strings.capitalize(Strings.stripColors(Vars.state.map.name()));

                if(Vars.state.rules.waves) mapww += " | " + Structs.random(arrWave) + " " + Vars.state.wave;

                gm = Vars.state.rules.pvp ? Structs.random(arrPvp) : Vars.state.rules.attackMode ? Structs.random(arrAttack) : Vars.state.rules.infiniteResources ? Structs.random(arrSandbox) : Structs.random(arrSurvival);

                if(Vars.net.active() && Groups.player.size() > 1){
                    gps = " | " + Groups.player.size() + " " + Structs.random(arrPlayers);
                }
            }else{
                if(Vars.ui.editor != null && Vars.ui.editor.isShown()){
                    state = Structs.random(arrEditor);
                }else if(Vars.ui.planet != null && Vars.ui.planet.isShown()){
                    state = Structs.random(arrPlanet);
                }else{
                    state = Structs.random(arrMenu);
                }
            }


            if(Vars.state.isGame()){
                presence.state = gm + gps;
                presence.details = mapww;
            }else{
                presence.state = state;
                presence.details = Structs.random(arrYellow);
            }

            presence.largeImageText = "Version " + Yellow.meta().version + " (" + Strings.stripColors(FirstLoadFragment.processMeta(Yellow.meta())) + ")";
            presence.largeImageKey = "yellow";
            presence.label1 = "Repository (Star!)";
            presence.url1 = "https://github.com/SMOLKEYS/yellow-rewritten";

            DiscordRPC.send(presence);
        }catch(Exception e){
            Log.err(e);
            //YellowVars.ui.notifrag.showErrorNotification("Yellow RPC presence properties failed to set, for some reason.", e);
        }
    }
}
