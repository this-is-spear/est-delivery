package com.example.estdelivery.coupon.domain.member

import com.example.estdelivery.coupon.domain.coupon.Coupon

class Member(
    val id: Long,
    val name: String,
    private val unusedCouponBook: UnusedCouponBook = UnusedCouponBook(),
) {
    fun useCoupon(coupon: Coupon) {
        unusedCouponBook.useCoupon(coupon)
    }

    fun showMyCouponBook(): List<Coupon> {
        return unusedCouponBook.showUnusedCoupons()
    }

    fun receiveCoupon(coupon: Coupon) {
        unusedCouponBook.addUnusedCoupon(coupon)
    }

    fun sendCoupon(
        coupon: Coupon,
        target: Member,
    ) {
        unusedCouponBook.useCoupon(coupon)
        target.receiveCoupon(coupon)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Member

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun toString(): String {
        return "Member(id=$id, name='$name', unUsedCouponBook=$unusedCouponBook)"
    }

    fun have(unusedCouponBook: UnusedCouponBook): Member {
        return Member(id, name, unusedCouponBook)
    }
}
