package com.example.estdelivery.member.controller

import com.example.estdelivery.member.dto.MemberResponse
import com.example.estdelivery.member.service.MemberService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class MemberController(
    private val memberService: MemberService,
) {

    @GetMapping(
        value = ["/members/{id}"],
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    fun findMember(@PathVariable id: Long): MemberResponse {
        return memberService.findMemberById(id)
    }
}
