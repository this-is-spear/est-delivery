package com.example.estdelivery.coupon.application.port.out.adapter.persistence

import com.example.estdelivery.coupon.application.port.out.adapter.persistence.entity.GiftMessageEntity
import com.example.estdelivery.coupon.application.port.out.adapter.persistence.mapper.fromCoupon
import com.example.estdelivery.coupon.application.port.out.adapter.persistence.repository.GiftMessageRepository
import com.example.estdelivery.coupon.domain.coupon.GiftCoupon
import com.example.estdelivery.coupon.domain.coupon.GiftCouponCode
import com.example.estdelivery.coupon.domain.fixture.나눠준_비율_할인_쿠폰
import com.example.estdelivery.coupon.domain.fixture.일건창
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.assertAll

class GiftCouponMessageAdapterTest : FreeSpec({
    val giftMessageRepository = mockk<GiftMessageRepository>()

    lateinit var giftCouponMessageAdapter: GiftCouponMessageAdapter

    beforeTest {
        giftCouponMessageAdapter = GiftCouponMessageAdapter(giftMessageRepository)
    }

    "쿠폰 선물 메시지를 저장한다." {
        // given
        val sender = 일건창()
        val coupon = 나눠준_비율_할인_쿠폰
        val message = "선물 메시지입니다."
        val giftCouponCode = GiftCouponCode.create()

        // when
        every { giftMessageRepository.save(any()) } returns GiftMessageEntity(
            sender = sender.id,
            coupon = fromCoupon(coupon),
            message = message,
            enrollCode = giftCouponCode.code
        )
        val savedGiftMessage = giftCouponMessageAdapter.create(sender, coupon, message, giftCouponCode)

        // then
        assertAll(
            { savedGiftMessage.giftMessage shouldBe message },
            { savedGiftMessage.giftCoupon shouldBe GiftCoupon(나눠준_비율_할인_쿠폰) },
            { savedGiftMessage.sender shouldBe sender },
            { savedGiftMessage.giftCouponCode shouldBe giftCouponCode },
        )
    }

    "쿠폰 선물 메시지를 조회한다." {
        // given
        val message = "선물 메시지"
        val giftCouponCode = GiftCouponCode.create()
        val sender = 일건창()
        val coupon = GiftCoupon(나눠준_비율_할인_쿠폰)

        // when
        every { giftMessageRepository.findByEnrollCode(giftCouponCode.code) } returns GiftMessageEntity(
            sender = sender.id,
            coupon = fromCoupon(coupon.coupon),
            message = message,
            enrollCode = giftCouponCode.code
        )

        val giftCoupon = giftCouponMessageAdapter.findGiftCoupon(giftCouponCode)

        // then
        giftCoupon shouldBe GiftCoupon(나눠준_비율_할인_쿠폰)
    }

    "선물받은 쿠폰 코드를 사용한다." {
        // given
        val giftCouponCode = GiftCouponCode.create()

        // when
        every { giftMessageRepository.findByEnrollCode(giftCouponCode.code) } returns GiftMessageEntity(
            sender = 1L,
            coupon = fromCoupon(나눠준_비율_할인_쿠폰),
            message = "선물 메시지",
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
