package com.example.estdelivery.coupon.application.port.out.adapter

import com.example.estdelivery.coupon.application.port.out.LoadMemberStatePort
import com.example.estdelivery.coupon.application.port.out.UpdateMemberStatePort
import com.example.estdelivery.coupon.application.port.out.adapter.infra.MemberInfraAdapter
import com.example.estdelivery.coupon.application.port.out.adapter.persistence.MemberPersistenceAdapter
import com.example.estdelivery.coupon.domain.member.Member
import org.springframework.stereotype.Component

@Component
class MemberAdapter(
    private val memberInfraAdapter: MemberInfraAdapter,
    private val memberPersistenceAdapter: MemberPersistenceAdapter,
): LoadMemberStatePort, UpdateMemberStatePort {
    override fun findMember(memberId: Long): Member {
        val member = memberInfraAdapter.findMember(memberId)
        val membersCoupon = memberPersistenceAdapter.findMemberCouponById(memberId)
        return member.have(membersCoupon)
    }

    override fun updateMembersCoupon(member: Member) {
        memberPersistenceAdapter.update(member)
    }
}
