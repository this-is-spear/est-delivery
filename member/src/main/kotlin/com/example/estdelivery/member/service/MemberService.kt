package com.example.estdelivery.member.service

import com.example.estdelivery.member.dto.MemberResponse
import com.example.estdelivery.member.repository.MemberRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class MemberService(
    private val memberRepository: MemberRepository,
) {
    fun findMemberById(id: Long): MemberResponse {
        val member = memberRepository.findByIdOrNull(id)
            ?: throw IllegalArgumentException("Member not found")
        check(member.id == id) { "Member id mismatch" }
        return MemberResponse(id, member.name)
    }
}
