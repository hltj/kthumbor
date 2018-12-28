import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    testImplementation(kotlin("reflect"))
    testImplementation(group = "io.kotlintest", name = "kotlintest-runner-junit5", version = "3.1.11")
    testImplementation(group = "ch.qos.logback", name = "logback-classic", version = "1.2.3")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
    kotlinOptions.freeCompilerArgs = listOf("-XXLanguage:+InlineClasses")
}

tasks.withType<Test> {
    useJUnitPlatform { }
}
