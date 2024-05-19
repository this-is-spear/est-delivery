import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.2.4"
    id("io.spring.dependency-management") version "1.1.4"
    id("com.google.osdetector") version "1.7.1"
    id("maven-publish")
    kotlin("jvm") version "1.9.23"
    kotlin("plugin.allopen") version "1.9.23"
    kotlin("plugin.spring") version "1.9.23"
    kotlin("plugin.jpa") version "1.9.23"
}

allprojects {
    repositories {
        mavenCentral()
        mavenLocal()
    }
}

subprojects {
    apply {
        plugin("io.spring.dependency-management")
        plugin("org.springframework.boot")
        plugin("org.jetbrains.kotlin.jvm")
        plugin("org.jetbrains.kotlin.plugin.spring")
        plugin("org.jetbrains.kotlin.plugin.jpa")
        plugin("org.jetbrains.kotlin.plugin.allopen")
        plugin("com.google.osdetector")
        plugin("maven-publish")
    }

    group = "com.example.estdelivery"
    version = "1.0-SNAPSHOT"

    kotlin {
        jvmToolchain(17)
    }

    extra["springCloudVersion"] = "2023.0.1"

    dependencies {
        // https://mvnrepository.com/artifact/io.netty/netty-resolver-dns-native-macos
        if (osdetector.classifier == "osx-aarch_64") {
            runtimeOnly("io.netty:netty-resolver-dns-native-macos:4.1.77.Final:${osdetector.classifier}")
        }
        implementation("org.springframework.boot:spring-boot-starter-data-jpa")
        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        runtimeOnly("com.h2database:h2")
        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testImplementation("org.junit.jupiter", "junit-jupiter", "5.8.2")
        testImplementation("org.assertj", "assertj-core", "3.22.0")
        testImplementation("io.kotest", "kotest-runner-junit5", "5.4.0")
        testImplementation("io.kotest", "kotest-property", "5.4.0")
        testImplementation("io.mockk:mockk:1.13.9")
        testImplementation("com.ninja-squad:springmockk:4.0.2")
    }

    dependencyManagement {
        imports {
            mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
        }
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs += "-Xjsr305=strict"
            jvmTarget = "17"
        }
    }

    tasks.test {
        useJUnitPlatform()
    }

    allOpen {
        annotation("jakarta.persistence.Entity")
        annotation("jakarta.persistence.Embeddable")
        annotation("jakarta.persistence.MappedSuperclass")
    }
}
