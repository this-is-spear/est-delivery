package com.example.estdelivery.coupon.domain.coupon

sealed class Coupon(
    open val name: String,
    open val description: String,
    internal val couponType: CouponType,
    internal val id: Long = 0,
) {
    class RateDiscountCoupon(
        val discountRate: Int,
        override val name: String,
        override val description: String,
        couponType: CouponType,
        id: Long = 0,
    ) : Coupon(name, description, couponType, id)

    class FixDiscountCoupon(
        val discountAmount: Int,
        override val name: String,
        override val description: String,
        couponType: CouponType,
        id: Long = 0,
    ) : Coupon(name, description, couponType, id)

    class UsedCoupon(
        val coupon: Coupon,
    ) : Coupon(coupon.name, coupon.description, coupon.couponType, coupon.id)

    fun isPublished(): Boolean {
        return couponType == CouponType.IS_PUBLISHED
    }

    fun isHandOut(): Boolean {
        return couponType == CouponType.IS_HAND_OUT
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Coupon

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun toString(): String {
        return "Coupon(name='$name', description='$description', couponType=$couponType, id=$id)"
    }
}
