import arc.util.*
import arc.util.serialization.Jval
import ent.*
import mindustry.client.*
import mindustry.client.service.*
import mindustry.client.task.*
import java.io.BufferedWriter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone
import java.util.jar.*

fun projParam(project: Project = rootProject, name: String, default: String = "") = projParamOrNull(project, name, default)

fun projParamOrNull(project: Project = rootProject, name: String, default: String? = null) = (if(project.hasProperty(name)) project.property(name) else default) as String?

buildscript{
    val (mindustry, mindustryVersion, mindustrySource) = when(val version = providers.gradleProperty("mindustryVersion").get()){
        "latest" -> Triple("Mindustry", "latest", "Anuken/Mindustry/releases/latest/download/dependencies.jar")
        "be" -> Triple("MindustryBuilds", "latest", "Anuken/MindustryBuilds/releases/download/master/latest.jar")
        else -> Triple("Mindustry", version, "Anuken/Mindustry/releases/download/[revision]/dependencies.jar")
    }

    dependencies{
        classpath("Anuken:$mindustry:$mindustryVersion")
    }

    configurations.configureEach{
        // Resolve the correct Mindustry dependency.
        resolutionStrategy.eachDependency{
            if(requested.group == "Anuken" && requested.name.startsWith("Mindustry")){
                useTarget("Anuken:$mindustry:$mindustryVersion")
            }
        }
    }

    repositories{
        ivy{
            url = uri("https://github.com")
            patternLayout{
                val path = mindustrySource.substringBeforeLast('/')
                val name = mindustrySource
                    .substringAfterLast('/')
                    .removeSuffix(".jar")

                artifact("$path/[classifier].jar")
                artifact("$path/$name(-[classifier]).jar")
                metadataSources{artifact()}
            }
            content{
                includeVersion("Anuken", mindustry, mindustryVersion)
            }
        }
    }
}

plugins{
    java
    id("com.github.GglLfr.EntityAnno") apply false
    id("com.github.GglLfr.MindustryClient") apply false
}

val (mindustry, mindustryVersion, mindustrySource) = when(val version = providers.gradleProperty("mindustryVersion").get()){
    "latest" -> Triple("Mindustry", "latest", "Anuken/Mindustry/releases/latest/download/dependencies.jar")
    "be" -> Triple("MindustryBuilds", "latest", "Anuken/MindustryBuilds/releases/download/master/latest.jar")
    else -> Triple("Mindustry", version, "Anuken/Mindustry/releases/download/[revision]/dependencies.jar")
}
val entVersion = providers.gradleProperty("entVersion").get()

val modArtifact = providers.gradleProperty("modArtifact").get()
val modFetch = providers.gradleProperty("modFetch").get()
val modGenSrc = providers.gradleProperty("modGenSrc").get()
val modGen = providers.gradleProperty("modGen").get()

