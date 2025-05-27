import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    org.jetbrains.kotlin.jvm
}

project.extensions.create<BuildConstantsExtension>("buildConstants")

tasks {
    val generateBuildConstants = register<GenerateBuildConstants>("generateBuildConstants") {
        group = Project.NAME.lowercase()
        description = "Generates the Buildconstants for ${Project.NAME}"
    }

    withType<KotlinCompile> {
        dependsOn(generateBuildConstants)
    }
}

kotlin {
    sourceSets {
        main {
            kotlin.srcDir(buildConstantDir())
        }
    }
}