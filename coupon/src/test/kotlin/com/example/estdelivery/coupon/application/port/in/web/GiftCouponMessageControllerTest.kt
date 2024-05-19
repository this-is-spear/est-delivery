package com.example.estdelivery.coupon.application.port.`in`.web

import com.example.estdelivery.coupon.application.port.`in`.FindAvailableGiftCouponUseCase
import com.example.estdelivery.coupon.domain.coupon.GiftCoupon
import com.example.estdelivery.coupon.domain.fixture.나눠준_비율_할인_쿠폰
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

private const val MEMBER_ID = "Member-ID"

@WebMvcTest(GiftCouponMessageController::class)
@AutoConfigureMockMvc
class GiftCouponMessageControllerTest {
    @MockkBean
    lateinit var findAvailableGiftCouponUseCase: FindAvailableGiftCouponUseCase

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun `선물 가능한 쿠폰을 조회한다`() {
        // given
        val memberId = 1L
        // when
        every { findAvailableGiftCouponUseCase.findAvailableGiftCoupon(memberId) } returns listOf(
            GiftCoupon(
                나눠준_비율_할인_쿠폰
            )
        )
        // then
        mockMvc.get("/gift-coupons") {
            header(MEMBER_ID, memberId)
        }.andExpect {
            status { isOk() }
            content {
                jsonPath("$.coupons") { isArray() }
                jsonPath("$.coupons[0].id") { value(나눠준_비율_할인_쿠폰.id!!) }
            }
        }
    }
}
