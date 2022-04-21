import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

buildscript {
    repositories {
        maven {
            url = uri("https://plugins.gradle.org/m2/")
        }
        mavenCentral()
        mavenLocal()
    }

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.21")
        classpath("com.gradle.publish:plugin-publish-plugin:0.21.0")
        classpath("si.kamino.gradle:lokalise:0.0.9")
    }
}

plugins {
    id("com.github.ben-manes.versions") version "0.42.0"
}

repositories {
    google()
    mavenCentral()
}

fun isNonStable(version: String): Boolean {
    val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.toUpperCase().contains(it) }
    val regex = "^[0-9,.v-]+(-r)?$".toRegex()
    val isStable = stableKeyword || regex.matches(version)
    return isStable.not()
}

tasks.named<DependencyUpdatesTask>("dependencyUpdates").configure {
    rejectVersionIf {
        isNonStable(candidate.version) && !isNonStable(currentVersion)
    }
}
