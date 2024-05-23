package com.example.estdelivery.coupon.application.port.out.adapter.persistence

import com.example.estdelivery.coupon.application.port.out.adapter.persistence.entity.EnrollTermEntity
import com.example.estdelivery.coupon.application.port.out.adapter.persistence.entity.GiftMessageEntity
import com.example.estdelivery.coupon.application.port.out.adapter.persistence.mapper.fromCoupon
import com.example.estdelivery.coupon.application.port.out.adapter.persistence.repository.EnrollDateRepository
import com.example.estdelivery.coupon.application.port.out.adapter.persistence.repository.GiftMessageRepository
import com.example.estdelivery.coupon.domain.coupon.GiftCouponCode
import com.example.estdelivery.coupon.domain.fixture.나눠준_비율_할인_쿠폰
import com.example.estdelivery.coupon.domain.fixture.일건창
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import org.junit.jupiter.api.assertAll

class GiftCouponMessageAdapterTest : FreeSpec({
    val giftMessageRepository = mockk<GiftMessageRepository>()
    val enrollDateRepository = mockk<EnrollDateRepository>()

    lateinit var giftCouponMessageAdapter: GiftCouponMessageAdapter

    beforeTest {
        giftCouponMessageAdapter = GiftCouponMessageAdapter(giftMessageRepository, enrollDateRepository)
    }

    val now = LocalDate.now()

    "쿠폰 선물 메시지를 저장한다." {
        // given
        val sender = 일건창()
        val coupon = 나눠준_비율_할인_쿠폰
        val message = "선물 메시지입니다."
        val giftCouponCode = GiftCouponCode.create()
        val enrollTerm = EnrollTermEntity(
            term = 6,
            unit = ChronoUnit.MONTHS
        )

        // when
        every { enrollDateRepository.findLatestPolicy() } returns enrollTerm
        every { giftMessageRepository.save(any()) } returns GiftMessageEntity(
            sender = sender.id,
            coupon = fromCoupon(coupon),
            message = message,
            enrollDate = now,
            enrollEndDate = now.plus(enrollTerm.term, enrollTerm.unit),
            enrollCode = giftCouponCode.code
        )
        val savedGiftMessage = giftCouponMessageAdapter.create(sender, coupon, message, giftCouponCode)

        // then
        assertAll(
            { savedGiftMessage.giftMessage shouldBe message },
            { savedGiftMessage.giftCoupon.coupon shouldBe coupon },
            { savedGiftMessage.sender shouldBe sender },
            { savedGiftMessage.giftCouponCode shouldBe giftCouponCode },
        )
    }

    "쿠폰 선물 메시지를 조회한다." {
        // given
        val message = "선물 메시지"
        val giftCouponCode = GiftCouponCode.create()
        val sender = 일건창()

        // when
        every { giftMessageRepository.findByEnrollCode(giftCouponCode.code) } returns GiftMessageEntity(
            sender = sender.id,
            coupon = fromCoupon(나눠준_비율_할인_쿠폰),
            message = message,
            enrollDate = now,
            enrollEndDate = now.plusMonths(6),
            enrollCode = giftCouponCode.code
        )

        val giftCoupon = giftCouponMessageAdapter.findGiftCoupon(giftCouponCode)

        // then
        giftCoupon.coupon shouldBe 나눠준_비율_할인_쿠폰
    }

    "선물받은 쿠폰 코드를 사용한다." {
        // given
        val giftCouponCode = GiftCouponCode.create()

        // when
        every { giftMessageRepository.findByEnrollCode(giftCouponCode.code) } returns GiftMessageEntity(
            sender = 1L,
            coupon = fromCoupon(나눠준_비율_할인_쿠폰),
            message = "선물 메시지",
            enrollDate = now,
            enrollEndDate = now.plusMonths(6),
            enrollCode = giftCouponCode.code
        )

        // then
        shouldNotThrow<IllegalArgumentException> {
            giftCouponMessageAdapter.useBy(giftCouponCode)
        }
    }

    "선물받은 쿠폰 코드가 존재하지는지 확인한다." {
        // given
        val giftCouponCode = GiftCouponCode.create()

        // when
        every { giftMessageRepository.existsByEnrollCode(giftCouponCode.code) } returns true

        val isValid = giftCouponMessageAdapter.validate(giftCouponCode)

        // then
        isValid shouldBe true
    }
})
