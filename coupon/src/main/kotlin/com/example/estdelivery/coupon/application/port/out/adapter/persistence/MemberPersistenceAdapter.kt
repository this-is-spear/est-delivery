package com.example.estdelivery.coupon.application.port.out.adapter.persistence

import com.example.estdelivery.coupon.application.port.out.adapter.persistence.entity.UnusedCouponEntity
import com.example.estdelivery.coupon.application.port.out.adapter.persistence.mapper.fromCoupon
import com.example.estdelivery.coupon.application.port.out.adapter.persistence.mapper.toCoupon
import com.example.estdelivery.coupon.application.port.out.adapter.persistence.repository.UnusedCouponRepository
import com.example.estdelivery.coupon.domain.coupon.Coupon
import com.example.estdelivery.coupon.domain.coupon.CouponBook
import com.example.estdelivery.coupon.domain.member.UnusedCouponBook
import jakarta.transaction.Transactional
import org.springframework.stereotype.Component

@Component
class MemberPersistenceAdapter(
    private val unusedCouponRepository: UnusedCouponRepository,
) {
    fun findMemberCouponByMemberId(memberId: Long) =
        unusedCouponRepository.findAllByMemberId(memberId)
            .map {
                toCoupon(it.coupon)
            }.let {
                UnusedCouponBook(CouponBook(it))
            }

    @Transactional
    fun updateUnusedCouponBook(memberId: Long, unusedCoupons: List<Coupon>) {
        val collectedCoupons = unusedCouponRepository.findAllByMemberId(memberId)
            .map { toCoupon(it.coupon) }
            .toSet()

        unusedCoupons.filter { !collectedCoupons.contains(it) }
            .map {
                UnusedCouponEntity(memberId, fromCoupon(it))
            }.let {
                unusedCouponRepository.saveAll(it)
            }
    }
}
