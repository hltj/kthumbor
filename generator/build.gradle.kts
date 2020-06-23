import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

dependencies {
    implementation(project(":share"))
    implementation(kotlin("stdlib-jdk8"))
    implementation(group = "net.coobird", name = "thumbnailator", version = "0.4.8")
    testImplementation(group = "ch.qos.logback", name = "logback-classic", version = "1.2.3")
    testImplementation(group = "io.kotest", name = "kotest-runner-junit5", version = "4.1.0")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.withType<Test> {
    useJUnitPlatform { }
}
