
import org.jetbrains.kotlin.parsing.parseBoolean
import java.io.OutputStream
import java.security.MessageDigest
import java.util.*

version = "1.0"

val windows = System.getProperty("os.name").lowercase().contains("windows")

val mindustryVersion = "v146"
val entVersion = "v146.0.10"

//project properties (used in androidCopy)
val useBE = project.hasProperty("adb.useBE") && parseBoolean(project.property("adb.useBE").toString())
val quickstart = project.hasProperty("adb.quickstart") && parseBoolean(project.property("adb.quickstart").toString())

fun arc(module: String) = "com.github.Anuken.Arc$module:$mindustryVersion"

fun mindustry(module: String) = "com.github.Anuken.Mindustry$module:$mindustryVersion"

fun kPlugin(module: String) = "org.jetbrains.kotlin.$module:org.jetbrains.kotlin.$module.gradle.plugin:1.9.0"

plugins {
    java
    kotlin("jvm") version "1.9.20"
}

allprojects {
    apply(plugin = "java")

    repositories {
        mavenCentral()
        maven("https://oss.sonatype.org/content/repositories/snapshots/")
        maven("https://oss.sonatype.org/content/repositories/releases/")
        maven("https://raw.githubusercontent.com/Zelaux/MindustryRepo/master/repository")
        maven("https://raw.githubusercontent.com/GlennFolker/EntityAnnoMaven/main")
        maven("https://www.jitpack.io")
    }

    sourceSets {
        main {
            java.srcDirs("src")
        }
        test {
            java.srcDir("test")
        }
    }

    dependencies {
        compileOnly(arc(":arc-core"))
        compileOnly(mindustry(":core"))

        //oh yeah
        //implementation(arc(":box2d"))

        annotationProcessor("com.github.GlennFolker.EntityAnno:downgrader:$entVersion")
    }

    configurations.all {
        resolutionStrategy.eachDependency {
            if(requested.group == "com.github.Anuken.Arc") {
                useVersion(mindustryVersion)
            }
        }

        exclude(group = "org.jetbrains.kotlin", module = "kotlin-stdlib")
    }

    tasks.withType<JavaCompile> {
        sourceCompatibility = "17"
        options.release.set(8)
        options.encoding = "UTF-8"
        options.isIncremental = true
    }

    task("jarAndroid") {
        fun hash(data: ByteArray): String =
                MessageDigest.getInstance("MD5")
                        .digest(data)
                        .let { Base64.getEncoder().encodeToString(it) }
                        .replace('/', '_')

        group = "build"
        description = "Compiles an android-only jar."
        dependsOn("jar")

        doLast {
            val sdkRoot = System.getenv("ANDROID_HOME") ?: System.getenv("ANDROID_SDK_ROOT")

            if(sdkRoot == null || sdkRoot.isEmpty() || !File(sdkRoot).exists()) {
                throw GradleException("""
				No valid Android SDK found. Ensure that ANDROID_HOME is set to your Android SDK directory.
				Note: if the gradle daemon has been started before ANDROID_HOME env variable was defined, it won't be able to read this variable.
				In this case you have to run "./gradlew --stop" and try again.
			""".trimIndent())
            }

            println("Searching for an Android SDK... ")
            val platformRoot = File("$sdkRoot/platforms/").listFiles()?.filter {
                val fi = File(it, "android.jar")
                val valid = fi.exists() && it.name.startsWith("android-")

                if (valid) {
                    print(it)
                    println(" - OK.")
                }
                return@filter valid
            }?.maxByOrNull {
                it.name.substring("android-".length).toIntOrNull() ?: -1
            }

            if (platformRoot == null) {
                throw GradleException("No android.jar found. Ensure that you have an Android platform installed. (platformRoot = $platformRoot)")
            } else {
                println("Using ${platformRoot.absolutePath}")
            }

            //collect dependencies needed to translate java 8 bytecode code to android-compatible bytecode (yeah, android's dvm and art do be sucking)
            val dependencies =
                    (configurations.runtimeClasspath.get().files)
                            .map { it.path }

            val dexRoot = File("${layout.buildDirectory.get()}/dex/").also { it.mkdirs() }
            val dexCacheRoot = dexRoot.resolve("cache").also { it.mkdirs() }

            // read the dex cache map (path-to-hash)
            val dexCacheHashes = dexRoot.resolve("listing.txt")
                    .takeIf { it.exists() }
                    ?.readText()
                    ?.lineSequence()
                    ?.map { it.split(" ") }
                    ?.filter { it.size == 2 }
                    ?.associate { it[0] to it[1] }
                    .orEmpty()
                    .toMutableMap()

            // calculate hashes for all dependencies
            val hashes = dependencies.associateWith {hash(File(it).readBytes())}

            // determime which dependencies can have their cached dex files reused and which can not
            val reusable = ArrayList<String>()
            val needReDex = HashMap<String, String>() // path-to-hash
            hashes.forEach { (path, hash) ->
                if (dexCacheHashes.getOrDefault(path, null) == hash) {
                    reusable += path
                } else {
                    needReDex[path] = hash
                }
            }

            println("${reusable.size} dependencies are already desugared and can be reused.")
            if (needReDex.isNotEmpty()) println("Desugaring ${needReDex.size} dependencies.")

            // for every non-reusable dependency, invoke d8 (d8.bat for windows) and save the new hash

            val d8 = if (windows) "d8.bat" else "d8"

            var index = 1
            needReDex.forEach { (dependency, hash) ->
                println("Processing ${index++}/${needReDex.size} ($dependency)")

                val outputDir = dexCacheRoot.resolve(hash(dependency.toByteArray()).replace("==", "")).also { it.mkdir() }
                exec {
                    errorOutput = object : OutputStream(){
                        override fun write(b: Int) {

                        }
                    }
                    commandLine(
                            d8,
                            "--intermediate",
                            "--classpath", "${platformRoot.absolutePath}/android.jar",
                            "--min-api", "14",
                            "--output", outputDir.absolutePath,
                            dependency
                    )
                }
                println()
                dexCacheHashes[dependency] = hash
            }

            // write the updated hash map to the file
            dexCacheHashes.asSequence()
                    .map { (k, v) -> "$k $v" }
                    .joinToString("\n")
                    .let { dexRoot.resolve("listing.txt").writeText(it) }

            if (needReDex.isNotEmpty()) println("Done.")
            println("Preparing to desugar the project and merge dex files.")

            val dexPathes = dependencies.map {s ->
                dexCacheRoot.resolve(hash(s.toByteArray())).also { it.mkdir() }
            }
            // assemble the list of classpath arguments for project dexing
            val dependenciesStr = Array<String>(dependencies.size * 2) {
                if (it % 2 == 0) "--classpath" else dexPathes[it / 2].absolutePath
            }

            // now, compile the project
            exec {
                val output = dexCacheRoot.resolve("project").also { it.mkdirs() }
                commandLine(
                        d8,
                        *dependenciesStr,
                        "--classpath", "${platformRoot.absolutePath}/android.jar",
                        "--min-api", "14",
                        "--output", "$output",
                        "${layout.buildDirectory.get()}/libs/${project.name}Desktop.jar"
                )
            }

            // finally, merge all dex files
            exec {
                val depDexes = dexPathes
                        .map { it.resolve("classes.dex") }.toTypedArray()
                        .filter { it.exists() } // some are empty
                        .map { it.absolutePath }
                        .toTypedArray()

                commandLine(
                        d8,
                        *depDexes,
                        dexCacheRoot.resolve("project/classes.dex").absolutePath,
                        "--output", "${layout.buildDirectory.get()}/libs/${project.name}Android.jar"
                )
            }
        }
    }

    task<Jar>("deploy") {
        group = "build"
        description = "Compiles a multiplatform jar."
        dependsOn("jarAndroid")

        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        archiveFileName.set("${project.name}.jar")
        from(
                zipTree("${layout.buildDirectory.get()}/libs/${project.name}Desktop.jar"),
                zipTree("${layout.buildDirectory.get()}/libs/${project.name}Android.jar")
        )
        /*

        this causes jarAndroid to fail on a second run.


        doLast {
            delete { delete("${layout.buildDirectory.get()}/libs/${project.name}Desktop.jar") }
            delete { delete("${layout.buildDirectory.get()}/libs/${project.name}Android.jar") }
        }
         */
    }
}

