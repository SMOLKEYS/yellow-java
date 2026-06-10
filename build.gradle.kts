//could you SHUT UP ALREADY
@file:Suppress("unused")

import arc.files.Fi
import arc.util.Log
import arc.util.OS
import arc.util.serialization.Jval
import ent.EntityAnnoExtension
import java.io.BufferedWriter
import java.text.SimpleDateFormat
import java.util.*

val arcVersion: String by project
val mindustryVersion: String by project
val entVersion: String by project

val modFetch: String by project
val modGenSrc: String by project
val modGen: String by project

val androidSdkVersion: String by project
val androidBuildVersion: String by project
val androidMinVersion: String by project

val useBE = project.hasProperty("adb.useBE") && project.property("adb.useBE").toString().toBoolean()
val quickstart = project.hasProperty("adb.quickstart") && project.property("adb.quickstart").toString().toBoolean()

fun arc(module: String, arcv: String = arcVersion) = "com.github.Anuken.Arc$module:$arcv"

fun mindustry(module: String, mindv: String = mindustryVersion) = "com.github.Anuken.Mindustry$module:$mindv"

fun entity(module: String, entv: String = entVersion) = "com.github.GglLfr.EntityAnno$module:$entv"

fun projParam(name: String, default: String = "") = projParamOrNull(name, default)

fun projParamOrNull(name: String, default: String? = null) = (if(project.hasProperty(name)) project.property(name) else default) as String?

fun RepositoryHandler.ivy(url: String = "https://github.com", patternLayout: String): IvyArtifactRepository = this.ivy {
    setUrl(url)
    patternLayout {
        artifact(patternLayout)
    }
    metadataSources{
        artifact()
    }
}

buildscript{
    repositories{
        gradlePluginPortal()
        mavenLocal()
        mavenCentral()
        maven("https://oss.sonatype.org/content/repositories/snapshots/")
        maven("https://oss.sonatype.org/content/repositories/releases/")
        maven("https://maven.xpdustry.com/mindustry")
    }
}

plugins {
    java
    id("com.github.GglLfr.EntityAnno") apply false
}

allprojects {
    apply(plugin = "java")
    sourceSets["main"].java.setSrcDirs(listOf(layout.projectDirectory.dir("src")))

    configurations.configureEach{
        resolutionStrategy.eachDependency{
            if(requested.group == "com.github.Anuken.Arc"){
                useVersion(arcVersion)
            }
        }
    }

    buildscript {
        val arcVersion: String by project

        dependencies{
            classpath("com.github.Anuken.Arc:arc-core:$arcVersion")
        }

        repositories{
            gradlePluginPortal()
            mavenCentral()
            maven("https://maven.xpdustry.com/mindustry")
            maven("https://raw.githubusercontent.com/Zelaux/MindustryRepo/master/repository")
        }
    }

    dependencies {
        annotationProcessor(entity(":downgrader"))

        compileOnly(mindustry(":core"))
        compileOnly(arc(":arc-core"))
    }

    repositories{
        // Necessary Maven repositories to pull dependencies from.
        mavenCentral()
        maven("https://oss.sonatype.org/content/repositories/snapshots/")
        maven("https://oss.sonatype.org/content/repositories/releases/")
        maven("https://raw.githubusercontent.com/GglLfr/EntityAnnoMaven/main")
        maven("https://maven.xpdustry.com/mindustry")
        maven("https://raw.githubusercontent.com/Zelaux/MindustryRepo/master/repository")
        maven("https://jitpack.io")
    }

    tasks.withType<JavaCompile>().configureEach{
        // Use Java 17+ syntax, but target Java 8 bytecode version.
        sourceCompatibility = "17"
        options.apply{
            release = 8
            compilerArgs.add("-Xlint:-options")
            //compilerArgs.add("-Xlint:unchecked")

            isIncremental = true
            encoding = "UTF-8"
        }
    }
}


