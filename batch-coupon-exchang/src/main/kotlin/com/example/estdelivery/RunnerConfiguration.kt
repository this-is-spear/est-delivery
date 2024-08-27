package com.example.estdelivery

import org.springframework.batch.core.explore.JobExplorer
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.batch.core.repository.JobRepository
import org.springframework.boot.autoconfigure.batch.BatchProperties
import org.springframework.boot.autoconfigure.batch.JobLauncherApplicationRunner
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.util.StringUtils

/**
 * CommandLineRunner 를 이용하면 spring boot 가 제공하는 기본 기능을 이용할 수 없다.
 * 예를 들어 JPA 관련 설정 DB 관련 설정이다. 이런 auto configure 가 필요없다면 해당 클래스를 직접 생성하지 않아도 된다.
 */
@Configuration
@EnableConfigurationProperties(BatchProperties::class)
class RunnerConfiguration {

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "spring.batch.job", name = ["enabled"])
    fun jobLauncherApplicationRunner(
        jobLauncher: JobLauncher,
        jobExplorer: JobExplorer,
        jobRepository: JobRepository,
        properties: BatchProperties
    ): JobLauncherApplicationRunner {
        val runner = JobLauncherApplicationRunner(jobLauncher, jobExplorer, jobRepository)
        val jobNames = properties.job.name
        if (StringUtils.hasText(jobNames)) {
            runner.setJobName(jobNames)
        }
        return runner
    }
}
