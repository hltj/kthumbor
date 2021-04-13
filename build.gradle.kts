import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

plugins {
    kotlin("jvm") version "1.4.32"
    application
    jacoco
    `project-report`
    id("com.github.ben-manes.versions") version "0.38.0"
}

subprojects {
    apply {
        plugin("org.jetbrains.kotlin.jvm")
        plugin("jacoco")
    }
}

allprojects {
    group = "me.hltj"
    version = "1.0-SNAPSHOT"

    repositories {
        mavenCentral()
    }

    tasks.compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
        kotlinOptions.useIR = true
    }

    tasks.compileTestKotlin {
        kotlinOptions.useIR = true
    }

    tasks.test {
        useJUnitPlatform()
    }

    dependencies {
        implementation(kotlin("stdlib-jdk8"))
        testImplementation(group = "org.slf4j", name = "slf4j-api", version = "1.7.30")
        testImplementation(group = "ch.qos.logback", name = "logback-classic", version = "1.2.3") {
            exclude(group = "org.slf4j", module = "slf4j-api")
        }
        testImplementation(group = "io.kotest", name = "kotest-runner-junit5-jvm", version = "4.4.3")
    }
}

repositories {
    mavenCentral()
}

val ktorVersion = "1.5.3"
fun ktor(module: String) = "io.ktor:ktor-$module:$ktorVersion"

dependencies {
    implementation(project(":share"))
    implementation(project(":parser"))
    implementation(project(":generator"))
    implementation(group = "ch.qos.logback", name = "logback-classic", version = "1.2.3") {
        exclude(group = "org.slf4j", module = "slf4j-api")
    }
    implementation(ktor("server-cio"))
    implementation(ktor("client-cio"))
    testImplementation(ktor("server-test-host"))
}

application.mainClass.set("io.ktor.server.cio.EngineMain")

tasks.compileKotlin {
    kotlinOptions.freeCompilerArgs = listOf("-Xinline-classes", "-Xopt-in=kotlin.Experimental")
}

tasks.test {
    finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
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
