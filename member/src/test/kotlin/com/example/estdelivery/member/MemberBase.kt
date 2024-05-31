package com.example.estdelivery.member

import com.example.estdelivery.member.dto.MemberResponse
import com.example.estdelivery.member.service.MemberService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.slot
import io.restassured.module.mockmvc.RestAssuredMockMvc
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.web.context.WebApplicationContext

@SpringBootTest
open class MemberBase {
    @Autowired
    lateinit var context: WebApplicationContext

    @MockkBean
    lateinit var memberService: MemberService

    @BeforeEach
    fun setup() {
        every { memberService.findMemberById(any()) } answers {
            val capturedId = firstArg<Long>()
            if (capturedId % 2 == 0L) {
                throw IllegalArgumentException("Invalid id")
            }
            MemberResponse(capturedId, "이건창")
        }

        RestAssuredMockMvc.webAppContextSetup(this.context)
    }
}
