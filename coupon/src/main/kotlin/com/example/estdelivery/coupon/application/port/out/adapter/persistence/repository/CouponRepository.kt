package com.example.estdelivery.coupon.application.port.out.adapter.persistence.repository

import com.example.estdelivery.coupon.application.port.out.adapter.persistence.entity.CouponEntity
import org.springframework.data.jpa.repository.JpaRepository

interface CouponRepository :
    JpaRepository<CouponEntity, Long>
