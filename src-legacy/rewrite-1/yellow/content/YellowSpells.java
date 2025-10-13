package yellow.content;

public class YellowSpells{

    public static Spell missileInverter, leftStrafe;

    public static void load(){
        missileInverter = new MissileReflectSpell("missile-inverter"){{
            cooldown = 60*3.5f;
            manaCost = 1260;
            radius = 8*20f;
        }};

        leftStrafe = new StrafeSpell("left-strafe"){{
            cooldown = 45f;
            manaCost = 100;
            strafeAngle = -90f;
            strafeSpeed = 3f;
            angleRnd = 7f;
        }};
    }
}
