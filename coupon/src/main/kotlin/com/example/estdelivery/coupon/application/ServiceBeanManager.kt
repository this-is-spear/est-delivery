package com.example.estdelivery.coupon.application

import com.example.estdelivery.coupon.application.port.out.LoadMemberStatePort
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ServiceBeanManager {
    @Bean
    fun findAvailableGiftCouponService(loadMemberStatePort: LoadMemberStatePort) =
        FindAvailableGiftCouponService(loadMemberStatePort)
}
