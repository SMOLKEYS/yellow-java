package yellow.math;

import arc.math.*;
import arc.math.geom.*;

@SuppressWarnings({"unused"})
public class Mathy{

    public static float dstPos(Position a, Position b){
        return Mathf.dst(a.getX(), a.getY(), b.getX(), b.getY());
    }

    public static float dstPos(float x, float y, Position other){
        return Mathf.dst(x, y, other.getX(), other.getY());
    }

    public static float dstPos(Position other, float x, float y){
        return Mathf.dst(other.getX(), other.getY(), x, y);
    }

    public static float lerp(float fromValue, float toValue, float progress, float peak){
        return Mathf.lerp(fromValue, toValue, progress / peak);
    }

    public static float lerp(float fromValue, float toValue, float progress, float start, float peak){
        return Mathf.lerp(fromValue, toValue, Math.max(start, progress) / peak);
    }

    public static float lerpc(float fromValue, float toValue, float progress, float peak){
        return Mathf.lerp(fromValue, toValue, Mathf.clamp(progress / peak));
    }

    public static float lerpc(float fromValue, float toValue, float progress, float start, float peak){
        return Mathf.lerp(fromValue, toValue, Mathf.clamp(Math.max(start, progress) / peak));
    }
}
