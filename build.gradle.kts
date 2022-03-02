import org.jetbrains.kotlin.gradle.tasks.KotlinCompile


plugins {
    id("application")
    id("com.github.johnrengelman.shadow") version "7.1.0"
    kotlin("jvm") version "1.6.0"
}

apply(plugin = "maven-publish")
apply(plugin = "java")
apply(plugin = "kotlin")
apply(plugin = "com.github.johnrengelman.shadow")

repositories {
    mavenCentral()
}

dependencies {
    api(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
        //freeCompilerArgs = freeCompilerArgs + arrayOf("-Xopt-in=kotlin.RequiresOptIn")
    }
}

tasks["build"].dependsOn("shadowJar")

application {
    mainClass.set("MainKt")
}

tasks {
    shadowJar {
        archiveFileName.set("json2kt.shadow.jar")
    }
}
