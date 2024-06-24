package com.example.estdelivery.job.step.reader

import com.example.estdelivery.domain.MemberCoupon
import com.example.estdelivery.domain.UNUSED_MEMBER_COUPON_FIND_ALL
import jakarta.persistence.EntityManagerFactory
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.item.database.JpaCursorItemReader
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder
import org.springframework.batch.item.database.orm.JpaNamedQueryProvider
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

private const val MEMBER_COUPON_READER = "MEMBER_COUPON_READER"

@Configuration
class MemberCouponReaders {

    @Bean
    @StepScope
    fun memberCouponReader(
        entityManagerFactory: EntityManagerFactory,
        @Value("#{jobParameters['expiredCouponId']}") expiredCouponId: Long,
    ): JpaCursorItemReader<MemberCoupon> {
        return JpaCursorItemReaderBuilder<MemberCoupon>()
            .name(MEMBER_COUPON_READER)
            .entityManagerFactory(entityManagerFactory)
            .parameterValues(mapOf("couponId" to expiredCouponId))
            .queryProvider(
                JpaNamedQueryProvider<MemberCoupon>().apply {
                    setNamedQuery(UNUSED_MEMBER_COUPON_FIND_ALL)
                }
            )
            .build()
    }
}
