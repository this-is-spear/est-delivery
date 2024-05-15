package com.example.estdelivery.coupon.domain.shop

import com.example.estdelivery.coupon.domain.coupon.Coupon
import com.example.estdelivery.coupon.domain.coupon.CouponBook
import com.example.estdelivery.coupon.domain.member.Member

class Shop(
    private val publishedCoupons: PublishedCouponBook,
    private val publishedEventCoupons: PublishedEventCouponBook,
    private val handOutCouponBook: HandOutCouponBook,
    private val usedCouponBook: UsedCouponBook,
    private val royalCustomers: RoyalCustomers,
    val id: Long,
    val name: String? = null,
) {
    fun publishCoupon(coupon: Coupon) {
        publishedCoupons.publishCoupon(coupon)
    }

    fun receiveCoupon(coupon: Coupon) {
        usedCouponBook.useCoupon(
            coupon,
            CouponBook(
                publishedCoupons.showPublishedCoupons() +
                        handOutCouponBook.showHandOutCoupon() +
                        publishedEventCoupons.showEventCoupons(),
            ),
        )
    }

    fun addRoyalCustomers(vararg members: Member) {
        royalCustomers.addRoyalCustomers(*members)
    }

    fun handOutCouponToRoyalCustomers(coupon: Coupon) {
        handOutCouponBook.addHandOutCoupon(coupon)
        royalCustomers.handOutCoupon(coupon)
    }

    fun showPublishedCoupons() = publishedCoupons.showPublishedCoupons()

    fun showRoyalCustomers() = royalCustomers.showRoyalCustomers()

    fun showHandOutCoupon() = handOutCouponBook.showHandOutCoupon()

    fun showUsedCoupons() = usedCouponBook.showUsedCoupons()

    fun showEventCoupons() = publishedEventCoupons.showEventCoupons()

    fun issueCoupon(coupon: Coupon) = publishedCoupons.issueCoupon(coupon)

    fun issueEventCoupon(coupon: Coupon) = publishedEventCoupons.issueEventCoupon(coupon)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Shop

        return id == other.id
    }

    override fun hashCode() = id.hashCode()

    override fun toString(): String {
        return "Shop(publishedCoupons=$publishedCoupons, publishedEventCoupons=$publishedEventCoupons, handOutCouponBook=$handOutCouponBook, usedCouponBook=$usedCouponBook, royalCustomers=$royalCustomers, id=$id, name=$name)"
    }
}
