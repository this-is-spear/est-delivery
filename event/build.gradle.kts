import org.springframework.cloud.contract.verifier.config.TestFramework.JUNIT5

plugins {
    id("org.springframework.cloud.contract") version "4.1.2"
}

group = "com.example.estdelivery.event"
version = "1.0-SNAPSHOT"

tasks.withType<Jar> {
    enabled = true
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    testImplementation("org.springframework.cloud:spring-cloud-starter-contract-verifier")
    testImplementation("org.springframework.cloud:spring-cloud-contract-spec-kotlin")
}

tasks.contractTest {
    useJUnitPlatform()
}

contracts {
    contractsDslDir.set(file("src/test/resources/contracts"))
    testFramework.set(JUNIT5)
    packageWithBaseClasses.set("com.example.estdelivery.event")
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            artifact(tasks.named("verifierStubsJar"))
        }
    }
}

tasks.withType<Delete> {
    doFirst {
        delete("~/.m2/repository/com/example/estdelivery/event")
    }
}