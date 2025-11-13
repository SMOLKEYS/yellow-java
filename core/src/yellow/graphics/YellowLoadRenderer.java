package yellow.graphics;

import arc.files.*;
import arc.func.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.scene.ui.layout.*;
import arc.struct.*;
import arc.util.*;
import mindustry.*;
import mindustry.core.*;
import mindustry.graphics.*;
import yellow.*;
import yellow.util.variable.*;

import java.util.*;

import static arc.Core.*;
import static yellow.Yellow.*;

public class YellowLoadRenderer extends LoadRenderer{

    public final SettingBoundVariable<Boolean> enabled = new SettingBoundVariable<>("yellow-enable-load-renderer", false, true);

    private final Color color = new Color(Pal.accent).lerp(Color.black, 0.5f);
    private final Color colorRed = Pal.breakInvalid.cpy().lerp(Color.black, 0.3f);
    private final String red = "[#" + colorRed + "]";
    private final String orange = "[#" + color + "]";
    private final StringBuilder assetText = new StringBuilder();
    private int lastLength;
    private final ObjectMap<String, TextureRegion> sprites = new ObjectMap<>();

    public YellowLoadRenderer(){
        super();

        sprites.put("yellow", makeOutline(new TextureRegion(new Texture(sprite("yellow")))));
    }

    @SuppressWarnings("SameParameterValue")
    private Fi sprite(String name){
        return mod().root.child("sprites").findAll(e -> Objects.equals(e.nameWithoutExtension(), name)).first();
    }

    @Override
    public void draw(){
        graphics.clear(Color.black);
        Draw.proj().setOrtho(0, 0, graphics.getWidth(), graphics.getHeight());

        if(assets.getLoadedAssets() != lastLength){
            assetText.setLength(0);
            for(String name : assets.getAssetNames()){
                boolean isRed = name.toLowerCase().contains("mod") || assets.getAssetType(name).getSimpleName().toLowerCase().contains("mod") || name.contains("preview");
                assetText
                        .append(isRed ? red : orange)
                        .append(name.replace(OS.username, "<<host>>").replace("/", "::")).append(red).append("::[]")
                        .append(assets.getAssetType(name).getSimpleName()).append("\n");
            }

            lastLength = assets.getLoadedAssets();
        }

        float w = graphics.getWidth(), h = graphics.getHeight(), s = Scl.scl(), progress = assets.getProgress(), midw = w / 2, midh = h / 2;
        float ofs = 85f, ofsH = 20f;

        // note: in-code placement order MATTERS

        Color c = Tmp.c1.set(color).a(1f);
        float rad = Math.max(w, h) * 0.6f;

        if(progress(0.4f)) Fill.light(0, 0, 20, rad, c, Color.clear);
        if(progress(0.63f)) Fill.light(w, 0, 20, rad, c, Color.clear);
        if(progress(0.77f)) Fill.light(0, h, 20, rad, c, Color.clear);
        if(progress(0.8f)) Fill.light(w, h, 20, rad, c, Color.clear);

        fontDraw(f -> {
            f.draw("[darkgray]===== MINDUSTRY CONSOLE =====[]\n" + assetText, 20*s, h - (20*s), Align.left);
        });

        Draw.color(Color.gray);
        Lines.stroke(15f*s);
        Lines.line(0, 0, w, h);
        Lines.stroke(10f*s);
        Lines.line(0, 0, Pal.remove, progress * w, progress * h, Pal.accent);

        Draw.color(Color.white);
        Draw.rect(sprites.get("yellow"), midw, midh, 120*s, 120*s);

        fontDraw(f -> {
            f.draw( Strings.autoFixed(progress*100f, debug ? 6 : 2) + "%", midw, midh - (ofs*s), Align.center);
            f.draw("< " + (assets.getCurrentLoading() != null ? assets.getCurrentLoading().fileName.toLowerCase() : "system") + " >", midw, midh - ((ofs+20)*s), Align.center);
            f.draw(Vars.mods.orderedMods().size + " mods loaded", midw, midh - ((ofs+40)*s), Align.center);
            f.draw(assets.getQueuedAssets() + " assets queued, " + assets.getLoadedAssets() + " assets loaded", midw, midh - ((ofs+60)*s), Align.center);

            if(Yellow.debug){
                f.draw(Vars.content.blocks().size + " Blocks", midw, midh - ((ofs + 80) * s), Align.center);
                f.draw(Vars.content.units().size + " Units", midw, midh - ((ofs + 100) * s), Align.center);
                f.draw(Vars.content.items().size + " Items", midw, midh - ((ofs + 120) * s), Align.center);
                f.draw(Vars.content.liquids().size + " Liquids", midw, midh - ((ofs + 140) * s), Align.center);
                f.draw(Vars.content.bullets().size + " Bullets", midw, midh - ((ofs + 160) * s), Align.center);
                f.draw(Vars.content.sectors().size + " Sectors", midw, midh - ((ofs + 180) * s), Align.center);
                f.draw(Vars.content.units().size + " Units", midw, midh - ((ofs + 200) * s), Align.center);
                f.draw(Vars.content.planets().size + " Planets", midw, midh - ((ofs + 220) * s), Align.center);
                f.draw(Vars.content.weathers().size + " Weathers", midw, midh - ((ofs + 240) * s), Align.center);
                f.draw(Vars.content.unitStances().size + " Unit Stances", midw, midh - ((ofs + 260) * s), Align.center);
                f.draw(Vars.content.unitCommands().size + " Unit Commands", midw, midh - ((ofs + 280) * s), Align.center);
            }

            f.draw("Yellow v" + mod().meta.version + (debug ? "\n[orange]Debug mode[]" : ""), w - (20f*s), 30f*s, Align.right);

            f.draw(graphics.getFramesPerSecond() + " FPS", w - (20f*s), h - ((ofsH)*s), Align.right);
            f.draw(Version.combined() + " (" + Version.number + ")", w - (20f*s), h - ((ofsH+20)*s), Align.right);
        });

        Draw.flush();
    }

    private boolean progress(float target){
        return assets.getProgress() >= target;
    }

    private void fontDraw(Cons<Font> ft){
        fontDraw("outline", ft);
    }

    private void fontDraw(String name, Cons<Font> ft){
        if(assets.isLoaded(name)){
            Font f = assets.get(name);
            f.getData().markupEnabled = true;

            ft.get(f);
        }
    }

    private TextureRegion makeOutline(TextureRegion region){
        return new TextureRegion(new Texture(region.texture.getTextureData().getPixmap().outline(Pal.darkerMetal, 3)));
    }
}
