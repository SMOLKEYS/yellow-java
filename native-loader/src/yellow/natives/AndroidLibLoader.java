package yellow.natives;

import arc.*;
import arc.files.*;
import arc.util.*;
import mindustry.*;

import java.util.*;

import static arc.util.OS.*;

public class AndroidLibLoader{

    private final Fi sources;

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
    }

    public void load(String libraryName){
        if(Objects.equals(arch, "unknown") || loadedLibraries.contains(libraryName)) return;
        String platformName = LibLoader.mapLibraryName(libraryName);

        Fi lib = sources.child(arch).child(platformName);
        if(!lib.exists()) throw new ArcRuntimeException("Couldn't find Android library '" + platformName + (is64Bit ? "', 64-bit" : "', 32-bit"));

        try{
            lib.copyTo(output);
            System.load(output.child(platformName).toString());
            SharedLibraryLoader.setLoaded(libraryName);
            loadedLibraries.add(libraryName);
        }catch(Throwable e){
            throw new ArcRuntimeException("Couldn't load Android library '" + platformName + (is64Bit ? "', 64-bit" : "', 32-bit"), e);
        }
    }
}
