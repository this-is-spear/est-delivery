package com.example.estdelivery.coupon.application.port.out.adapter.persistence

import com.example.estdelivery.coupon.application.port.out.adapter.persistence.entity.UnusedCouponEntity
import com.example.estdelivery.coupon.application.port.out.adapter.persistence.mapper.fromCoupon
import com.example.estdelivery.coupon.application.port.out.adapter.persistence.repository.UnusedCouponRepository
import com.example.estdelivery.coupon.domain.fixture.나눠준_비율_할인_쿠폰
import com.example.estdelivery.coupon.domain.fixture.일건창
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot

class UnusedCouponPersistenceAdapterTest : FreeSpec({

    lateinit var unusedCouponRepository: UnusedCouponRepository
    lateinit var memberPersistenceAdapter: MemberPersistenceAdapter

    beforeTest {
        unusedCouponRepository = mockk<UnusedCouponRepository>()
        memberPersistenceAdapter = MemberPersistenceAdapter(unusedCouponRepository)
    }

    "findById" - {
        "회원이 가진 사용하지 않은 쿠폰을 찾는다." {
            // given
            val memberId = 1L
            val unusedCouponBookEntity = UnusedCouponEntity(memberId, fromCoupon(나눠준_비율_할인_쿠폰), 1)

            // when
            every { unusedCouponRepository.findAllByMemberId(memberId) } returns listOf(unusedCouponBookEntity)
            val unusedCouponBook = memberPersistenceAdapter.findMemberCouponByMemberId(memberId)

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
            val savedCouponEntity = slot<List<UnusedCouponEntity>>()

            every { unusedCouponRepository.findAllByMemberId(any()) } returns emptyList()
            every { unusedCouponRepository.saveAll(capture(savedCouponEntity)) } returns listOf(mockk())

            memberPersistenceAdapter.updateUnusedCouponBook(member.id, member.showMyCouponBook())
            savedCouponEntity.captured.size shouldBe 1
        }

        "새롭게 등록하는 쿠폰만 등록한다" {
            // given
            val member = 일건창()
            member.receiveCoupon(나눠준_비율_할인_쿠폰)
            val savedCouponEntity = slot<List<UnusedCouponEntity>>()

            every { unusedCouponRepository.findAllByMemberId(member.id) } returns listOf(
                UnusedCouponEntity(member.id, fromCoupon(나눠준_비율_할인_쿠폰), 1)
            )
            every { unusedCouponRepository.saveAll(capture(savedCouponEntity)) } returns listOf(mockk())

            // when
            memberPersistenceAdapter.updateUnusedCouponBook(member.id, member.showMyCouponBook())

            // then
            savedCouponEntity.captured.size shouldBe 0
        }
    }
})
