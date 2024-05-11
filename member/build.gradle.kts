plugins {
    id("org.springframework.cloud.contract") version "4.1.2"
}

tasks.withType<Jar> {
    enabled = true
}

dependencies {
    testImplementation("org.springframework.cloud:spring-cloud-starter-contract-verifier")
}

tasks.contractTest {
    useJUnitPlatform()
}

contracts {
}
