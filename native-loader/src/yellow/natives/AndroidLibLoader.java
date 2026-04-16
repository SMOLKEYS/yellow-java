package yellow.natives;

import arc.*;
import arc.files.*;
import arc.util.*;
import mindustry.*;

import java.io.*;
import java.util.*;

import static arc.util.OS.*;

public class AndroidLibLoader{

    private final Fi sources;
    private final Class<?> sourcesClass;
    private final LoadPoint load;

    private static final Fi output = Core.files.cache("modlibext");
    private static final HashSet<String> loadedLibraries = new HashSet<>();
    private static String arch;

    static{
        if(Vars.mobile){
            if(!output.exists()){
                output.mkdirs();
            }else if(!output.isDirectory()){
                output.delete();
                output.mkdirs();
            }else{
                output.emptyDirectory();
            }

            //dogshit
            try{
                arch = OS.exec("getprop", "ro.product.cpu.abi");
            }catch(Exception e){
                arch = "unknown";
            }
        }
    }

    public AndroidLibLoader(Fi sources){
        this.sources = sources instanceof ZipFi ? sources : new ZipFi(sources);
        this.sourcesClass = null;
        this.load = LoadPoint.ARCHIVE;
    }

    public AndroidLibLoader(Class<?> sourcesClass){
        this.sources = null;
        this.sourcesClass = sourcesClass;
        this.load = LoadPoint.RESOURCE;
    }

    public void load(String libraryName){
        if(Objects.equals(arch, "unknown") || loadedLibraries.contains(libraryName)) return;
        String platformName = LibLoader.mapLibraryName(libraryName);

        if(load == LoadPoint.ARCHIVE && sources != null){
            Fi lib = sources.child(arch).child(platformName);
            if(!lib.exists())
                throw new ArcRuntimeException("Couldn't find Android library '" + platformName + (is64Bit ? "', 64-bit" : "', 32-bit"));

            try{
                lib.copyTo(output);
                System.load(output.child(platformName).toString());
                SharedLibraryLoader.setLoaded(libraryName);
                loadedLibraries.add(libraryName);
            }catch(Throwable e){
                throw new ArcRuntimeException("Couldn't load Android library '" + platformName + (is64Bit ? "', 64-bit" : "', 32-bit"), e);
            }
        }else if(load == LoadPoint.RESOURCE && sourcesClass != null){
            InputStream stream = sourcesClass.getResourceAsStream("/" + arch + "/" + platformName);
            if(stream == null)
                throw new ArcRuntimeException(("Couldn't find Android library '" + platformName + (is64Bit ? "', 64-bit" : "', 32-bit")) + ", class " + sourcesClass.getCanonicalName());

            Fi out = output.child(System.currentTimeMillis() + "-" + platformName);
            out.write(stream, false);

            try{
                System.load(out.toString());
                SharedLibraryLoader.setLoaded(libraryName);
                loadedLibraries.add(libraryName);
            }catch(Throwable e){
                throw new ArcRuntimeException("Couldn't load Android library '" + platformName + (is64Bit ? "', 64-bit" : "', 32-bit"), e);
            }
        }
    }

    private enum LoadPoint{
        RESOURCE, ARCHIVE
    }
}
