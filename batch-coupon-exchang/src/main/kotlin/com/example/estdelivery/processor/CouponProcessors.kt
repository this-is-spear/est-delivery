package com.example.estdelivery.processor

import com.example.estdelivery.entity.CouponEntity
import org.springframework.batch.item.ItemProcessor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CouponProcessors {
    @Bean
    fun couponProcessor() = ItemProcessor<CouponEntity, CouponEntity> { coupon ->
        coupon.expire()
        return@ItemProcessor coupon
    }
}
