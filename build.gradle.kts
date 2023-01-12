//modded gradle file
//origin "PKChaos/Ion"

import java.io.ByteArrayOutputStream

version = "1.0"
plugins {
    java
    kotlin("jvm") version "1.8.0"
}

val mindustryVersion = "v140.3"
val jabelCommitHash = "93fde537c7"
repositories {
    mavenCentral()
    maven("https://jitpack.io")
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
    compileOnly("com.github.Anuken.Arc:arc-core:$mindustryVersion")
    //flabel hash eae3c2dc59 is broken
    //compileOnly("com.github.Anuken.Arc:flabel:$mindustryVersion")
    compileOnly("com.github.Anuken.Mindustry:core:$mindustryVersion")
    implementation("com.github.SMOLKEYS:kotmindy:9787d228d6")
    implementation("com.github.mnemotechnician:mkui:v1.2")
    
    //annotationProcessor("com.github.Anuken:jabel:$jabelCommitHash")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}


val genYellowClasspath by tasks.registering {
    dependsOn("compileKotlin")
    dependsOn("compileJava")

    inputs.files(configurations.runtimeClasspath.get().files)
    outputs.dir("$buildDir/gen-assets/")

    doLast {
        val packages = sourceSets.main.get().output.classesDirs
            .flatMap {
                // collect all classes
                it.walkTopDown().filter { 
                    !it.isDirectory && it.name.endsWith(".class") && "$" !in it.name 
                }.toList()
            }.map {
                // turn class files pathes into packages
                it.absolutePath.removeSuffix(".class")
                    .removePrefix("$buildDir/classes/")
                    .removePrefix("java/main/").removePrefix("kotlin/main/") // any can be present
                    .replace('/', '.')
                    .substringBeforeLast('.') // turining class path into package path
            }
            .distinct() // remove duplicate packages
            .sorted() // because why not
            .joinToString("\n")
        
        File("$buildDir/gen-assets/classpaths/").let {
            it.mkdirs()
            it.resolve("yellow-classpath.txt").writeText(packages)
        }
    }
}

tasks.register("jarAndroid") {
    group = "build"
    dependsOn("jar")

    doLast {
        val sdkRoot = System.getenv("ANDROID_HOME") ?: System.getenv("ANDROID_SDK_ROOT")
        if (sdkRoot == null || !File(sdkRoot).exists())
            throw GradleException("No valid Android SDK found. Ensure that ANDROID_HOME is set to your Android SDK directory.")
        val platformRoot = File("$sdkRoot/platforms/").listFiles()!!.sorted().reversed()
            .find { f -> File(f, "android.jar").exists() }
            ?: throw GradleException("No android.jar found. Ensure that you have an Android platform installed.")
        //collect dependencies needed for desugaring
        val allDependencies = configurations.compileClasspath.get().toList() +
                configurations.runtimeClasspath.get().toList() +
                listOf(File(platformRoot, "android.jar"))
        val dependencies = allDependencies.joinToString(" ") { "--classpath ${it.path}" }
        //dex and desugar files - this requires d8 in your PATH
        val paras = "$dependencies --min-api 14 --output ${project.name}Android.jar ${project.name}Desktop.jar"
        try {
            exec {
                commandLine = "d8 $paras".split(' ')
                workingDir = File("$buildDir/libs")
                standardOutput = System.out
                errorOutput = System.err
            }
        } catch (_: Exception) {
            val cmdOutput = ByteArrayOutputStream()
            logger.lifecycle("d8 cannot be found in your PATH, so trying to use an absolute path.")
            exec {
                commandLine = listOf("where", "d8")
                standardOutput = cmdOutput
                errorOutput = System.err
            }
            val d8FullPath = cmdOutput.toString().replace("\r", "").replace("\n", "")
            exec {
                commandLine = "$d8FullPath $paras".split(' ')
                workingDir = File("$buildDir/libs")
                standardOutput = System.out
                errorOutput = System.err
            }
        }
    }
}

tasks.jar {
    dependsOn(genYellowClasspath)

    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    archiveFileName.set("${project.name}Desktop.jar")

    from(*configurations.runtimeClasspath.get().files.map { if (it.isDirectory) it else zipTree(it) }.toTypedArray())

    from(rootDir) {
        include("mod.hjson")
        include("icon.png")
    }

    from("$rootDir/assets/") { include("**") }

    from("$buildDir/gen-assets/") { include("**") }
}

task<Jar>("deploy") {
    dependsOn("jarAndroid")
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    archiveFileName.set("${project.name}.jar")
    from(
        zipTree("$buildDir/libs/${project.name}Desktop.jar"),
        zipTree("$buildDir/libs/${project.name}Android.jar")
    )
    doLast {
        delete { delete("$buildDir/libs/${project.name}Desktop.jar") }
        delete { delete("$buildDir/libs/${project.name}Android.jar") }
    }
}
