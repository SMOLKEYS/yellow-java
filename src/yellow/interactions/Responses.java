package yellow.interactions;

import arc.struct.*;

public class Responses{
    
    public static ObjectMap<String, Runnable> scriptedResponses = new ObjectMap<>();
    
    public static String[] responses = {
        "cope",
        "ratio",
        "L",
        "get real",
        "sus",
        "amogus",
        "get in the vent",
        "fatherless",
        "imagine using routerchains",
        "smh",
        "shaking my head",
        "smh my head",
        "shaking my head my head",
        "noob",
        "start using intervection pfps",
        "why dont you get some octs",
        "fatherful",
        "smoothbrained",
        "touch grass",
        "stop touching grass",
        "mindustry is a racial conflict game",
        "damn daniel",
        "damn intervection",
        "damn cheesy",
        "damn smolkeys",
        "damn dryehm",
        "damn anuke",
        "damn ilya",
        "rule 4",
        "no cryo",
        "no cryo?",
        "you have been warned too many times",
        "/votekick is an among us reference"
    };
    
    public static String[][] secrets = {
        {"UWOOOOOOGH", "SEEEEEEGS"}
    };
    //testing
    public static String[] screams = {
        "a",
        "aaaaa",
        "aaaaaaaaaa",
        "aaaaaaaaaaaaaaaaaaaa",
        "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
        "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
        "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
    };
    
    public static void put(Object... objects){
        scriptedResponses.of(objects);
    }
}
