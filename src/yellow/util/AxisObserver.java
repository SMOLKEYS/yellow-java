package yellow.util;

import arc.*;
import arc.input.*;
import arc.util.*;

public class AxisObserver{

    public void axisDown(KeyBind axis, @Nullable Runnable left, @Nullable Runnable right){
        if(Core.input.axis(axis) < 0 && left != null) left.run();
        if(Core.input.axis(axis) > 0 && right != null) right.run();
    }

    public void axisTap(KeyBind axis, @Nullable Runnable left, @Nullable Runnable right){
        if(Core.input.axisTap(axis) < 0 && left != null) left.run();
        if(Core.input.axisTap(axis) > 0 && right != null) right.run();
    }

}
