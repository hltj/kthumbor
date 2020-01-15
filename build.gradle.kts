import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.61"
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
        mavenCentral()
        jcenter()
    }
}

repositories {
    mavenCentral()
    jcenter()
}

val ktorVersion = "1.3.0"
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
    testImplementation(group = "io.kotlintest", name = "kotlintest-runner-junit5", version = "3.4.2") {
        exclude(group = "org.jetbrains.kotlin", module = "kotlin-reflect")
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
