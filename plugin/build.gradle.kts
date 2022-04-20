plugins {
    id("org.jetbrains.kotlin.jvm")
    id("idea")
    id("java-gradle-plugin")
    id("com.gradle.plugin-publish")
    id("maven-publish")
    id("org.jetbrains.kotlin.plugin.serialization") version "1.6.21"
}

group = project.findProperty("projectGroup")!!
version = project.findProperty("projectVersion")!!

gradlePlugin {
    plugins {
        create("lokalise-base") {
            id = "si.kamino.gradle.lokalise.base"
            implementationClass = "si.kamino.gradle.lokalise.LokaliseBasePlugin"
            displayName = "Base Lokalise Gradle Plugin"
        }
        create("lokalise") {
            id = "si.kamino.gradle.lokalise"
            implementationClass = "si.kamino.gradle.lokalise.LokalisePlugin"
            displayName = "Lokalise Gradle Plugin"
        }
    }
}

repositories {
    google()
    mavenCentral()
}

dependencies {
    implementation(gradleApi())
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.6.20")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.1")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0")

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.9.3")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.3")

    testImplementation("junit:junit:4.13.2")
}

pluginBundle {
    website = project.findProperty("POM_URL") as String
    vcsUrl = project.findProperty("POM_SCM_URL") as String
    description = project.findProperty("POM_DESCRIPTION") as String
    tags = listOf("android","lokalise")
}
