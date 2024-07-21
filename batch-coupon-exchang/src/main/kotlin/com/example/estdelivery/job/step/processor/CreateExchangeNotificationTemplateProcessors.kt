package com.example.estdelivery.job.step.processor

import com.example.estdelivery.client.MemberClient
import com.example.estdelivery.domain.CouponExchangeNotificationTemplate
import com.example.estdelivery.domain.MemberCoupon
import com.example.estdelivery.job.step.service.CouponService
import com.example.estdelivery.job.step.service.ExchangedCouponIdStorage
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.item.ItemProcessor
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CreateExchangeNotificationTemplateProcessors {

    @Bean
    @StepScope
    fun createExchangeNotificationTemplateProcessor(
        memberClient: MemberClient,
        couponService: CouponService,
        exchangedCouponIdStorage: ExchangedCouponIdStorage,
        @Value("#{jobParameters['expiredCouponId']}") expiredCouponId: Long,
    ): ItemProcessor<MemberCoupon, CouponExchangeNotificationTemplate> =
        ItemProcessor { memberCoupon ->
            val memberResponse = memberClient.findMemberById(memberCoupon.memberId)
            val couponNameAfterExchange = couponService.findCouponById(exchangedCouponIdStorage.couponIdAfterExchange).name
            val couponNameBeforeExchange = couponService.findCouponById(expiredCouponId).name

            CouponExchangeNotificationTemplate(
                receivingPhoneNumber = memberResponse.phone,
                receivingName = memberResponse.name,
                couponNameBeforeExchange = couponNameBeforeExchange,
                couponNameAfterExchange = couponNameAfterExchange,
            )
        }
}
