package com.example.estdelivery.coupon.application.port.out.persistence.repository

import com.example.estdelivery.coupon.application.port.out.persistence.entity.CouponEntity
import org.springframework.data.jpa.repository.JpaRepository

interface CouponRepository :
    JpaRepository<CouponEntity, Long>
