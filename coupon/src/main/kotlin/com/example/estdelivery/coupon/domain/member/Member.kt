package com.example.estdelivery.coupon.domain.member

import com.example.estdelivery.coupon.domain.coupon.Coupon

class Member(
    val id: Long,
    val name: String,
    private var unusedCouponBook: UnusedCouponBook = UnusedCouponBook(),
) {
    fun useCoupon(coupon: Coupon) {
        unusedCouponBook.removeUsedCoupon(coupon)
    }

    fun showMyCouponBook(): List<Coupon> {
        return unusedCouponBook.showUnusedCoupons()
    }

    fun receiveCoupon(coupon: Coupon) {
        unusedCouponBook.addUnusedCoupon(coupon)
    }

    fun hasCoupon(coupon: Coupon): Boolean {
        return unusedCouponBook.showUnusedCoupons().contains(coupon)
    }

    fun sendCoupon(
        coupon: Coupon,
        target: Member,
    ) {
        unusedCouponBook.removeUsedCoupon(coupon)
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

    fun have(membersCoupon: Member): Member {
        return Member(id, name, membersCoupon.unusedCouponBook)
    }
}
