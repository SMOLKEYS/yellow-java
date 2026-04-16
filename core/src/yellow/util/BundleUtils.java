package yellow.util;

import arc.*;
import arc.struct.*;
import arc.util.*;
import yellow.*;

public class BundleUtils{

    private static final Seq<String> tmpSeq = new Seq<>(String.class);

    /** Reads through a sequence of bundles ({@code "@bundle-1", "@bundle-2", "@bundle-..."}) until it reaches the end.
     * Returns a new empty string array if the sequence wasn't found. */
    public static String[] getSequence(String head){
        tmpSeq.clear();

        int i = 1;
        while(Core.bundle.has(head + i)){
            int ind = i++;
            tmpSeq.add(Core.bundle.get(head + ind));
        }

        if(tmpSeq.isEmpty()) return new String[0];

        return tmpSeq.toArray();
    }
}
