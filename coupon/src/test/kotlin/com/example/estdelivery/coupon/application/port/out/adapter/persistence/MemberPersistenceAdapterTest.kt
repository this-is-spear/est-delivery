package com.example.estdelivery.coupon.application.port.out.adapter.persistence

import com.example.estdelivery.coupon.application.port.out.adapter.persistence.mapper.fromCouponMember
import com.example.estdelivery.coupon.application.port.out.adapter.persistence.repository.MemberRepository
import com.example.estdelivery.coupon.domain.fixture.나눠준_비율_할인_쿠폰
import com.example.estdelivery.coupon.domain.fixture.일건창
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.util.Optional

class MemberPersistenceAdapterTest : FreeSpec({

    lateinit var memberRepository: MemberRepository
    lateinit var memberPersistenceAdapter: MemberPersistenceAdapter

    beforeTest {
        memberRepository = mockk<MemberRepository>()
        memberPersistenceAdapter = MemberPersistenceAdapter(memberRepository)
    }

    "findById" - {
        "회원을 찾는다." {
            // given
            val memberId = 1L
            val memberEntity = fromCouponMember(일건창())

            // when
            every { memberRepository.findById(memberId) } returns Optional.of(memberEntity)
            val member = memberPersistenceAdapter.findMemberCouponById(memberId)

            // then
            member shouldBe 일건창()
        }

        "회원이 존재하지 않으면 예외가 발생한다." {
            // given
            val memberId = 1L

            // when
            every { memberRepository.findById(memberId) } returns Optional.empty()

            // then
            shouldThrow<IllegalArgumentException> {
                memberPersistenceAdapter.findMemberCouponById(memberId)
            }
        }
    }

    "update" - {
        "회원을 수정한다." {
            // given
            val member = 일건창()
            // when
            member.receiveCoupon(나눠준_비율_할인_쿠폰)

            every { memberRepository.save(any()) } returns fromCouponMember(member)

            memberPersistenceAdapter.update(member)

            // then
            verify(exactly = 1) { memberRepository.save(any()) }
        }
    }
})
