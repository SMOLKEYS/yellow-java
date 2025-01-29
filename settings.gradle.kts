pluginManagement{
    repositories{
        gradlePluginPortal()
        maven("https://jitpack.io")
    }
}

if(JavaVersion.current().ordinal < JavaVersion.VERSION_17.ordinal) throw GradleException("JDK 17 is a required minimum version. Yours: ${System.getProperty("java.version")}")

rootProject.name = "yellow-java"
//include(":discord")

