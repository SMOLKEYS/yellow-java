package yellow.combat;

import arc.*;
import arc.input.*;
import arc.math.*;
import arc.math.geom.*;
import arc.util.*;
import mindustry.game.*;
import mindustry.gen.*;
import mindustry.input.*;
import yellow.util.*;

import static arc.Core.*;
import static mindustry.Vars.*;

/** A special ULTRAKILL-inspired input system. */
public class CombatInput extends DesktopInput{
    private InputHandler lastInput;

    public boolean canManipulateEnvironment = false;
    public final KeyBind swapKey;

    public CombatInput(){
        super();
        swapKey = YellowKeyBind.add("swap_mode", KeyCode.h, "combat");
        lastInput = control.input;
    }

    @Override
    public void update(){
        updateMovementKeys();
    }

    @Override
    public boolean isPlacing(){
        return super.isPlacing() && canManipulateEnvironment;
    }

    @Override
    public boolean isBreaking(){
        return super.isBreaking() && canManipulateEnvironment;
    }

    public boolean isActive(){
        return control.input instanceof CombatInput;
    }

    //updates no matter what input is active
    public void updateSwapListener(){
        if(input.keyTap(swapKey)){
            InputHandler h = control.input;
            control.input = lastInput == null ? this : isActive() ? lastInput : this;
            lastInput = h;
        }
    }

    public void updateMovementKeys(){

        //region code needed for basic movement and some ui, from DesktopInput

        if(net.active() && Core.input.keyTap(Binding.playerList) && (scene.getKeyboardFocus() == null || scene.getKeyboardFocus().isDescendantOf(ui.listfrag.content) || scene.getKeyboardFocus().isDescendantOf(ui.minimapfrag.elem))){
            ui.listfrag.toggle();
        }

        boolean locked = locked();
        boolean panCam = false;
        float camSpeed = (!Core.input.keyDown(Binding.boost) ? panSpeed : panBoostSpeed) * Time.delta;
        boolean detached = settings.getBool("detach-camera", false);

        if(!scene.hasField() && !scene.hasDialog()){
            if(input.keyTap(Binding.detachCamera)){
                settings.put("detach-camera", detached = !detached);
                if(!detached){
                    panning = false;
                }
                spectating = null;
            }

            if(input.keyDown(Binding.pan)){
                panCam = true;
                panning = true;
                spectating = null;
            }

            if((Math.abs(Core.input.axis(Binding.moveX)) > 0 || Math.abs(Core.input.axis(Binding.moveY)) > 0 || input.keyDown(Binding.mouseMove))){
                panning = false;
                spectating = null;
            }
        }

        panning |= detached;


        if(!locked){
            if(((player.dead() || state.isPaused() || detached) && !ui.chatfrag.shown()) && !scene.hasField() && !scene.hasDialog()){
                if(input.keyDown(Binding.mouseMove)){
                    panCam = true;
                }

                Core.camera.position.add(Tmp.v1.setZero().add(Core.input.axis(Binding.moveX), Core.input.axis(Binding.moveY)).nor().scl(camSpeed));
            }else if((!player.dead() || spectating != null) && !panning){
                //TODO do not pan
                Team corePanTeam = state.won ? state.rules.waveTeam : player.team();
                Position coreTarget = state.gameOver && !state.rules.pvp && corePanTeam.data().lastCore != null ? corePanTeam.data().lastCore : null;
                Position panTarget = coreTarget != null ? coreTarget : spectating != null ? spectating : player;

                Core.camera.position.lerpDelta(panTarget, Core.settings.getBool("smoothcamera") ? 0.08f : 1f);
            }

            if(panCam){
                Core.camera.position.x += Mathf.clamp((Core.input.mouseX() - Core.graphics.getWidth() / 2f) * panScale, -1, 1) * camSpeed;
                Core.camera.position.y += Mathf.clamp((Core.input.mouseY() - Core.graphics.getHeight() / 2f) * panScale, -1, 1) * camSpeed;
            }
        }

        if(!player.dead() && !state.isPaused() && !scene.hasField() && !locked){
            updateMovement(player.unit());

            if(Core.input.keyTap(Binding.respawn)){
                controlledType = null;
                recentRespawnTimer = 1f;
                Call.unitClear(player);
            }
        }

        //endregion

    }
}
