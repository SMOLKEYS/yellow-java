import arc.util.Log
import arc.util.OS
import com.badlogic.gdx.jnigen.commons.CompilerABIType
import com.badlogic.gdx.jnigen.gradle.JnigenExtension.*
import org.gradle.kotlin.dsl.get

plugins {
    id("com.badlogicgames.jnigen.jnigen-gradle") version "3.1.1"
}

jnigen {

    nativeCodeGenerator {
        // merge the project compile classpath with the generator's runtime classpath
        // this allows the jnigen task to work on classes that use dependency libraries
        sourceSet.runtimeClasspath += sourceSets["main"].compileClasspath.filter {
            //Log.info(it.name) for debugging
            // exclude EntityAnno, since native code inside entity classes isn't necessary
            // + it throws a compile error if the library IS included
            // + native code inside generated classes might be beyond jnigen's scope
            // + is probably just a bad idea in general
            // (then again, this use of jnigen in a mod is also probably a bad idea)
            !it.name.contains("entity")
        }
    }

    sharedLibName = "yellow"
    libsDir = "build/natives"

    fun libs(buildName: String): String {
        return "-Lffmpeg/build/$buildName/lib"
        //return "-L${file("ffmpeg/build/$buildName").absolutePath}/lib"
    }

    fun headers(buildName: String): Array<String> {
        return arrayOf("ffmpeg/build/$buildName/include")
        //return arrayOf("${file("ffmpeg/build/$buildName").absolutePath}/include")
    }

    all {
        headerDirs += arrayOf("jni")
        cFlags += arrayOf("-fvisibility=hidden")
        cppFlags += arrayOf("-fvisibility=hidden")
        cIncludes += arrayOf("jni/*.c")
        cppIncludes += arrayOf("jni/*.cpp")
        libraries += "-lavformat -lavcodec -lavutil -lswscale -lswresample -lpthread -ldav1d".split(" ")
    }

    addWindows(x64, x86){
        headerDirs += headers("windows64")
        libraries = arrayOf(libs("windows64"), "-lbcrypt", "-lws2_32") + libraries
        Log.info(libraries.contentDeepToString())
        cppFlags += arrayOf("-DWIN32")
    }
    addAndroid()
    addLinux(x64, x86) {
        headerDirs += headers("linux64")
        libraries += libs("linux64")
        linkerFlags += arrayOf("-Wl,-Bsymbolic", "-Wl,--no-undefined")
    }
    // TODO not used yet
    addMac(x64, x86)
    addMac(x64, ARM)
}

val lwjglVersion = "3.4.1"

repositories {
    gradlePluginPortal()
    mavenLocal()
    mavenCentral()
    maven("https://oss.sonatype.org/content/repositories/snapshots/")
    maven("https://oss.sonatype.org/content/repositories/releases/")
    maven("https://central.sonatype.com/repository/maven-snapshots")
}


tasks.named("jnigenBuildAllWindows"){
    dependsOn("ffmpeg:buildFFmpegWindowsAll")
}

tasks.named("jnigenBuildAllLinux"){
    dependsOn("ffmpeg:buildFFmpegLinuxAll")
}

tasks.register("postJnigen") {
    dependsOn("jnigen", "jnigenBuildAllWindows", /*"jnigenBuildAllAndroid",*/ "jnigenBuildAllLinux")
}


val dir: String = jnigen.libsDir

tasks.named<Jar>("jar") {
    dependsOn("postJnigen")

    duplicatesStrategy = DuplicatesStrategy.EXCLUDE

    from("$dir/linux64", "$dir/windows64", "$dir/android32", "$dir/macosx64")
    include("**")

    from(
        configurations.runtimeClasspath.map{conf -> conf.map{if(it.isDirectory) it else zipTree(it)}}
    )
}
