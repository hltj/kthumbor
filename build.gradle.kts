import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.72"
    application
    jacoco
    `project-report`
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
}

allprojects {
    group = "me.hltj"
    version = "1.0-SNAPSHOT"

    repositories {
        mavenCentral()
        jcenter()
    }
}

repositories {
    mavenCentral()
    jcenter()
}

val ktorVersion = "1.3.2"
fun ktor(module: String) = "io.ktor:ktor-$module:$ktorVersion"

dependencies {
    implementation(project(":share"))
    implementation(project(":parser"))
    implementation(project(":generator"))
    implementation(kotlin("stdlib-jdk8"))
    implementation(group = "ch.qos.logback", name = "logback-classic", version = "1.2.3")
    implementation(ktor("server-cio"))
    implementation(ktor("client-cio"))
    testImplementation(ktor("server-test-host"))
    testImplementation(group = "io.kotest", name = "kotest-runner-junit5-jvm", version = "4.1.0")
}

application.mainClassName = "io.ktor.server.cio.EngineMain"

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
    kotlinOptions.freeCompilerArgs = listOf("-Xinline-classes", "-Xopt-in=kotlin.Experimental")
}

tasks.test {
    useJUnitPlatform { }
    finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.isEnabled = true
        xml.destination = file("$buildDir/reports/jacoco/report.xml")
        csv.isEnabled = false
    }
}
