package com.example.estdelivery.coupon.application.port.out.adapter.persistence

import com.example.estdelivery.coupon.application.port.out.adapter.persistence.entity.MemberCouponEntity
import com.example.estdelivery.coupon.application.port.out.adapter.persistence.entity.MemberCouponUseState
import com.example.estdelivery.coupon.application.port.out.adapter.persistence.mapper.fromCoupon
import com.example.estdelivery.coupon.application.port.out.adapter.persistence.repository.MemberCouponRepository
import com.example.estdelivery.coupon.domain.fixture.나눠준_비율_할인_쿠폰
import com.example.estdelivery.coupon.domain.fixture.일건창
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

class MemberPersistenceAdapterTest : FreeSpec({
    val memberCouponRepository = mockk<MemberCouponRepository>()
    lateinit var memberPersistenceAdapter: MemberPersistenceAdapter

    beforeTest {
        memberPersistenceAdapter = MemberPersistenceAdapter(memberCouponRepository)
    }

    "findById" - {
        "회원이 가진 사용하지 않은 쿠폰을 찾는다." {
            // given
            val memberId = 1L
            val unusedCouponEntity = fromCoupon(나눠준_비율_할인_쿠폰)

            // when
            every { memberCouponRepository.findMembersUnusedCoupon(memberId) } returns listOf(
                MemberCouponEntity(
                    unusedCouponEntity,
                    memberId,
                    MemberCouponUseState.UNUSED,
                    1L
                )
            )
            val unusedCouponBook = memberPersistenceAdapter.findUnusedCouponByMemberId(memberId)

            // then
            unusedCouponBook.showUnusedCoupons().size shouldBe 1
        }
    }

    "update" - {
        "회원이 가진 사용하지 않은 쿠폰을 수정한다." {
            // given
            val member = 일건창()
            // when
            member.receiveCoupon(나눠준_비율_할인_쿠폰)
            val unusedCouponEntity = fromCoupon(나눠준_비율_할인_쿠폰)

            every { memberCouponRepository.findByMemberIdAndCoupon(member.id, unusedCouponEntity) }
            memberPersistenceAdapter.updateUnusedCouponBook(member.id, member.showMyCouponBook())
        }
    }
})
