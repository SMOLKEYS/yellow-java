package yellow.spec.stage;

import yellow.spec.*;

public class S2 extends StageEntry{

    @Override
    public boolean conditions(){
        return false;
    }

    @Override
    public boolean skip(){
        return Chaos.stageIndex() > 1;
    }

    @Override
    public StageEntry next(){
        return null;
    }
}
