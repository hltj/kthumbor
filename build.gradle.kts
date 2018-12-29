import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.11"
    application
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
    jcenter()
    mavenCentral()
}

val ktorVersion = "1.1.1"
fun ktor(module: String) = "io.ktor:ktor-$module:$ktorVersion"

dependencies {
    implementation(project(":share"))
    implementation(project(":parser"))
    implementation(project(":generator"))
    implementation(kotlin("stdlib-jdk8"))
    implementation(ktor("server-cio"))
    implementation(ktor("client-cio"))
    implementation(group = "ch.qos.logback", name = "logback-classic", version = "1.2.3")
    testImplementation(ktor("server-test-host"))
    testImplementation(kotlin("reflect"))
    testImplementation(group = "io.kotlintest", name = "kotlintest-runner-junit5", version = "3.1.11"){
        exclude(group = "org.jetbrains.kotlin", module = "kotlin-reflect")
    }
}

application.mainClassName = "io.ktor.server.cio.EngineMain"

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
    kotlinOptions.freeCompilerArgs = listOf("-Xuse-experimental=kotlin.Experimental")
}

tasks.withType<Test> {
    useJUnitPlatform { }
}
