group = "com.example.estdelivery"
version = "1.0-SNAPSHOT"

tasks.withType<Jar> {
    enabled = true
}

repositories {
    mavenCentral()
}

dependencies {
    runtimeOnly("com.mysql:mysql-connector-j")
    implementation("org.springframework.boot:spring-boot-starter-batch")
    testImplementation("org.springframework.cloud:spring-cloud-starter-contract-stub-runner")
    testImplementation("org.springframework.batch:spring-batch-test")
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
    testImplementation("org.testcontainers:mysql")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("com.navercorp.fixturemonkey:fixture-monkey-starter-kotlin:1.0.16")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}
