import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

dependencies {
    implementation(project(":share"))
    implementation(kotlin("stdlib-jdk8"))
    implementation(group = "net.coobird", name = "thumbnailator", version = "0.4.8")
    testImplementation(group = "ch.qos.logback", name = "logback-classic", version = "1.2.3")
    testImplementation(kotlin("reflect"))
    testImplementation(group = "org.jetbrains.kotlinx", name = "kotlinx-coroutines-core", version = "1.2.2") {}
    testImplementation(group = "io.kotlintest", name = "kotlintest-runner-junit5", version = "3.4.0") {
        exclude(group = "org.jetbrains.kotlin", module = "kotlin-stdlib")
        exclude(group = "org.jetbrains.kotlin", module = "kotlin-stdlib-jdk7")
        exclude(group = "org.jetbrains.kotlin", module = "kotlin-reflect")
        exclude(group = "org.jetbrains.kotlinx", module = "kotlinx-coroutines-core")
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.withType<Test> {
    useJUnitPlatform { }
}
