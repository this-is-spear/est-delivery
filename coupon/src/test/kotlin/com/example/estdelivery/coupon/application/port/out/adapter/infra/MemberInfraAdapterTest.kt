package com.example.estdelivery.coupon.application.port.out.adapter.infra

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.HttpServerErrorException


@SpringBootTest
@AutoConfigureStubRunner(
    stubsMode = StubRunnerProperties.StubsMode.LOCAL,
    ids = ["com.example.estdelivery.member:member:1.0-SNAPSHOT:8081"]
)
class MemberInfraAdapterTest(
    @Autowired
    private val memberInfraAdapter: MemberInfraAdapter
) {

    @Test
    fun `회원 이름을 찾는다`() {
        val member = memberInfraAdapter.findMember(1L)
        assertThat(member.id).isEqualTo(1L)
        assertThat(member.name).isEqualTo("이건창")
    }

    @Test
    fun `1000번 식별자를 조회하면 400 에러가 발생한다`() {
        assertThrows<HttpClientErrorException.BadRequest> {
            memberInfraAdapter.findMember(1000)
        }
    }

    @Test
    fun `1001번 식별자를 조회하면 429 에러가 발생한다`() {
        assertThrows<HttpClientErrorException.TooManyRequests> {
            memberInfraAdapter.findMember(1001)
        }
    }

    @Test
    fun `1100번 식별자를 조회하면 500 에러가 발생한다`() {
        assertThrows<HttpServerErrorException.InternalServerError> {
            memberInfraAdapter.findMember(1100)
        }
    }

    @Test
    fun `1101번 식별자를 조회하면 503 에러가 발생한다`() {
        assertThrows<HttpServerErrorException.ServiceUnavailable> {
            memberInfraAdapter.findMember(1101)
        }
    }
}
