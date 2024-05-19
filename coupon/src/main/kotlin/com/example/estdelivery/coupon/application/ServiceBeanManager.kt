package com.example.estdelivery.coupon.application

import com.example.estdelivery.coupon.application.port.`in`.FindAvailableGiftCouponUseCase
import com.example.estdelivery.coupon.application.port.`in`.GiftCouponByMessageUseCase
import com.example.estdelivery.coupon.application.port.out.CreateGiftCouponMessageStatePort
import com.example.estdelivery.coupon.application.port.out.LoadMemberStatePort
import com.example.estdelivery.coupon.application.port.out.UpdateMemberStatePort
import com.example.estdelivery.coupon.application.utils.TransactionArea
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ServiceBeanManager {
    @Bean
    fun findAvailableGiftCouponUseCase(loadMemberStatePort: LoadMemberStatePort): FindAvailableGiftCouponUseCase =
        FindAvailableGiftCouponService(loadMemberStatePort)

    @Bean
    fun giftCouponByMessageUseCase(
        transactionArea: TransactionArea,
        loadMemberStatePort: LoadMemberStatePort,
        createGiftCouponMessageStatePort: CreateGiftCouponMessageStatePort,
        updateMemberStatePort: UpdateMemberStatePort,
    ): GiftCouponByMessageUseCase = GiftCouponByMessageService(
        loadMemberStatePort,
        createGiftCouponMessageStatePort,
        updateMemberStatePort,
        transactionArea,
    )
}