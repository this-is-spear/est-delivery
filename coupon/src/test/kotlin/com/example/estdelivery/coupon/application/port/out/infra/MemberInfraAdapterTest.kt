package com.example.estdelivery.coupon.application.port.out.infra

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties


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
}
