package com.example.estdelivery.member

import com.example.estdelivery.member.controller.MemberController
import com.example.estdelivery.member.dto.MemberResponse
import com.example.estdelivery.member.service.MemberService
import io.mockk.every
import io.mockk.mockk
import io.restassured.module.mockmvc.RestAssuredMockMvc
import org.junit.jupiter.api.BeforeEach

open class MemberBase {
    @BeforeEach
    fun setup() {
        val memberService = mockk<MemberService>()
        every { memberService.findMemberById(1) } returns MemberResponse(1, "이건창")
        RestAssuredMockMvc.standaloneSetup(MemberController(memberService))
    }
}
