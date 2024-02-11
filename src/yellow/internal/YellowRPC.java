package yellow.internal;

import arc.discord.*;

public class YellowRPC{

    static final DiscordRPC.RichPresence pres = new DiscordRPC.RichPresence();

    public static void send(){
        try{
            pres.details = RichPresenceChaos.getRpc().random().replace("\r", "");
            pres.state = "yellow invades!";
            pres.label1 = "check me out! very coolio";
            pres.url1 = "https://github.com/SMOLKEYS/yellow-java";
            pres.label2 = "(...is she?)";
            pres.url2 = "https://github.com/SMOLKEYS";
            pres.largeImageKey = "yellow";

            DiscordRPC.send(pres);
        }catch(Exception e){
            //shut.
        }
    }
}
