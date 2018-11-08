import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

dependencies {
    implementation(project(":share"))
    implementation(kotlin("stdlib-jdk8"))
    testImplementation(kotlin("reflect"))
    testImplementation(group = "io.kotlintest", name = "kotlintest-runner-junit5", version = "3.1.10")
    testImplementation(group = "ch.qos.logback", name = "logback-classic", version = "1.2.3")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.withType<Test> {
    useJUnitPlatform { }
}