allprojects{
    apply(plugin = "java")
    sourceSets["main"].java.setSrcDirs(listOf(layout.projectDirectory.dir("src")))

    java{
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    dependencies{
        registerTransform(TrimSources::class){
            from.attribute(ArtifactTypeDefinition.ARTIFACT_TYPE_ATTRIBUTE, ArtifactTypeDefinition.JAR_TYPE)
            to.attribute(ArtifactTypeDefinition.ARTIFACT_TYPE_ATTRIBUTE, "jar-stripped")
        }
    }

    configurations.configureEach{
        // Resolve the correct Mindustry dependency.
        resolutionStrategy.eachDependency{
            if(requested.group == "Anuken" && requested.name.startsWith("Mindustry")){
                useTarget("Anuken:$mindustry:$mindustryVersion")
            }
        }

        if(isCanBeResolved) attributes{
            attribute(ArtifactTypeDefinition.ARTIFACT_TYPE_ATTRIBUTE, "jar-stripped")
        }
    }

    repositories{
        // Use Ivy repository for Mindustry builds.
        ivy{
            url = uri("https://github.com")
            patternLayout{
                val path = mindustrySource.substringBeforeLast('/')
                val name = mindustrySource
                    .substringAfterLast('/')
                    .removeSuffix(".jar")

                artifact("$path/[classifier].jar")
                artifact("$path/$name(-[classifier]).jar")
                metadataSources{artifact()}
            }
            content{
                includeVersion("Anuken", mindustry, mindustryVersion)
            }
        }

        // Necessary Maven repositories to pull dependencies from.
        mavenLocal()
        mavenCentral()
        maven("https://oss.sonatype.org/content/repositories/snapshots/")
        maven("https://oss.sonatype.org/content/repositories/releases/")
        maven("https://raw.githubusercontent.com/GglLfr/EntityAnnoMaven/main")

        // custom code: For other things
        maven("https://maven.xpdustry.com/mindustry")
    }

    tasks.withType<JavaCompile>().configureEach{
        options.apply{
            compilerArgs.add("-Xlint:-options")
            compilerArgs.add("-implicit:none")
            compilerArgs.addAll(providers.gradleProperty("org.gradle.jvmargs").get()
                .split(Regex("\\s+"))
                .filter{it.startsWith("--add-opens")}
                .map{"--add-exports=${it.substring("--add-opens=".length)}"}
            )

            isIncremental = true
            isFork = false
            encoding = "UTF-8"
        }
    }
}

project(":core"){
    apply(plugin = "com.github.GglLfr.EntityAnno")
    apply(plugin = "com.github.GglLfr.MindustryClient")

    configure<EntityAnnoExtension>{
        revisionDir = layout.projectDirectory.dir("revisions")
        fetchPackage = modFetch
        genSrcPackage = modGenSrc
        genPackage = modGen
    }

    dependencies{
        // Use the entity generation annotation processor.
        compileOnly("com.github.GglLfr.EntityAnno:entity:$entVersion")
        annotationProcessor("com.github.GglLfr.EntityAnno:entity:$entVersion")
        compileOnly("com.github.Anuken.Arc:discord:$mindustryVersion")

        compileOnly("Anuken:$mindustry:$mindustryVersion")
    }

    val client = gradle.sharedServices.registerIfAbsent(MindustryClientPlugin.serviceName, MindustryClientService::class.java){}
    val jar = tasks.named<Jar>("jar") {
        archiveFileName = "${modArtifact}Desktop.jar"

        // Can be any of either `mod.json` or `mod.hjson`.
        val meta = layout.projectDirectory.file("$temporaryDir/mod.json")
        from(
            files(sourceSets["main"].output.classesDirs),
            files(sourceSets["main"].output.resourcesDir),
            configurations.runtimeClasspath.map { conf -> conf.map { if (it.isDirectory) it else zipTree(it) } },
            files(layout.projectDirectory.dir("assets")),
            layout.projectDirectory.file("icon.png"),
            meta
        )

        metaInf.from(layout.projectDirectory.file("LICENSE"))

        val metaJson = layout.projectDirectory.file("mod.json")
        val metaHjson = layout.projectDirectory.file("mod.hjson")

        if (metaJson.asFile.exists() && metaHjson.asFile.exists()) {
            throw IllegalStateException("Ambiguous mod meta: both `mod.json` and `mod.hjson` exist.")
        } else if (!metaJson.asFile.exists() && !metaHjson.asFile.exists()) {
            throw IllegalStateException("Missing mod meta: neither `mod.json` nor `mod.hjson` exist.")
        }

        val isJson = metaJson.asFile.exists()
        val map = (if (isJson) metaJson else metaHjson).asFile
            .reader(Charsets.UTF_8)
            .use {
                fun Jval.appendString(value: String, append: String): Jval {
                    val prm = getString(value)
                    return put(value, "$prm$append")
                }

                fun String.abbreviate(char: Char = ' '): String {
                    val sb = StringBuilder()
                    this.split(char).forEach { st ->
                        sb.append(st[0])
                    }
                    return sb.toString()
                }

                val jv = Jval.read(it)

                val append = projParamOrNull(this@project, "rapid.append-version")
                val releaseType = projParamOrNull(this@project, "release.type")

                val simpleDateFormat = SimpleDateFormat("dd-MM-yyyy_hh-mm-ss")
                val currentDate = Date()
                val localTz = TimeZone.getDefault()?.displayName ?: "Unidentifiable User Location"

                val dateIdentifier = "${simpleDateFormat.format(currentDate)}_${localTz.abbreviate()}"

                if (append != null) {
                    jv.appendString("version", "-rapid-$append-$dateIdentifier")
                    jv.appendString("displayName", " RDev Build")
                    jv.appendString("description", "\n\n[RAPID DEVELOPMENT BUILD]")
                } else when (releaseType) {
                    null -> jv.appendString("version", "-dev-$dateIdentifier")
                    "release" -> {
                        val releaseGroup = jv.getString("group")
                        val releaseBuild = jv.getInt("build", 0)

                        if (releaseGroup == null) throw IllegalStateException("No release group found in mod metadata.\nPlease specify one with 'group: <group>'.")

                        jv.appendString("version", StringBuilder().apply {
                            append("-$releaseGroup")
                            if (releaseBuild != 0 && releaseGroup != "release") append("-$releaseBuild")
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

                if (jv.asObject().removeAll { fil ->
                        !filter.contains(fil.key)
                    }) Log.info("Removed all unnecessary mod metadata symbols")

                return@use jv
            }

        meta.asFile.writer(Charsets.UTF_8)
            .use { file -> BufferedWriter(file).use { map.writeTo(it, Jval.Jformat.formatted) } }
    }

    val dexOutput = layout.buildDirectory.file("dex/Dex.jar")
    val dex = tasks.register<Exec>("dex"){
        description = "Runs `d8` against the desktop JAR to build an ART bytecode."
        outputs.file(dexOutput).withPropertyName("output")

        val androidSdkVersion = providers.gradleProperty("androidSdkVersion").get()
        val androidBuildVersion = providers.gradleProperty("androidBuildVersion").get()
        val androidMinVersion = providers.gradleProperty("androidMinVersion").get()
        val desktopJar = jar.flatMap{it.archiveFile}

        inputs.property("sdkVersion", androidSdkVersion)
        inputs.property("buildVersion", androidBuildVersion)
        inputs.property("minVersion", androidMinVersion)
        inputs.files(desktopJar).withPropertyName("jarOutput")

        val classpaths = files(configurations.compileClasspath, configurations.runtimeClasspath)
        inputs.files(classpaths).withPropertyName("artClasspaths").withNormalizer(ClasspathNormalizer::class.java)

        doFirst{
            val userHome = OS.env(if(OS.isWindows) "USERPROFILE" else "HOME")
            // Find Android SDK root.
            val sdkRoot = File(
                OS.env("ANDROID_HOME") ?: OS.env("ANDROID_SDK_ROOT")
                ?: arrayOf("android", ".android", "androidSdk", ".androidSdk").find { File(userHome, it).exists() }.let {
                    if(it != null) "$userHome/$it" else null
                }
                ?: throw GradleException()
            )

            logger.lifecycle("path: ${sdkRoot.absolutePath}")

            // Find `d8`.
            val d8 = File(sdkRoot, "build-tools/$androidBuildVersion/${if(OS.isWindows) "d8.bat" else "d8"}")
            if(!d8.exists()) throw GradleException("Android SDK `build-tools;$androidBuildVersion` isn't installed or is corrupted")

            // Initialize a release build.
            val input = desktopJar.get().asFile
            val command = arrayListOf("$d8", "--release", "--min-api", androidMinVersion, "--output", "${dexOutput.get().asFile.absolutePath}", "$input")

            // Include all compile and runtime classpath.
            classpaths.forEach{
                if(it.exists()) command.addAll(arrayOf("--classpath", it.path))
            }

            // Include Android platform as library.
            val androidJar = File(sdkRoot, "platforms/android-$androidSdkVersion/android.jar")
            if(!androidJar.exists()) throw GradleException("Android SDK `platforms;android-$androidSdkVersion` isn't installed or is corrupted")

            command.addAll(arrayOf("--lib", "$androidJar"))
            if(OS.isWindows) command.addAll(0, arrayOf("cmd", "/c").toList())

            // Run `d8`.
            commandLine(command)
        }
    }

    val deploy = tasks.register<Jar>("deploy"){
        description = "Builds an Android-compatible JAR from the desktop-only JAR. Use this file for GitHub release."
        dependsOn(dex)

        archiveFileName = "$modArtifact.jar"
        from(zipTree(jar.flatMap{it.archiveFile}), zipTree(dexOutput))
    }

    val install = tasks.register<DefaultTask>("install"){
        description = "Installs the desktop JAR to your `mods/` folder."
        usesService(client)

        val desktopJar = jar.flatMap{it.archiveFile}
        val deployJarName = deploy.flatMap{it.archiveFileName}

        inputs.files(desktopJar).withPropertyName("desktopJar")
        doLast{
            val mods = client.get().detected.modsDirectory
            mods.mkdirs()
            mods.resolve(deployJarName.get()).delete()

            val input = desktopJar.get().asFile
            val output = mods.resolve(input.name)
            input.copyTo(output, overwrite = true)

            logger.lifecycle("Copied :jar output to ${mods}.")
        }
    }

    tasks.withType<RunClientTask>().configureEach{
        dependsOn(install)
    }
}

abstract class TrimSources : TransformAction<TransformParameters.None>{
    @get:InputArtifact
    abstract val file: Provider<FileSystemLocation>

    override fun transform(outputs: TransformOutputs){
        val input = file.get().asFile
        val classes = outputs.file(input.name)

        JarFile(input).use{jar ->
            val entries = jar.entries()
            classes.outputStream().use{JarOutputStream(it).use{classes ->
                for(entry in entries){
                    if(entry.name.endsWith(".java")) continue

                    classes.putNextEntry(JarEntry(entry.name))
                    jar.getInputStream(entry).use{jar -> jar.copyTo(classes)}
                    classes.closeEntry()
                }
            }}
        }
    }
}
