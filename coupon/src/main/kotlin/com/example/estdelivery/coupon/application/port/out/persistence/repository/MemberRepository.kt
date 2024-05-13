package com.example.estdelivery.coupon.application.port.out.persistence.repository

import com.example.estdelivery.coupon.application.port.out.persistence.entity.MemberEntity
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository :
    JpaRepository<MemberEntity, Long>
