package com.example.estdelivery.coupon.application.port.out.adapter.persistence

import com.example.estdelivery.coupon.application.port.out.adapter.persistence.mapper.fromCouponMember
import com.example.estdelivery.coupon.application.port.out.adapter.persistence.mapper.toMember
import com.example.estdelivery.coupon.application.port.out.adapter.persistence.repository.MemberRepository
import com.example.estdelivery.coupon.domain.member.Member
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class MemberPersistenceAdapter(
    private val memberRepository: MemberRepository,
) {
    fun findMemberCouponById(memberId: Long): Member {
        return toMember(
            memberRepository.findByIdOrNull(memberId)
                ?: throw IllegalArgumentException("Member not found")
        )
    }

    @Transactional
    fun update(member: Member) {
        memberRepository.save(fromCouponMember(member))
    }
}
