package com.example.estdelivery.coupon.application.port.out.adapter.infra

import com.example.estdelivery.coupon.application.port.out.adapter.infra.dto.MemberState
import com.example.estdelivery.coupon.domain.member.Member
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient

@Component
class MemberInfraAdapter(
    private val memberClient: RestClient,
) {
    fun findMember(memberId: Long): Member {
        return memberClient.get().apply {
            uri("/members/{memberId}", memberId)
        }.retrieve()
            .body(MemberState::class.java)?.let {
                Member(
                    id = it.id,
                    name = it.name,
                )
            } ?: throw RuntimeException("Member not found")
    }
}
