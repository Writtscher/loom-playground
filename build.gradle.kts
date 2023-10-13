plugins {
    id("java")
}

group = "org.playground.java21"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("ch.qos.logback:logback-core:1.4.11")
    implementation("ch.qos.logback:logback-classic:1.4.11")
    implementation("org.slf4j:slf4j-api:2.0.9")

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks {
    withType<JavaCompile> {
        // Activate Java 21 Preview
        options.compilerArgs.add("--enable-preview")
    }
    test {
        useJUnitPlatform()
    }
}