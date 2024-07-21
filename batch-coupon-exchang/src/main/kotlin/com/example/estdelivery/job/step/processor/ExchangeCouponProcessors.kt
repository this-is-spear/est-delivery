package com.example.estdelivery.job.step.processor

import com.example.estdelivery.domain.MemberCoupon
import com.example.estdelivery.domain.MemberCouponUseState
import com.example.estdelivery.job.step.service.ExchangedCouponIdStorage
import org.springframework.batch.item.ItemProcessor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ExchangeCouponProcessors {

    @Bean
    fun exchangeCouponProcessor(
        exchangedCouponIdStorage: ExchangedCouponIdStorage,
    ): ItemProcessor<MemberCoupon, MemberCoupon> {
        return ItemProcessor { memberCoupon ->
            if (memberCoupon.status != MemberCouponUseState.UNUSED) {
                return@ItemProcessor null
            }
            memberCoupon.exchange(exchangedCouponIdStorage.couponIdAfterExchange)
        }
    }
}
