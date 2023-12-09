val arrowVersion: String by project
val guavaVersion: String by project
val junitVersion: String by project
val kotlinVersion: String by project
val logbackVersion: String by project
val slf4JVersion: String by project

plugins {
    kotlin("jvm") version "1.9.21"
}

group = "tech.ixirsii"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    // Arrow
    implementation("io.arrow-kt:arrow-core:$arrowVersion")
    // Google Guava
    implementation("com.google.guava:guava:$guavaVersion")
    // Logback - SLF4J implementation
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    // SLF4J
    implementation("org.slf4j:slf4j-api:$slf4JVersion")

    // JUnit testing framework
    testImplementation("org.junit.jupiter:junit-jupiter:$junitVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-params:$junitVersion")
    // Kotlin test
    testImplementation("org.jetbrains.kotlin:kotlin-test:$kotlinVersion")
}

kotlin {
    jvmToolchain(21)
}

tasks.test {
    useJUnitPlatform()
}
