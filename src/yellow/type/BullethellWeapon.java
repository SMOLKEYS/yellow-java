package yellow.type;

import arc.math.*;
import arc.struct.*;
import yellow.entities.units.*;

public class BullethellWeapon extends DisableableWeapon{
    public static final ObjectMap<String, Seq<BullethellWeapon>> groups = new ObjectMap<>();

    public int moveMode;
    public int difficulty;
    public JavaIntRange time = new JavaIntRange(200, 300);

    public BullethellWeapon(String name, String... groupList){
        super(name);

        mountType = BullethellWeaponMount::new;

        for(String s : groupList){
            if(groups.containsKey(s)){
                groups.get(s).add(this);
            }else{
                groups.put(s, new Seq<>());
                groups.get(s).add(this);
            }
        }
    }

    public static Seq<BullethellWeapon> groupByName(String groupName){
        return groups.get(groupName);
    }

    public static class JavaIntRange{
        public int start, end;

        public JavaIntRange(int start, int end){
            this.start = start;
            this.end = end;
        }

        public int random(){
            return Mathf.random(start, end);
        }
    }
}
