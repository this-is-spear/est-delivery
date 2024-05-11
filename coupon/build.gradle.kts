tasks.withType<Jar> {
    enabled = true
}

dependencies {
    testImplementation("org.springframework.cloud:spring-cloud-starter-contract-stub-runner")
    testImplementation("org.springframework.cloud:spring-cloud-contract-spec-kotlin")
}
