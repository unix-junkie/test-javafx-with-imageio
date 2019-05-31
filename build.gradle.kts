plugins {
    id("org.jetbrains.kotlin.jvm").version("1.3.21")
}

repositories {
    jcenter()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.1.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:1.1.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-swing:1.1.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-javafx:1.1.0")
}
