package yellow.internal

import mindustry.*
import rhino.*
import yellow.internal.util.YellowUtils.controlledLog

//sh1p you have done it
object YellowClassGateway{

    private var uniGateImports = 0
    private var uniGateErrors = 0

    @JvmStatic
    fun load(){
        val scope = Vars.mods.scripts.scope as ImporterTopLevel
        
        val packages = Vars.tree.get("classpaths/yellow-classpath.txt").readString().split('\n')

        controlledLog("[yellow]--------STARTING GATEWAY--------[]")
        controlledLog("Scope: $scope, Generator: $this, Class Loader: ${Vars.mods.mainLoader()}")
        packages.forEach{
            val p = NativeJavaPackage(it, Vars.mods.mainLoader())
            controlledLog("importing classes from $it...")
            p.parentScope = scope
            scope.importPackage(p)
        }
        controlledLog("[green]--------GATEWAY STARTED!--------[]")
    }
    
    /*
    
    TODO crashes due to the creation of a SingletonList somewhere
    
    fun loadUniversal(){
        var scope = Vars.mods.scripts.scope as ImporterTopLevel
        
        val source = Core.settings.dataDirectory.child("yellow").child("universal-classpath.txt")
        val modListSource = Core.settings.dataDirectory.child("yellow").child("mod-lists.txt")
        
        if(!source.exists()) source.writeString("put.mod.paths.or.game.paths.here")
        if(!modListSource.exists()) modListSource.writeString("")
        
        val packages = source.readString().split('\n')
        val prep = modListSource.readString().split('\n')
        val modPaths = ObjectMap<String, String>()
        
        prep.forEach{
            val asu = it.split(",")
            modPaths.put(asu[0], asu[1])
        }
        
        controlledLog("[yellow]--------STARTING UNIVERSAL GATEWAY--------[]")
        packages.forEach{
            val p = NativeJavaPackage(it, Vars.mods.mainLoader())
            controlledLog("importing classes from $it...")
            p.parentScope = scope
            scope.importPackage(p)
            uniGateImports++
        }
        
        modPaths.each{k: String, v: String -> 
            val dump = Seq<String>()
            try{
                YellowUtilsKt.traverse(ZipFi(Core.settings.dataDirectory.child("mods").child(k)).child(v), dump)
                
                dump.each{
                    val p = NativeJavaPackage(it, Vars.mods.mainLoader())
                    controlledLog("importing classes from $it...")
                    p.parentScope = scope
                    scope.importPackage(p)
                    uniGateImports++
                }
                
                val p = NativeJavaPackage(v, Vars.mods.mainLoader())
                controlledLog("importing classes from $v...")
                p.parentScope = scope
                scope.importPackage(p)
                uniGateImports++
                
            }catch(e: Exception){
                Log.err("Failed to handle importing:", e)
                uniGateErrors++
            }
        }
        controlledLog("[green]--------UNIVERSAL GATEWAY STARTED!--------[]")
        Log.info("Total universal gateway imports: $uniGateImports, Total universal gateway import errors: $uniGateErrors")
    }
    */
}
