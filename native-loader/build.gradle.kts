val (mindustry, mindustryVersion, mindustrySource) = when(val version = providers.gradleProperty("mindustryVersion").get()){
    "latest" -> Triple("Mindustry", "latest", "Anuken/Mindustry/releases/latest/download/dependencies.jar")
    "be" -> Triple("MindustryBuilds", "latest", "Anuken/MindustryBuilds/releases/download/master/latest.jar")
    else -> Triple("Mindustry", version, "Anuken/Mindustry/releases/download/[revision]/dependencies.jar")
}

dependencies{
    compileOnly("Anuken:$mindustry:$mindustryVersion")
}