package com.example.estdelivery.coupon.application.port.out.adapter.persistence

import com.example.estdelivery.coupon.application.port.out.adapter.persistence.entity.MemberCouponEntity
import com.example.estdelivery.coupon.application.port.out.adapter.persistence.entity.MemberCouponUseState
import com.example.estdelivery.coupon.application.port.out.adapter.persistence.mapper.fromCoupon
import com.example.estdelivery.coupon.application.port.out.adapter.persistence.mapper.toCoupon
import com.example.estdelivery.coupon.application.port.out.adapter.persistence.repository.MemberCouponRepository
import com.example.estdelivery.coupon.domain.coupon.Coupon
import com.example.estdelivery.coupon.domain.coupon.CouponBook
import com.example.estdelivery.coupon.domain.member.UnusedCouponBook
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class MemberPersistenceAdapter(
    private val memberCouponRepository: MemberCouponRepository,
) {
    /**
     * 회원이 사용하지 않은 쿠폰 정보를 조회한다. 회원 정보가 없는 경우 회원 정보를 새롭게 추가한다.
     * `asSequence`를 이용해 사용자의 쿠폰 리스트를 중간마다 버퍼링하지 않고 최종적으로 버퍼링을 진행한다.
     */
    @Transactional(readOnly = true)
    fun findUnusedCouponByMemberId(memberId: Long) =
        memberCouponRepository.findMembersUnusedCoupon(memberId)
            .asSequence()
            .map { it.coupon }
            .map { toCoupon(it) }
            .let { UnusedCouponBook(CouponBook(it.toList())) }

    /**
     * 추가된 쿠폰을 추가하거나 사용한 쿠폰만 찾아 상태를 변경한다.
     * `dirty checking`을 사용했으니 주의하자.
     */
    @Transactional
    fun updateUnusedCouponBook(memberId: Long, unusedCoupons: List<Coupon>) {
        unusedCoupons
            .map {
                memberCouponRepository.findByMemberIdAndCoupon(memberId, fromCoupon(it))
                    ?: memberCouponRepository.save(
                        MemberCouponEntity(
                            fromCoupon(it),
                            memberId,
                            MemberCouponUseState.UNUSED
                        )
                    )
            }
            .filterIsInstance<Coupon.UsedCoupon>()
            .map { memberCouponRepository.findByMemberIdAndCoupon(memberId, fromCoupon(it)) }
            .map { it!!.status = MemberCouponUseState.USED }
    }
}
