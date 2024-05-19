package com.example.estdelivery.coupon.application.port.out

import com.example.estdelivery.coupon.domain.coupon.Coupon
import com.example.estdelivery.coupon.domain.coupon.GiftCouponCode
import com.example.estdelivery.coupon.domain.coupon.GiftMessage
import com.example.estdelivery.coupon.domain.member.Member

interface CreateGiftCouponMessageStatePort {
    fun create(sender: Member, coupon: Coupon, message: String, giftCouponCode: GiftCouponCode): GiftMessage
}
