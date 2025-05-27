import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    org.jetbrains.kotlin.plugin.serialization
    org.jetbrains.kotlin.jvm
    org.jetbrains.dokka
}

version = System.getenv(/* name = */ "CI_COMMIT_TAG") ?: System.getenv(/* name = */ "CI_COMMIT_SHORT_SHA")?.let { "$it-dev" } ?: "0.0.0"

repositories {
    mavenCentral()
}

public val javaVersion: JavaVersion = JavaVersion.VERSION_21

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(javaVersion.majorVersion))
    targetCompatibility = javaVersion
    sourceCompatibility = javaVersion

    withJavadocJar()
    withSourcesJar()
}

tasks {
    withType<JavaCompile> {
        options.encoding = "UTF-8"
    }

    withType<KotlinCompile> {
        compilerOptions {
            jvmTarget = JvmTarget.fromTarget(javaVersion.majorVersion)
        }
    }

    withType<Test> {
        useJUnitPlatform()
        reports.junitXml.required = true
        systemProperty("gradle.build.dir", layout.buildDirectory.get().asFile.absolutePath)

        filter {
            isFailOnNoMatchingTests = false
        }

        testLogging {
            showExceptions = true
            showStandardStreams = true
            events = setOf(TestLogEvent.FAILED, TestLogEvent.PASSED, TestLogEvent.SKIPPED)
            exceptionFormat = TestExceptionFormat.FULL
        }
    }

    dokkaHtml {
        description = Project.DESCRIPTION
        failOnWarning = true
    }
}

kotlin {
    explicitApi()
    compilerOptions {
        allWarningsAsErrors = true
    }
}