subprojects {
    val supportsAndroid: String? by this
    val supportsAndroidBool = supportsAndroid?.let {parseBoolean(it)} ?: false

    dependencies{
        compileOnly(rootProject)
    }

    tasks.jar {
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        archiveFileName.set(if(supportsAndroidBool) "${project.name}Desktop.jar" else "${project.name}.jar")

        from(*configurations.runtimeClasspath.get().files.map { if (it.isDirectory) it else zipTree(it) }.toTypedArray())

        from("$rootDir/${project.name}") {
            include("ext.hjson")
        }
    }

    tasks.named("jarAndroid") {
        enabled = supportsAndroidBool
    }

    tasks.named("deploy") {
        enabled = supportsAndroidBool
    }
}

tasks.jar {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    archiveFileName.set("${project.name}Desktop.jar")

    from(*configurations.runtimeClasspath.get().files.map { if (it.isDirectory) it else zipTree(it) }.toTypedArray())

    from(rootDir) {
        include("mod.hjson")
        include("icon.png")
    }

    from("$rootDir/assets/") { include("**") }
}

project(":discord") {
    dependencies {
        compileOnly(arc(":discord"))
    }
}

task("copy") {
    group = "copy"
    description = "Compiles a desktop-only jar and copies it to your mindustry data directory."
    dependsOn("jar")

    val dir = if(windows) "${System.getenv("APPDATA")}\\Mindustry\\mods" else "${System.getenv("HOME")}/.local/share/Mindustry/mods"
    val dirC = if(project.hasProperty("copy.target")) project.property("copy.target").toString() else null

    doLast {
        println("Copying mod...")

        val fDir = if(project.hasProperty("copy.target")) dirC else dir

        if(!fDir?.let {File(it).exists()}!!){
            println("WARN: Target copy directory ($fDir) does not exist. Skipping copy operation.")
            if(dirC == null) println("If you use a custom data directory, you may specify '-Pcopy.target=<path-to-mods-dir>'.")
            return@doLast
        }

        copy {
            from("${layout.buildDirectory.get()}/libs")
            into(fDir)
            include("${project.name}Desktop.jar")
        }
    }
}


