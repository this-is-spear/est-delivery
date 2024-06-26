package com.example.estdelivery.coupon.domain.shop

import com.example.estdelivery.coupon.domain.coupon.Coupon
import com.example.estdelivery.coupon.domain.member.Member

class RoyalCustomers(
    private var customers: List<Member> = listOf(),
) {
    fun handOutCoupon(coupon: Coupon) {
        customers.filter {
            it.showMyCouponBook().contains(coupon).not()
        }.forEach { it.receiveCoupon(coupon) }
    }

    fun addRoyalCustomers(vararg members: Member) {
        for (member in members) {
            require(customers.contains(member).not()) { "이미 등록된 회원입니다." }
        }
        customers = customers + members
    }

    fun showRoyalCustomers(): List<Member> {
        return customers.toList()
    }

    override fun toString(): String {
        return "RoyalCustomers(customers=$customers)"
    }
}
