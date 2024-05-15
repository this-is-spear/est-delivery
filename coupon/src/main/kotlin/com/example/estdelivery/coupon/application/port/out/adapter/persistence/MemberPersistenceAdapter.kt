package com.example.estdelivery.coupon.application.port.out.adapter.persistence

import com.example.estdelivery.coupon.application.port.out.adapter.persistence.entity.MemberEntity
import com.example.estdelivery.coupon.application.port.out.adapter.persistence.mapper.fromCoupon
import com.example.estdelivery.coupon.application.port.out.adapter.persistence.mapper.toCoupon
import com.example.estdelivery.coupon.application.port.out.adapter.persistence.repository.MemberRepository
import com.example.estdelivery.coupon.domain.coupon.Coupon
import com.example.estdelivery.coupon.domain.coupon.CouponBook
import com.example.estdelivery.coupon.domain.member.UnusedCouponBook
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class MemberPersistenceAdapter(
    private val memberRepository: MemberRepository
) {
    /**
     * 회원이 사용하지 않은 쿠폰 정보를 조회한다. 회원 정보가 없는 경우 회원 정보를 새롭게 추가한다.
     */
    @Transactional(readOnly = true)
    fun findUnusedCouponByMemberId(memberId: Long) =
        memberRepository.findByIdOrNull(memberId)?.let { memberEntity ->
            memberEntity.unusedCoupons.map {
                toCoupon(it)
            }.let {
                UnusedCouponBook(CouponBook(it))
            }
        } ?: memberRepository.save(
            MemberEntity(
                emptyList(),
                memberId
            )
        ).let {
            UnusedCouponBook(CouponBook(emptyList()))
        }

    @Transactional
    fun updateUnusedCouponBook(memberId: Long, unusedCoupons: List<Coupon>) {
        val memberEntity = memberRepository.findByIdOrNull(memberId) ?: return
        memberEntity.unusedCoupons = unusedCoupons
            .map {
                fromCoupon(it)
            }.toList()
    }
}
