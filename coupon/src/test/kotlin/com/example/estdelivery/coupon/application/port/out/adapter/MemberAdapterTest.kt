package com.example.estdelivery.coupon.application.port.out.adapter

import com.example.estdelivery.coupon.application.port.out.adapter.persistence.entity.MemberCouponEntity
import com.example.estdelivery.coupon.application.port.out.adapter.persistence.entity.MemberCouponUseState
import com.example.estdelivery.coupon.application.port.out.adapter.persistence.mapper.fromCoupon
import com.example.estdelivery.coupon.application.port.out.adapter.persistence.mapper.toCoupon
import com.example.estdelivery.coupon.application.port.out.adapter.persistence.repository.CouponRepository
import com.example.estdelivery.coupon.application.port.out.adapter.persistence.repository.MemberCouponRepository
import com.example.estdelivery.coupon.domain.coupon.Coupon
import com.example.estdelivery.coupon.domain.coupon.CouponType
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.BeforeEach
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
class MemberAdapterTest(
    @Autowired
    private val memberAdapter: MemberAdapter,
    @Autowired
    private val memberCouponRepository: MemberCouponRepository,
    @Autowired
    private val couponRepository: CouponRepository,
) {
    @BeforeEach
    fun setUp() {
        memberCouponRepository.deleteAll()
        couponRepository.deleteAll()
    }

    @Test
    fun `회원을 조회한다`() {
        // given
        val 할인_쿠폰 = couponRepository.save(
            fromCoupon(
                Coupon.RateDiscountCoupon(
                    10, "10% 할인 쿠폰", "[USER_ID: 1][발급된 10% 할인 쿠폰]", CouponType.IS_PUBLISHED
                )
            )
        )
        val 회원_식별자 = 1L
        memberCouponRepository.save(MemberCouponEntity(할인_쿠폰, 회원_식별자, MemberCouponUseState.UNUSED))

        // when
        val member = memberAdapter.findMember(회원_식별자)

        // then
        member.id shouldBe 회원_식별자
        member.showMyCouponBook().size shouldBe 1
    }

    @Test
    fun `회원이 가진 쿠폰 정보를 수정한다`() {
        // given
        val 할인_쿠폰 = couponRepository.save(
            fromCoupon(
                Coupon.RateDiscountCoupon(
                    10, "10% 할인 쿠폰", "[USER_ID: 1][발급된 10% 할인 쿠폰]", CouponType.IS_PUBLISHED
                )
            )
        )

        val 회원_식별자 = 1L

        // when
        val member = memberAdapter.findMember(회원_식별자)
        val previousCouponSize = member.showMyCouponBook().size
        member.receiveCoupon(toCoupon(할인_쿠폰))
        memberAdapter.updateMembersCoupon(member)
        val updatedMember = memberAdapter.findMember(1)
        val afterCouponSize = updatedMember.showMyCouponBook().size

        // then
        previousCouponSize shouldBe 0
        afterCouponSize shouldBe 1
    }
}
