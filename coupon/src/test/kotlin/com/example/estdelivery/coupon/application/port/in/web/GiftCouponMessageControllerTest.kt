package com.example.estdelivery.coupon.application.port.`in`.web

import com.example.estdelivery.coupon.application.port.`in`.EnrollCouponByMessageUseCase
import com.example.estdelivery.coupon.application.port.`in`.FindAvailableGiftCouponUseCase
import com.example.estdelivery.coupon.application.port.`in`.GiftCouponByMessageUseCase
import com.example.estdelivery.coupon.application.port.`in`.web.dto.GiftCouponResponse
import com.example.estdelivery.coupon.application.port.`in`.web.dto.GiftCouponResponses
import com.example.estdelivery.coupon.application.port.`in`.web.dto.GiftMessageResponse
import com.example.estdelivery.coupon.domain.coupon.GiftCoupon
import com.example.estdelivery.coupon.domain.coupon.GiftCouponCode
import com.example.estdelivery.coupon.domain.fixture.나눠준_비율_할인_쿠폰
import com.example.estdelivery.coupon.domain.fixture.일건창
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import java.net.URL
import java.time.LocalDate
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

private const val MEMBER_ID = "Member-ID"

@WebMvcTest(GiftCouponMessageController::class)
@AutoConfigureMockMvc
class GiftCouponMessageControllerTest {
    @MockkBean
    lateinit var findAvailableGiftCouponUseCase: FindAvailableGiftCouponUseCase

    @MockkBean
    lateinit var giftCouponByMessageUseCase: GiftCouponByMessageUseCase

    @MockkBean
    lateinit var enrollCouponByMessageUseCase: EnrollCouponByMessageUseCase

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun `선물 가능한 쿠폰을 조회한다`() {
        // given
        val memberId = 1L
        // when
        every { findAvailableGiftCouponUseCase.findAvailableGiftCoupon(memberId) } returns GiftCouponResponses(
            listOf(
                GiftCouponResponse(
                    id = 나눠준_비율_할인_쿠폰.id!!,
                    name = 나눠준_비율_할인_쿠폰.name,
                    discountAmount = 나눠준_비율_할인_쿠폰.discountRate,
                    discountType = 나눠준_비율_할인_쿠폰.couponType
                )
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

    @Test
    fun `쿠폰 선물할 메시지를 전달받는다`() {
        // given
        val 일건창 = 일건창()
        val 선물할_쿠폰 = GiftCoupon(나눠준_비율_할인_쿠폰, LocalDate.now().plusDays(1))

        // when
        val messageDescription = "선물 메시지"
        val giftCode = "A1B2C3D4"

        every {
            giftCouponByMessageUseCase.sendGiftAvailableCoupon(일건창.id, 선물할_쿠폰.coupon.id!!, messageDescription)
        } returns GiftMessageResponse(
            senderName = 일건창.name,
            description = messageDescription,
            enrollEndDate = 선물할_쿠폰.enrollEndDate,
            enrollHref = URL("http", "localhost", 8080, "/gift-coupons/enroll/$giftCode")
        )

        // then
        mockMvc.post("/gift-coupons/send/${선물할_쿠폰.coupon.id}") {
            header(MEMBER_ID, 일건창.id)
            param("message", messageDescription)
        }.andExpect {
            status { isOk() }
            content {
                jsonPath("$.enrollHref") { value("http://localhost:8080/gift-coupons/enroll/$giftCode") }
                jsonPath("$.senderName") { value(일건창.name) }
                jsonPath("$.description") { value(messageDescription) }
            }
        }
    }

    @Test
    fun `쿠폰 코드를 입력해 쿠폰을 등록한다`() {
        // given
        val giftCouponCode = GiftCouponCode.create()
        val 일건창 = 일건창()

        // when
        every { enrollCouponByMessageUseCase.enroll(일건창.id, giftCouponCode) } returns Unit

        // then
        mockMvc.get("/gift-coupons/enroll/{code}", giftCouponCode.code) {
            header(MEMBER_ID, 일건창.id)
        }.andExpect {
            status { isOk() }
        }
    }
}
