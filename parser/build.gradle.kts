import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

dependencies {
    api(project(":share"))
    implementation(kotlin("stdlib-jdk8"))
    testImplementation(group = "ch.qos.logback", name = "logback-classic", version = "1.2.3")
    testImplementation(group = "io.kotest", name = "kotest-runner-junit5-jvm", version = "4.1.0")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.withType<Test> {
    useJUnitPlatform { }
}
