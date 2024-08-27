tasks.withType<Jar> {
    enabled = true
}

group = "com.example.estdelivery.coupon"
version = "1.0-SNAPSHOT"


dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    testImplementation("org.springframework.cloud:spring-cloud-starter-contract-stub-runner")
    testImplementation("org.springframework.cloud:spring-cloud-contract-spec-kotlin")
}
