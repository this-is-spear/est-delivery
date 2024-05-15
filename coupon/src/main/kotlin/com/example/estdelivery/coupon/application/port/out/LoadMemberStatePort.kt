package com.example.estdelivery.coupon.application.port.out

import com.example.estdelivery.coupon.domain.member.Member

interface LoadMemberStatePort {
    fun findMember(memberId: Long): Member
}
