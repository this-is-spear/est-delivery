package com.example.estdelivery.reader

import com.example.estdelivery.entity.CouponEntity
import jakarta.persistence.EntityManagerFactory
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder
import org.springframework.batch.item.database.orm.JpaNamedQueryProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CouponReaders {
    @Bean
    fun couponReader(
        entityManagerFactory: EntityManagerFactory,
    ) = JpaCursorItemReaderBuilder<CouponEntity>()
        .name("EXCHANGE_COUPON_READER")
        .entityManagerFactory(entityManagerFactory)
        .queryProvider(
            JpaNamedQueryProvider<CouponEntity>().apply {
                setNamedQuery("couponFinaAll")
                setEntityClass(CouponEntity::class.java)
            }
        )
        .build()
}
