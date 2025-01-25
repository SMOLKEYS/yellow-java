package yellow.mods;

import arc.*;
import arc.files.*;
import arc.struct.*;
import arc.util.*;
import arc.util.serialization.*;
import java.util.*;
import mindustry.*;
import mindustry.ctype.*;
import mindustry.mod.*;

//TODO subject for rework
public class ExtensionCore{

    public static Seq<LoadedExtension> extensions = new Seq<>();
    public static JsonReader reader = new JsonReader();

    public static ExtensionMeta load(Fi extension) throws Exception{
        //android 14
        if(!extension.file().setReadOnly()) Log.warn("Could not make @ read-only.", extension.name());

        ZipFi f = new ZipFi(extension);
        if(!f.child("ext.hjson").exists()) throw new ExtensionMetaMissingException("Extension file " + extension.nameWithoutExtension() + " has no ext.hjson metadata file.");
        //manually add a bracket ("{}") wrapper (if it doesnt have one) because errors
        String s = f.child("ext.hjson").readString();
        if(!s.startsWith("{")) s = "{\n" + s + "\n}";
        ExtensionMeta meta = metaBuild(reader.parse(s));
        ClassLoader cl = Vars.platform.loadJar(extension, Vars.mods.mainLoader());
        ((ModClassLoader) Vars.mods.mainLoader()).addChild(cl);
        LoadedExtension ext = new LoadedExtension(meta.name, extension, cl, meta.enabled() ? (YellowExtension) cl.loadClass(meta.main).getConstructor().newInstance() : null, meta);
        extensions.add(ext);
        return meta;
    }

    public static LoadedExtension getExtension(String name){
        return extensions.find(e -> Objects.equals(e.name, name));
    }

    private static ExtensionMeta metaBuild(JsonValue root){
        ExtensionMeta m = new ExtensionMeta();
        m.name = root.getString("name");
        m.displayName = root.getString("displayName", null);
        m.author = root.getString("author", null);
        m.description = root.getString("description", null);
        m.version = root.getString("version", null);
        m.main = root.getString("main");
        return m;
    }

    /** Similar to {@link mindustry.mod.Mods.LoadedMod LoadedMod}, see for reference and documentation. ALWAYS a jar.
     * Note that an extension being loaded does not necessarily mean it is enabled. */
    public static class LoadedExtension{
        public final String name;
        public final Fi file;
        public final ExtensionMeta meta;
        public final ClassLoader loader;
        //null if this extension is not enabled.
        public @Nullable YellowExtension main;

        public LoadedExtension(String name, Fi file, ClassLoader loader, YellowExtension main, ExtensionMeta meta){
            this.name = name;
            this.file = file;
            this.loader = loader;
            this.main = main;
            this.meta = meta;
        }
    }

    public static class ExtensionMeta{
        public String name, main;
        public @Nullable String displayName, author, description, version;
        public @Nullable Content brokenContent;

        public boolean enabled(){
            return Core.settings.getBool("extension-" + name + "-enabled", true);
        }

        public void enabled(boolean enabled){
            Core.settings.put("extension-" + name + "-enabled", enabled);
        }
    }

    public static class ErroneousExtension{
        public Exception exception;
        public Fi file;

        public ErroneousExtension(Fi file, Exception exception){
            this.exception = exception;
            this.file = file;
        }
    }

    public static class ExtensionMetaMissingException extends Exception{

        public ExtensionMetaMissingException(){
        }

        public ExtensionMetaMissingException(String message){
            super(message);
        }

        public ExtensionMetaMissingException(String message, Throwable cause){
            super(message, cause);
        }

        public ExtensionMetaMissingException(Throwable cause){
            super(cause);
        }
    }
}
