package yellow.content;

import yellow.world.meta.*;
import yellow.world.meta.character.*;

import static yellow.world.meta.character.AffectionCharacter.*;

@SuppressWarnings("SpellCheckingInspection")
public class YellowCharacters{

    //TODO
    public static GameCharacter
    zen, enzie, alice, zamia, //scf + sharded alliance
    nihara, ophelia, orson, rose, mykka, umi, ami, vixon, //azura kingdom (incomplete)
    noel, omar, zemil, tristan; //eshrimel kingdom (incomplete)

    //TODO
    public static RelationshipRank[] basicRanks = {
            new RelationshipRank("acquaintance", 0),
            new RelationshipRank("companion", 1000),
            new RelationshipRank("friend", 1850),
            new RelationshipRank("close-friend-1", 3690),
            new RelationshipRank("close-friend-2", 5540),
            new RelationshipRank("close-friend-3", 8990),
            new RelationshipRank("very-close", 11000),
            new RelationshipRank("more-than-a-friend", 13500),
            new RelationshipRank("lover", 15000),
            new RelationshipRank("lover-2", 18000),
            new RelationshipRank("lover-3", 22000),
            new RelationshipRank("lover-4", 25000),
            new RelationshipRank("certified-lover", 30000),
            new RelationshipRank("love-master", 50000),
            //i applaud you and deem you insane
            new RelationshipRank("thats-enough-my-friend", 100000)
    };

    public static void load(){
        //region scf + sharded alliance
        zen = new GameCharacter("zen");

        enzie = new AffectionCharacter("enzie"){{
            finalAffinity = true;
            startingAffinity = 7395;
            relationshipRanks = basicRanks;
        }};

        alice = new GameCharacter("alice");

        zamia = new GameCharacter("zamia");

        //region azura
        nihara = new AffectionCharacter("nihara"){{
            relationshipRanks = basicRanks;
        }};

        ophelia = new GameCharacter("ophelia");

        orson = new GameCharacter("orson");

        rose = new GameCharacter("rose");

        mykka = new GameCharacter("mykka");

        umi = new GameCharacter("umi");

        ami = new GameCharacter("ami");

        vixon = new GameCharacter("vixon");

        //region eshrimel
        noel = new GameCharacter("noel");

        omar = new GameCharacter("omar");

        zemil = new GameCharacter("zemil");

        tristan = new GameCharacter("tristan");
    }
}
