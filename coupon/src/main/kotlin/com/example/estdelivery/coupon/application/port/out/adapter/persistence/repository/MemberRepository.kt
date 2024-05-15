package com.example.estdelivery.coupon.application.port.out.adapter.persistence.repository

import com.example.estdelivery.coupon.application.port.out.adapter.persistence.entity.MemberCouponEntity
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository :
    JpaRepository<MemberCouponEntity, Long>