project(":core"){
    apply(plugin = "com.github.GglLfr.EntityAnno")

    configure<EntityAnnoExtension> {
        modName = project.properties["modName"].toString()
        mindustryVersion = project.properties["mindustryVersion"].toString()
        revisionDir = layout.projectDirectory.dir("revisions").asFile
        fetchPackage = modFetch
        genSrcPackage = modGenSrc
        genPackage = modGen
    }

    dependencies {
        //core components
        compileOnly(entity(":entity"))
        add("kapt", entity(":entity"))
        compileOnly(arc(":discord"))

        //experimental
        implementation(project(":native-loader"))
        //implementation(arc(":box2d"))
        //implementation(arc(":natives-box2d-android"))
        //implementation(arc(":natives-box2d-desktop"))

        //mod integrations
    }

    val jar = tasks.named<Jar>("jar"){
        archiveFileName = "${project.name}Desktop.jar"

        val meta = layout.projectDirectory.file("$temporaryDir/mod.json")
        from(
            files(sourceSets["main"].output.classesDirs),
            files(sourceSets["main"].output.resourcesDir),
            configurations.runtimeClasspath.map{conf -> conf.map{if(it.isDirectory) it else zipTree(it)}},
            files(layout.projectDirectory.dir("assets")),
            layout.projectDirectory.file("icon.png"),
            meta
        )

        metaInf.from(layout.projectDirectory.file("LICENSE"))
        doFirst{
            // Deliberately check if the mod meta is actually written in HJSON, since, well, some people actually use
            // it. But this is also not mentioned in the `README.md`, for the mischievous reason of driving beginners
            // into using JSON instead.
            val metaJson = layout.projectDirectory.file("mod.json")
            val metaHjson = layout.projectDirectory.file("mod.hjson")

            if(metaJson.asFile.exists() && metaHjson.asFile.exists()){
                throw IllegalStateException("Ambiguous mod meta: both `mod.json` and `mod.hjson` exist.")
            }else if(!metaJson.asFile.exists() && !metaHjson.asFile.exists()){
                throw IllegalStateException("Missing mod meta: neither `mod.json` nor `mod.hjson` exist.")
            }

            val isJson = metaJson.asFile.exists()
            val map = (if(isJson) metaJson else metaHjson).asFile
                .reader(Charsets.UTF_8)
                .use{
                    fun Jval.appendString(value: String, append: String): Jval{
                        val prm = getString(value)
                        return put(value, "$prm$append")
                    }

                    fun String.abbreviate(char: Char = ' '): String{
                        val sb = StringBuilder()
                        this.split(char).forEach { st ->
                            sb.append(st[0])
                        }
                        return sb.toString()
                    }

                    val jv = Jval.read(it)

                    val append = projParamOrNull("rapid.append-version")
                    val releaseType = projParamOrNull("release.type")

                    val simpleDateFormat = SimpleDateFormat("dd-MM-yyyy_hh-mm-ss")
                    val currentDate = Date()
                    val localTz = TimeZone.getDefault()?.displayName ?: "Unidentifiable User Location"

                    val dateIdentifier = "${simpleDateFormat.format(currentDate)}_${localTz.abbreviate()}"

                    if(append != null){
                        jv.appendString("version", "-rapid-$append-$dateIdentifier")
                        jv.appendString("displayName", " RDev Build")
                        jv.appendString("description", "\n\n[RAPID DEVELOPMENT BUILD]")
                    } else when(releaseType){
                        null -> jv.appendString("version", "-dev-$dateIdentifier")
                        "release" -> {
                            val releaseGroup = jv.getString("group")
                            val releaseBuild = jv.getInt("build", 0)

                            if(releaseGroup == null) throw IllegalStateException("No release group found in mod metadata.\nPlease specify one with 'group: <group>'.")

                            jv.appendString("version", StringBuilder().apply{
                                append("-$releaseGroup")
                                if(releaseBuild != 0 && releaseGroup != "release") append("-$releaseBuild")
                            }.toString())
                        }
                    }

                    val filter = arrayOf(
                        "displayName", "name", "author",
                        "main", "description", "version",
                        "minGameVersion", "java", "hideBrowser",
                        "iosCompatible", "subtitle", "repo",
                        "dependencies", "softDependencies",
                        "texturescale", "pregenerated", "contentOrder",
                        "legacyCompatible"
                    )

                    if(jv.asObject().removeAll { fil ->
                        !filter.contains(fil.key)
                    }) Log.info("Removed all unnecessary mod metadata symbols")

                    return@use jv
                }

            meta.asFile.writer(Charsets.UTF_8).use{file -> BufferedWriter(file).use{map.writeTo(it, Jval.Jformat.formatted)}}
        }
    }

    val dex = tasks.register<Jar>("dex"){
        inputs.files(jar)
        archiveFileName = "${project.name}.jar"

        val desktopJar = jar.flatMap{it.archiveFile}
        val dexJar = File(temporaryDir, "Dex.jar")

        from(zipTree(desktopJar), zipTree(dexJar))
        doFirst{
            logger.lifecycle("Running `d8`.")
            providers.exec{
                // find Android SDK root.
                fun findSdk(): String {
                    // user defined SDK root (no validity, assume it just exists)
                    val commandLineSpec = projParamOrNull("android.sdk-path")
                    if(commandLineSpec != null) return commandLineSpec

                    // home directory
                    val osHome = OS.env(if(OS.isWindows) "USERPROFILE" else "HOME")
                    // check environment vars
                    arrayOf("ANDROID_SDK_ROOT", "ANDROID_HOME").forEach{ envs ->
                        try{
                            if(File(OS.env(envs)).exists()) println("Found '$envs'. Using that directory.")
                            return OS.env(envs)
                        }catch(_: Exception){} // ignore those
                    }
                    println("Environment variables not found. Testing common home subdirectories...")
                    // nothing there, so check the user home
                    arrayOf("android", ".android", "androidSdk", ".androidSdk").forEach{ commons ->
                        val file = File(osHome, commons)
                        if(file.exists()){
                            println("Found '$commons' in the user home. Using that directory.")
                            // print to error output for emphasis
                            System.err.println("[WARN] Add 'ANDROID_SDK_ROOT' or 'ANDROID_HOME' as an environment variable pointing to '$osHome/$commons'.")
                            return "$osHome/$commons"
                        }
                    }
                    // nothing found? well shit
                    throw IllegalStateException("Environment variables 'ANDROID_SDK_ROOT' and 'ANDROID_HOME' not found, alongside the common SDK paths.\n\nRefer to the mod README for details on setting these up.")
                }

                fun composeIncompleteToolkitError(missing: String): String {
                    val sdkHome = findSdk()
                    val seperator = if(OS.isWindows) " & " else "; "
                    val execStyle = if(OS.isWindows) "cmdline-tools\\bin\\sdkmanager.bat" else "./cmdline-tools/bin/sdkmanager"

                    return """
                        To download the appropriate SDK tools (assuming you have the cmdline-tools), run the following command:
                        
                        cd "$sdkHome"${seperator}$execStyle --sdk_root="$sdkHome" "$missing"
                        
                    """.trimIndent()
                }

                val sdkRoot = File(findSdk())

                // Find `d8`.
                val d8 = File(sdkRoot, "build-tools/$androidBuildVersion/${if(OS.isWindows) "d8.bat" else "d8"}")
                if(!d8.exists()) throw IllegalStateException("Android SDK `build-tools;$androidBuildVersion` isn't installed, is in the wrong directory or is corrupted\n${composeIncompleteToolkitError("build-tools;$androidBuildVersion")}")

                // Initialize a release build.
                val input = desktopJar.get().asFile
                val command = arrayListOf("$d8", "--release", "--min-api", androidMinVersion, "--output", "$dexJar", "$input")

                // Include all compile and runtime classpath.
                (configurations.compileClasspath.get().toList() + configurations.runtimeClasspath.get().toList()).forEach{
                    if(it.exists()) command.addAll(arrayOf("--classpath", it.path))
                }

                // Include Android platform as library.
                val androidJar = File(sdkRoot, "platforms/android-$androidSdkVersion/android.jar")
                if(!androidJar.exists()) throw IllegalStateException("Android SDK `platforms;android-$androidSdkVersion` isn't installed, is in the wrong directory or is corrupted\n${composeIncompleteToolkitError("platforms;android-$androidSdkVersion")}")

                command.addAll(arrayOf("--lib", "$androidJar"))
                if(OS.isWindows) command.addAll(0, arrayOf("cmd", "/c").toList())

                // Run `d8`.
                commandLine(command)
            }.result.get().rethrowFailure()
        }
    }

    fun Project.startMindustry(data: String) {
        val java = if(OS.isWindows) "java.exe" else "java"
        val jarFilePathString = if(project.hasProperty("game.path")) project.property("game.path").toString() else null
        val javaParams = if(project.hasProperty("game.params")) project.property("game.params").toString() else ""

        if(jarFilePathString != null) exec {
            val fJava = "$java -jar -Dmindustry.data.dir=$data $jarFilePathString $javaParams"
            println("Java runtime cmdline: \"$fJava\"")

            commandLine = fJava.split(' ')
            standardOutput = System.out
            errorOutput = System.err
        }
    }

    val copy = tasks.register("copy") {
        group = "copy"
        description = "Compiles a desktop-only jar and copies it to your Mindustry data directory."
        dependsOn(jar)

        val dir = OS.getAppDataDirectoryString("Mindustry")
        val dirC = projParamOrNull("copy.target")

        doLast {
            println("Copying mod...")

            val fDir = dirC ?: dir

            if(!fDir?.let {Fi(it).exists()}!!){
                if(fDir != dir){
                    println("WARN: Target copy directory ($fDir) does not exist. Skipping copy operation.")
                }else{
                    println("WARN: No Mindustry data directory found. Skipping copy operation.")
                }
                println("If you use a custom data directory, you may specify '-Pcopy.target=<path-to-mods-dir>'.")
                return@doLast
            }

            copy {
                from("${layout.buildDirectory.get()}/libs")
                into("$fDir/mods")
                include("${project.name}Desktop.jar")
            }

            println("Mod copied.")

            startMindustry(fDir)
        }
    }

    val copyDeploy = tasks.register<DefaultTask>("copyDeploy") {
        group = "copy"
        description = "Compiles a multiplatform jar and copies it to your Mindustry data directory."
        dependsOn(dex)

        val dir = OS.getAppDataDirectoryString("Mindustry")
        val dirC = projParamOrNull("copy.target")

        doLast {
            println("Copying mod...")

            val fDir = dirC ?: dir

            if(!fDir?.let {Fi(it).exists()}!!){
                if(fDir != dir){
                    println("WARN: Target copy directory ($fDir) does not exist. Skipping copy operation.")
                }else{
                    println("WARN: No Mindustry data directory found. Skipping copy operation.")
                }
                println("If you use a custom data directory, you may specify '-Pcopy.target=<path-to-mods-dir>'.")
                return@doLast
            }

            copy {
                from("${layout.buildDirectory.get()}/libs")
                into("$fDir/mods")
                include("${project.name}.jar")
            }

            println("Mod copied.")
        }
    }

    val androidCopy = tasks.register("androidCopy") {
        group = "copy"
        description = "Compiles a multiplatform jar and copies it to a connected device using ADB. This requires the device to have USB debugging enabled."
        dependsOn(dex)

        val adb = if(OS.isWindows) "adb.exe" else "adb"
        val serial = if(project.hasProperty("adb.serial")) "-s \"${project.property("adb.serial").toString()}\"" else ""

        val adbCmd = "$adb $serial".trim()

        doLast {
            println("Copying mod to connected device...")
            if(serial.isNotEmpty()) println("Target device: $serial")

            val target = if(useBE){ println("Using BE directory."); "io.anuke.mindustry.be" } else "io.anuke.mindustry"

            exec {
                commandLine = "$adbCmd push ${layout.buildDirectory.get()}/libs/${project.name}.jar /sdcard/Android/data/$target/files/mods".split(' ')
                standardOutput = System.out
                errorOutput = System.err
            }

            exec {
                commandLine = "$adbCmd shell chmod 755 /sdcard/Android/data/$target/files/mods/${project.name}.jar".split(' ')
                standardOutput = System.out
                errorOutput = System.err
            }

            if(quickstart) exec {
                println("Starting Mindustry on connected device...")
                commandLine = "$adbCmd shell am start -n $target/mindustry.android.AndroidLauncher".split(' ')
                standardOutput = System.out
                errorOutput = System.err
            }
        }
    }
}