package com.example.estdelivery.coupon.application.port.out.adapter.persistence.repository

import com.example.estdelivery.coupon.application.port.out.adapter.persistence.entity.ShopEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ShopRepository :
    JpaRepository<ShopEntity, Long>
