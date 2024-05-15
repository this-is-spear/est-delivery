package com.example.estdelivery.coupon.application.port.out.adapter.persistence.repository

import com.example.estdelivery.coupon.application.port.out.adapter.persistence.entity.MemberEntity
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<MemberEntity, Long>
