package com.example.estdelivery

import org.springframework.batch.test.context.SpringBatchTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName

@SpringBatchTest
@SpringBootTest
@Testcontainers
class SpringBootInitializer {
    companion object {
        @Container
        @ServiceConnection
        val batchMariaDBContainer: MySQLContainer<*> = MySQLContainer(DockerImageName.parse("mysql:latest"))
            .withDatabaseName("coupon")
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
