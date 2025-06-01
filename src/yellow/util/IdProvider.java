package yellow.util;

import arc.struct.*;

public class IdProvider{
    private static final ObjectMap<String, Counter> map = new ObjectMap<>();

    public static int nextId(String name){
        return map.containsKey(name) ? map.get(name).nextId() : map.put(name, new Counter()).nextId();
    }

    private static class Counter{
        public int counter = 0;

        public int nextId(){
            if(counter >= Integer.MAX_VALUE - 2) counter = 0;
            return counter++;
        }
    }
}
