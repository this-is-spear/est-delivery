package com.example.estdelivery.job.step.service.repository

import com.example.estdelivery.domain.Coupon
import org.springframework.data.jpa.repository.JpaRepository

interface CouponRepository : JpaRepository<Coupon, Long>