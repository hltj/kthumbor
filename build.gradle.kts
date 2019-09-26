import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.50"
    application
    `project-report`
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
}

allprojects {
    group = "me.hltj"
    version = "1.0-SNAPSHOT"

    repositories {
        jcenter()
        mavenCentral()
    }
}

repositories {
    mavenCentral()
    jcenter()
}

val ktorVersion = "1.2.4"
fun ktor(module: String) = "io.ktor:ktor-$module:$ktorVersion"

dependencies {
    implementation(project(":share"))
    implementation(project(":parser"))
    implementation(project(":generator"))
    implementation(kotlin("stdlib-jdk8"))
    implementation(group = "ch.qos.logback", name = "logback-classic", version = "1.2.3")
    implementation(group = "org.jetbrains.kotlinx", name = "kotlinx-coroutines-jdk8", version = "1.3.1")
    implementation(ktor("server-cio")) {
        exclude(group = "org.jetbrains.kotlinx", module = "kotlinx-coroutines-jdk8")
    }
    implementation(ktor("client-cio")) {
        exclude(group = "org.jetbrains.kotlinx", module = "kotlinx-coroutines-jdk8")
    }
    testImplementation(ktor("server-test-host")) {
        exclude(group = "org.jetbrains.kotlinx", module = "kotlinx-coroutines-jdk8")
    }
    testImplementation(group = "io.kotlintest", name = "kotlintest-runner-junit5", version = "3.4.2") {
        exclude(group = "org.jetbrains.kotlinx", module = "kotlinx-coroutines-core")
    }
}

application.mainClassName = "io.ktor.server.cio.EngineMain"

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
    kotlinOptions.freeCompilerArgs = listOf("-Xinline-classes", "-Xuse-experimental=kotlin.Experimental")
}

tasks.withType<Test> {
    useJUnitPlatform { }
}
