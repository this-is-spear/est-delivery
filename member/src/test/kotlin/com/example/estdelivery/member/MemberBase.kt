package com.example.estdelivery.member

import com.example.estdelivery.member.controller.BadRequestException
import com.example.estdelivery.member.controller.InternalServerErrorException
import com.example.estdelivery.member.controller.ServiceUnavailableException
import com.example.estdelivery.member.controller.TooManyRequestsException
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
            val id = firstArg<Long>()
            handleDefaultException(id)
            MemberResponse(id, "이건창")
        }

        RestAssuredMockMvc.webAppContextSetup(this.context)
    }

    private fun handleDefaultException(id: Long) {
        if (id == 1000L) {
            throw BadRequestException()
        }

        if (id == 1001L) {
            throw TooManyRequestsException()
        }

        if (id == 1100L) {
            throw InternalServerErrorException()
        }

        if (id == 1101L) {
            throw ServiceUnavailableException()
        }
    }
}
