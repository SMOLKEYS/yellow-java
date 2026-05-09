package yellow.graphics;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.graphics.gl.*;
import mindustry.game.EventType.*;
import mindustry.graphics.*;

public class YellowGraphics{

    public static FrameBuffer drawBuffer;

    public static void init(){
        drawBuffer = new FrameBuffer(Core.graphics.getWidth(), Core.graphics.getHeight());

        Events.run(Trigger.draw, () -> {
            Draw.drawRange(Layer.flyingUnit + 3f, () -> drawBuffer.begin(Color.clear), () -> {
                drawBuffer.blit(Shaders.water);
                drawBuffer.end();
            });
        });
    }
}
