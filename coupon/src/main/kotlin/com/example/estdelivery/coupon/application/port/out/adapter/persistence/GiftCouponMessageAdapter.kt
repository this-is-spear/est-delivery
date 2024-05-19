package com.example.estdelivery.coupon.application.port.out.adapter.persistence

import com.example.estdelivery.coupon.application.port.out.CreateGiftCouponMessageStatePort
import com.example.estdelivery.coupon.domain.coupon.Coupon
import com.example.estdelivery.coupon.domain.coupon.GiftMessage
import com.example.estdelivery.coupon.domain.member.Member
import org.springframework.stereotype.Component

@Component
class GiftCouponMessageAdapter : CreateGiftCouponMessageStatePort {
    override fun create(sender: Member, coupon: Coupon, message: String): GiftMessage {
        TODO("Not yet implemented")
    }
}