//TODO support for using different devices when multiple are connected (serial no.)
task("androidCopy") {
    group = "copy"
    description = "Compiles a multiplatform jar and copies it to a connected device using ADB. This requires the device to have USB debugging enabled."
    dependsOn("deploy")

    val adb = if(windows) "adb.exe" else "adb"

    doLast {
        println("Copying mod to connected device...")

        val target = if(useBE){ println("Using BE directory."); "io.anuke.mindustry.be" } else "io.anuke.mindustry"

        exec {
            commandLine = "$adb push ${layout.buildDirectory.get()}/libs/${project.name}.jar /sdcard/Android/data/$target/files/mods".split(' ')
            standardOutput = System.out
            errorOutput = System.err
        }

        //piece of shit.
        exec {
        	commandLine = "$adb shell chmod 755 /sdcard/Android/data/$target/files/mods/${project.name}.jar".split(' ')
        	standardOutput = System.out
        	errorOutput = System.err
        }

        if(quickstart) exec {
            println("Starting Mindustry on connected device...")
            commandLine = "$adb shell am start -n $target/mindustry.android.AndroidLauncher".split(' ')
            standardOutput = System.out
            errorOutput = System.err
        }
    }
}

task<Copy>("buildAll") {
    val taskList = arrayListOf<Task>()
    val dirList = arrayListOf<String>()
    allprojects.forEach {p ->
        val supportsAndroid: String? by p

        taskList.add(p.tasks.getByName("deploy"))
        dirList.add("${p.layout.buildDirectory.get()}/libs/${p.name}.jar")
    }
    dependsOn(taskList)

    doLast {
        copy {
            from(dirList)
            into("${layout.buildDirectory.get()}/libs/")
        }
    }
}

