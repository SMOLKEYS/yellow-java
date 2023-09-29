//modded gradle file
//origin "PKChaos/Ion"

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException

version = "1.0"
plugins {
    java
    kotlin("jvm") version "1.9.0"
}

val mindustryVersion = "v146"
val jabelCommitHash = "93fde537c7"
repositories {
    mavenCentral()
    maven("https://raw.githubusercontent.com/Zelaux/MindustryRepo/master/repository")
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
    compileOnly("com.github.Anuken.Arc:arc-core:$mindustryVersion")
    compileOnly("com.github.Anuken.Mindustry:core:$mindustryVersion")
    implementation("com.github.SMOLKEYS:kotmindy:9787d228d6")
    implementation("com.github.mnemotechnician:mkui:v1.3.2")
}

configurations.all {
    resolutionStrategy.eachDependency {
        if (requested.group == "com.github.Anuken.Arc") {
            useVersion(mindustryVersion)
        }
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        // target java version - 8. do not increase unless you really need to
        // this will not change a lot but will break mobile compatibility.
        jvmTarget = "1.8"
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
        //windows is weird
        val d8 = if(System.getProperty("os.name").toLowerCase().contains("windows")) "d8.bat" else "d8"
        //dex and desugar files - this requires d8 in your PATH
        val paras = "$dependencies --min-api 14 --output ${project.name}Android.jar ${project.name}Desktop.jar"
        try {
            exec {
                commandLine = "$d8 $paras".split(' ')
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

    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    archiveFileName.set("${project.name}Desktop.jar")

    from(*configurations.runtimeClasspath.get().files.map { if (it.isDirectory) it else zipTree(it) }.toTypedArray())

    from(rootDir) {
        include("mod.hjson")
        include("icon.png")
    }

    from("$rootDir/assets/") { include("**") }

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

task<Copy>("copy") {
    dependsOn("jar")

    println("Copying mod...")
    from("$buildDir/libs")
    into("${System.getenv("APPDATA")}\\Mindustry\\mods")
    include("${project.name}Desktop.jar")
}

task<Copy>("copyDeploy") {
    dependsOn("deploy")

    println("Copying mod...")
    from("$buildDir/libs")
    into("${System.getenv("APPDATA")}\\Mindustry\\mods")
    include("${project.name}.jar")
}