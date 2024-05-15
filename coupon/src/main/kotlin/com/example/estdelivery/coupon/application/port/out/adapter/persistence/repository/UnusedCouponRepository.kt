package com.example.estdelivery.coupon.application.port.out.adapter.persistence.repository

import com.example.estdelivery.coupon.application.port.out.adapter.persistence.entity.UnusedCouponEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UnusedCouponRepository : JpaRepository<UnusedCouponEntity, Long> {
    fun findAllByMemberId(memberId: Long): List<UnusedCouponEntity>
}
