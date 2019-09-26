import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

dependencies {
    api(project(":share"))
    implementation(kotlin("stdlib-jdk8"))
    testImplementation(group = "ch.qos.logback", name = "logback-classic", version = "1.2.3")
    testImplementation(group = "org.jetbrains.kotlinx", name = "kotlinx-coroutines-core", version = "1.3.1")
    testImplementation(group = "io.kotlintest", name = "kotlintest-runner-junit5", version = "3.4.2") {
        exclude(group = "org.jetbrains.kotlinx", module = "kotlinx-coroutines-core")
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.withType<Test> {
    useJUnitPlatform { }
}
