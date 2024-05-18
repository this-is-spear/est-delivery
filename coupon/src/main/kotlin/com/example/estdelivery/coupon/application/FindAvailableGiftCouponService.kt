package com.example.estdelivery.coupon.application

import com.example.estdelivery.coupon.application.port.`in`.FindAvailableGiftCouponUseCase
import com.example.estdelivery.coupon.application.port.out.LoadMemberStatePort
import com.example.estdelivery.coupon.domain.coupon.Coupon
import com.example.estdelivery.coupon.domain.coupon.GiftCoupon
import com.example.estdelivery.coupon.domain.member.Member

class FindAvailableGiftCouponService(
    loadMemberStatePort: LoadMemberStatePort,
    private val findMember: (Long) -> Member = { loadMemberStatePort.findMember(it) },
) : FindAvailableGiftCouponUseCase {
    /**
     * 1. 회원 정보를 조회한다.
     * 2. 회원이 가진 쿠폰 리스트를 조회한다.
     * 3. 선물 가능한 쿠폰을 고른다.
     * 4. 선물 가능한 쿠폰을 반환한다.
     */
    override fun findAvailableGiftCoupon(memberId: Long): List<GiftCoupon> {
        val member = findMember(memberId)
        val myCouponBook = member.showMyCouponBook()
        return myCouponBook
            .filter { isGiftAvailable(it) }
            .map { GiftCoupon(it) }
    }

    private fun isGiftAvailable(coupon: Coupon): Boolean {
        return !coupon.isPublished()
    }
}
