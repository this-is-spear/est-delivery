package com.example.estdelivery.coupon.application.port.out.adapter.persistence.repository

import com.example.estdelivery.coupon.application.port.out.adapter.persistence.entity.GiftMessageEntity
import org.springframework.data.jpa.repository.JpaRepository

interface GiftMessageRepository : JpaRepository<GiftMessageEntity, Long> {
    fun existsByEnrollCode(enrollCode: String): Boolean
}
