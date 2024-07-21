package com.example.estdelivery

import com.example.estdelivery.domain.CouponStateAmountType
import io.kotest.matchers.shouldBe
import jakarta.persistence.EntityManagerFactory
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.batch.core.ExitStatus.COMPLETED
import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParameter
import org.springframework.batch.core.JobParameters
import org.springframework.batch.test.JobLauncherTestUtils
import org.springframework.beans.factory.annotation.Autowired

class ExchangeCronjobTest(
    @Autowired
    private val jobLauncherTestUtils: JobLauncherTestUtils,
    @Autowired
    private val exchangeCouponJob: Job,
    @Autowired
    private val entityManagerFactory: EntityManagerFactory,
) : SpringBootInitializer() {

    @BeforeEach
    fun setUp() {
        val entityManager = entityManagerFactory.createEntityManager()
        repeat(30) {
            entityManager.transaction.begin()
            entityManager.persist(couponBuilder.sample())
            entityManager.transaction.commit()
        }

        repeat(100) {
            entityManager.transaction.begin()
            entityManager.persist(couponMemberBuilder.sample())
            entityManager.transaction.commit()
        }

    }

    @Test
    fun exchangeCouponJobTest() {
        jobLauncherTestUtils.job = exchangeCouponJob
        val jobExecution = jobLauncherTestUtils.launchJob(
            JobParameters(
                mapOf(
                    "name" to JobParameter("보상", String::class.java),
                    "description" to JobParameter("설명", String::class.java),
                    "amountType" to JobParameter(CouponStateAmountType.FIX, CouponStateAmountType::class.java),
                    "amount" to JobParameter(1000, Int::class.java),
                    "expiredCouponId" to JobParameter(1L, Long::class.java),
                )
            )
        )
        jobExecution.exitStatus shouldBe COMPLETED
    }
}
