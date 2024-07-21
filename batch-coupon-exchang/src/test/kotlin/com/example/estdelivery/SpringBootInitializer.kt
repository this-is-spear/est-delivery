package com.example.estdelivery

import org.springframework.batch.test.context.SpringBatchTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName

/**
 * `SpringBoot`가 제공하는 `auto configure`를 제공받았기 때문에 `SpringBootTest`를 추가했다.
 *  자동 설정되는 영역을 [org.springframework.test.context.junit.jupiter.SpringJUnitConfig] 애너테이션을 이용하면 구성할 수 있지만 어렵게 가지 않겠다.
 */
@SpringBatchTest
@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
class SpringBootInitializer {
    companion object {
        @Container
        @ServiceConnection
        val batchMariaDBContainer: MySQLContainer<*> = MySQLContainer(DockerImageName.parse("mysql:latest"))
            .withDatabaseName("batch")
            .withUsername("root")
            .withPassword("")
            .withInitScript("schema-mysql.sql")

        @DynamicPropertySource
        fun overrideProps(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url") { batchMariaDBContainer.jdbcUrl }
            registry.add("spring.datasource.username") { batchMariaDBContainer.username }
            registry.add("spring.datasource.password") { batchMariaDBContainer.password }
            registry.add("spring.datasource.driver-class-name") { batchMariaDBContainer.driverClassName }
        }
    }
}
