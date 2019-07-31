import java.io.File

val userDir = System.getProperty("user.dir")
var currentDir: String = userDir

fun forward(child: String) {
    currentDir = "$currentDir/$child"
    with(File(currentDir)) {
        if (!exists()) mkdirs()
    }
}

fun backward() {
    currentDir = currentDir.substringBeforeLast("/")
}

fun goTo(path: String) {
    currentDir = path
}

fun file(name: String, content: String) {
    file(currentDir, name, content)
}

fun file(path: String, name: String, content: String) {
    with(File("$path/$name")) {
        if (!exists()) {
            parentFile.mkdirs()
            createNewFile()
        }
        writeText(content)
    }
}

fun ensureDirs(child: String) {
    ensureDirs(currentDir, child)
}

fun ensureDirs(path: String, child: String) {
    with(File("$path/$child")) {
        if (!exists()) mkdirs()
    }
}

fun includeModuleInSettingsGradle(moduleId: String) {
    // find settings gradle kts
    var includeTag = ""
    var settingsGradleKtsFile: File? = null
    var current: File? = File(userDir)
    while (current != null) {
        settingsGradleKtsFile = File("${current.absolutePath}/settings.gradle.kts")
        if (settingsGradleKtsFile.exists()) break
        settingsGradleKtsFile = null
        includeTag = ":${current.name}$includeTag"
        current = current.parentFile
    }

    if (settingsGradleKtsFile != null) {
        includeTag += ":$moduleId"

        // the following code assumes that the settings.gradle.kts file is written in the following style
        //
        // include(
        //     ":module1",
        //     ":module2",
        //     ":module3",
        // )
        //

        val settingsGradleKtsContent = settingsGradleKtsFile.readText()

        val oldModules = settingsGradleKtsContent
            .substringAfter("include(")
            .replace(")", "")
            .replace("\"", "")
            .split(",")
            .map { it.trim() }

        if (!oldModules.contains(includeTag)) {
            val newModules = (oldModules + includeTag)
                .sorted()

            settingsGradleKtsFile.writeText(
                buildString {
                    append("include(\n")
                    newModules.forEachIndexed { index, module ->
                        append("    ") // indent by 4
                        append("\"")
                        append(module)
                        append("\"")
                        if (index != newModules.lastIndex) {
                            append(",\n")
                        }
                    }
                    append("\n")
                    append(")")
                }
            )
        }
    }
}