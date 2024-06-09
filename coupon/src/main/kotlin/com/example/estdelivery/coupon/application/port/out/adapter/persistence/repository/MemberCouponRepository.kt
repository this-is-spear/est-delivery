package com.example.estdelivery.coupon.application.port.out.adapter.persistence.repository

import com.example.estdelivery.coupon.application.port.out.adapter.persistence.entity.CouponEntity
import com.example.estdelivery.coupon.application.port.out.adapter.persistence.entity.MemberCouponEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface MemberCouponRepository : JpaRepository<MemberCouponEntity, Long> {
    @Query(
        """
        SELECT mc 
        FROM MemberCouponEntity mc 
        WHERE mc.memberId = :memberId 
        AND mc.status = com.example.estdelivery.coupon.application.port.out.adapter.persistence.entity.MemberCouponUseState.UNUSED
        """
    )
    fun findMembersUnusedCoupon(memberId: Long): List<MemberCouponEntity>
    fun findByMemberIdAndCoupon(memberId: Long, coupon: CouponEntity): MemberCouponEntity?
}
