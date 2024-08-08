tasks.withType<Jar> {
    enabled = true
}

group = "com.example.estdelivery.coupon"
version = "1.0-SNAPSHOT"


dependencies {
    testImplementation("org.springframework.cloud:spring-cloud-starter-contract-stub-runner")
    testImplementation("org.springframework.cloud:spring-cloud-contract-spec-kotlin")
}

val dockerUsername: String? = System.getProperty("DOCKER_USERNAME")
val dockerPassword: String? = System.getProperty("DOCKER_PASSWORD")

jib {
    from {
        image = "openjdk:17.0.2-slim"
        platforms {
            platform {
                architecture = "arm64"
                os = "linux"
            }
        }
    }
    to {
        image = "geonc123/tis-${project.name}"
        auth {
            username = dockerUsername
            password = dockerPassword
        }
        tags = setOf("latest", project.version.toString().lowercase())
    }
    container {
        jvmFlags = listOf("-Xms256m", "-Xmx512m")
    }
}
