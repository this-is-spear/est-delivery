package com.example.estdelivery.coupon.application.port.out.adapter.persistence.mapper

import com.example.estdelivery.coupon.application.port.out.adapter.persistence.entity.CouponEntity
import com.example.estdelivery.coupon.application.port.out.adapter.persistence.entity.CouponStateAmountType
import com.example.estdelivery.coupon.application.port.out.adapter.persistence.entity.CouponStateAmountType.FIX
import com.example.estdelivery.coupon.application.port.out.adapter.persistence.entity.CouponStateAmountType.RATE
import com.example.estdelivery.coupon.application.port.out.adapter.persistence.entity.CouponStateType
import com.example.estdelivery.coupon.application.port.out.adapter.persistence.entity.CouponStateType.EVENT
import com.example.estdelivery.coupon.application.port.out.adapter.persistence.entity.CouponStateType.HANDOUT
import com.example.estdelivery.coupon.application.port.out.adapter.persistence.entity.CouponStateType.PUBLISHED
import com.example.estdelivery.coupon.domain.coupon.Coupon
import com.example.estdelivery.coupon.domain.coupon.CouponType.IS_EVENT
import com.example.estdelivery.coupon.domain.coupon.CouponType.IS_HAND_OUT
import com.example.estdelivery.coupon.domain.coupon.CouponType.IS_PUBLISHED

internal fun toCoupon(entity: CouponEntity): Coupon {
    return when (entity.amountType) {
        RATE ->
            Coupon.RateDiscountCoupon(
                entity.amount,
                entity.name,
                entity.description,
                getCouponType(entity.type),
                entity.id,
            )

        FIX ->
            Coupon.FixDiscountCoupon(
                entity.amount,
                entity.name,
                entity.description,
                getCouponType(entity.type),
                entity.id,
            )
    }
}

internal fun fromCoupon(coupon: Coupon): CouponEntity {
    return CouponEntity(
        coupon.name,
        coupon.description,
        getCouponStateAmountType(coupon),
        getCouponStateType(coupon),
        getCouponAmount(coupon),
        coupon.id,
    )
}

private fun getCouponAmount(coupon: Coupon): Int {
    return when (coupon) {
        is Coupon.RateDiscountCoupon -> coupon.discountRate
        is Coupon.FixDiscountCoupon -> coupon.discountAmount
    }
}

private fun getCouponStateType(coupon: Coupon): CouponStateType {
    return when (coupon.couponType) {
        IS_PUBLISHED -> PUBLISHED
        IS_HAND_OUT -> HANDOUT
        IS_EVENT -> EVENT
    }
}

private fun getCouponStateAmountType(coupon: Coupon): CouponStateAmountType {
    return when (coupon) {
        is Coupon.RateDiscountCoupon -> RATE
        is Coupon.FixDiscountCoupon -> FIX
    }
}

private fun getCouponType(type: CouponStateType) =
    when (type) {
        PUBLISHED -> IS_PUBLISHED
        HANDOUT -> IS_HAND_OUT
        EVENT -> IS_EVENT
    }
