package yellow.util;

import arc.struct.*;

public class IdProvider{
    private static final ObjectMap<String, Counter> map = new ObjectMap<>();

    public static int nextId(String name){
        if(map.containsKey(name)){
            return map.get(name).nextId();
        }
        Counter c = new Counter();
        map.put(name, c);
        return c.nextId();
    }

    private static class Counter{
        public int counter = 0;

        public int nextId(){
            if(counter >= Integer.MAX_VALUE - 2) counter = 0;
            return counter++;
        }
    }
}
