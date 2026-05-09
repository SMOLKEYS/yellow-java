package yellow.world.blocks.storage;

import arc.audio.*;
import mindustry.world.blocks.storage.*;
import yellow.content.*;

public class SanctumCore extends CoreBlock{
    public SanctumCore(String name){
        super(name);
        unitType = YellowUnitTypes.yellow;
    }

    public class SanctumCoreBuild extends CoreBuild{

        @Override
        public float launchDuration(){
            return super.launchDuration();
        }

        @Override
        public Music landMusic(){
            return new Music();
        }

        @Override
        public void beginLaunch(boolean launching){
            super.beginLaunch(launching);
        }

        @Override
        public void endLaunch(){
            super.endLaunch();
        }

        @Override
        public void drawLaunch(){
            super.drawLaunch();
        }

        @Override
        public void drawLanding(float x, float y){
            super.drawLanding(x, y);
        }

        @Override
        protected void drawLandingThrusters(float x, float y, float rotation, float frame){
            super.drawLandingThrusters(x, y, rotation, frame);
        }

        @Override
        public void drawThrusters(float frame){
            super.drawThrusters(frame);
        }

        @Override
        public float zoomLaunch(){
            return super.zoomLaunch();
        }

        @Override
        public void updateLaunch(){
            super.updateLaunch();
        }
    }
}
