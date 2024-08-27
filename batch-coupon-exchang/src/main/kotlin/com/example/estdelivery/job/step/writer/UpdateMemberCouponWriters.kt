package com.example.estdelivery.job.step.writer

import com.example.estdelivery.domain.MemberCoupon
import jakarta.persistence.EntityManagerFactory
import org.springframework.batch.item.database.JpaItemWriter
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UpdateMemberCouponWriters {
    @Bean
    fun updateMemberCouponWriter(
        entityManagerFactory: EntityManagerFactory
    ): JpaItemWriter<MemberCoupon> {
        return JpaItemWriterBuilder<MemberCoupon>()
            .entityManagerFactory(entityManagerFactory)
            .build()
    }
}
