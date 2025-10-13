package yellow.cutscene;

import arc.func.*;
import arc.math.*;
import arc.math.geom.*;
import arc.util.pooling.*;
import mindustry.gen.*;

public class Controllers{

    /** Returns a new or pooled controller of the specified type. */
    public static <T extends CutsceneController<?>> T controller(Class<T> type, Prov<T> sup){
        T control = Pools.obtain(type, sup);
        control.setPool(Pools.get(type, sup));
        return control;
    }

    /** @return A controller that moves the camera to the target coordinates. */
    public static CameraMoveController moveCameraTo(float x, float y, float time, Interp interp){
        CameraMoveController camera = controller(CameraMoveController.class, CameraMoveController::new);
        camera.x = x;
        camera.y = y;
        camera.time = time;
        camera.interp = interp;
        camera.moveMode = CameraMoveController.MoveMode.to;
        return camera;
    }

    /** @return A controller that moves the camera to the target position. */
    public static CameraMoveController moveCameraTo(Prov<Position> pos, float time, Interp interp){
        CameraMoveController camera = controller(CameraMoveController.class, CameraMoveController::new);
        camera.target = pos;
        camera.time = time;
        camera.interp = interp;
        camera.moveMode = CameraMoveController.MoveMode.to;
        return camera;
    }

    /** @return A controller that moves the camera by the specified amount. */
    public static CameraMoveController moveCameraBy(float x, float y, float time, Interp interp){
        CameraMoveController camera = controller(CameraMoveController.class, CameraMoveController::new);
        camera.x = x;
        camera.y = y;
        camera.time = time;
        camera.interp = interp;
        camera.moveMode = CameraMoveController.MoveMode.by;
        return camera;
    }

    /** @return A controller that changes the camera zoom to the specified distance. */
    public static CameraZoomController changeZoom(float zoom, float time, Interp interp){
        CameraZoomController camera = controller(CameraZoomController.class, CameraZoomController::new);
        camera.zoom = zoom;
        camera.time = time;
        camera.interp = interp;
        return camera;
    }

    /** @return A controller that transfers the player to the specified unit that was focused on by the last controller. */
    public static TransferPlayerController playerTransfer(){
        return controller(TransferPlayerController.class, TransferPlayerController::new);
    }

    /** @return A controller that transfers the player to the specified unit. */
    public static TransferPlayerController playerTransfer(Unit target){
        TransferPlayerController transfer = controller(TransferPlayerController.class, TransferPlayerController::new);
        transfer.target = target;
        return transfer;
    }

    public static ParallelController parallel(CutsceneController<?>... controllers){
        ParallelController p = controller(ParallelController.class, ParallelController::new);
        p.controllers.add(controllers);
        return p;
    }

    public static DelayController delay(float time){
        DelayController delay = controller(DelayController.class, DelayController::new);
        delay.time = time;
        return delay;
    }

    public static RunnableController run(Runnable r){
        RunnableController run = controller(RunnableController.class, RunnableController::new);
        run.run = r;
        return run;
    }

    public static ConsController cons(Cons<Object> c){
        ConsController cons = controller(ConsController.class, ConsController::new);
        cons.cons = c;
        return cons;
    }

    public static CurtainController resizeCurtains(float span, float time, Interp interp, boolean parallelize){
        CurtainController curtain = controller(CurtainController.class, CurtainController::new);
        curtain.span = span;
        curtain.time = time;
        curtain.interp = interp;
        curtain.parallelize = parallelize;
        return curtain;
    }

    public static CurtainController resetCurtains(float time, Interp interp, boolean parallelize){
        return resizeCurtains(0.22f, time, interp, parallelize);
    }

    public static CurtainController hideCurtains(float time, Interp interp, boolean parallelize){
        return resizeCurtains(0f, time, interp, parallelize);
    }

    public static CurtainController coverScreen(float time, Interp interp, boolean parallelize){
        return resizeCurtains(1f, time, interp, parallelize);
    }

    public static CurtainController resetCurtains(boolean parallelize){
        return resizeCurtains(0.22f, 60f, Interp.smooth, parallelize);
    }

    public static CurtainController hideCurtains(boolean parallelize){
        return resizeCurtains(0f, 60f, Interp.smooth, parallelize);
    }

    public static CurtainController coverScreen(boolean parallelize){
        return resizeCurtains(1f, 60f, Interp.smooth, parallelize);
    }
}
