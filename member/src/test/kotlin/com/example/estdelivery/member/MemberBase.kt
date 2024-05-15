package com.example.estdelivery.member

import com.example.estdelivery.member.controller.MemberController
import com.example.estdelivery.member.dto.MemberResponse
import com.example.estdelivery.member.service.MemberService
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.restassured.module.mockmvc.RestAssuredMockMvc
import org.junit.jupiter.api.BeforeEach

open class MemberBase {
    @BeforeEach
    fun setup() {
        val memberService = mockk<MemberService>()
        val id = slot<Long>()
        every { memberService.findMemberById(capture(id)) } answers { MemberResponse(id.captured, "이건창") }
        RestAssuredMockMvc.standaloneSetup(MemberController(memberService))
    }
}
