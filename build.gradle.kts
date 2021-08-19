import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

plugins {
    kotlin("jvm") version "1.5.21"
    application
    jacoco
    `project-report`
    id("com.github.ben-manes.versions") version "0.39.0"
}

subprojects {
    apply {
        plugin("org.jetbrains.kotlin.jvm")
        plugin("jacoco")
    }

    tasks.test {
        useJUnitPlatform()
    }
}

val logbackDependency = "ch.qos.logback:logback-classic:1.2.5"

allprojects {
    group = "me.hltj"
    version = "1.0-SNAPSHOT"

    repositories {
        mavenCentral()
    }

    tasks.compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }

    dependencies {
        implementation(kotlin("stdlib-jdk8"))
        testImplementation(group = "org.slf4j", name = "slf4j-api", version = "1.7.32")
        testImplementation(logbackDependency) {
            exclude(group = "org.slf4j", module = "slf4j-api")
        }
        testImplementation(group = "io.kotest", name = "kotest-runner-junit5-jvm", version = "4.6.1")
    }
}

repositories {
    mavenCentral()
}

val ktorVersion = "1.6.2"
fun ktor(module: String) = "io.ktor:ktor-$module:$ktorVersion"

dependencies {
    implementation(project(":share"))
    implementation(project(":parser"))
    implementation(project(":generator"))
    implementation(logbackDependency) {
        exclude(group = "org.slf4j", module = "slf4j-api")
    }
    implementation(ktor("server-cio"))
    implementation(ktor("client-cio"))
    testImplementation(ktor("server-test-host"))
}

application.mainClass.set("io.ktor.server.cio.EngineMain")

tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}

jacoco {
    toolVersion = "0.8.7"
}

tasks.jacocoTestReport {
    dependsOn(subprojects.map { it.tasks.test })
    dependsOn(tasks.test)
    dependsOn(tasks.withType<CreateStartScripts>())

    executionData(fileTree(project.rootDir.absolutePath).include("**/build/jacoco/*.exec"))

    subprojects.forEach {
        sourceSets(it.sourceSets["main"])
    }

    reports {
        xml.isEnabled = true
        xml.destination = file("$buildDir/reports/jacoco/report.xml")
        csv.isEnabled = false
    }
}

tasks.named<DependencyUpdatesTask>("dependencyUpdates").configure {
    rejectVersionIf {
        isNonStable(candidate.version)
    }
}

fun isNonStable(version: String): Boolean {
    val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.toUpperCase().contains(it) }
    val regex = "^[0-9,.v-]+(-r)?$".toRegex()
    return !stableKeyword && !regex.matches(version)
}
