package com.example.estdelivery.domain

import com.example.estdelivery.domain.MemberCouponUseState.UNUSED
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.NamedQuery
import jakarta.persistence.Table

@Entity
@NamedQuery(
    name = "unusedMemberCouponFinaAll",
    query = """
        SELECT c 
        FROM MemberCoupon c 
        WHERE c.status = com.example.estdelivery.domain.MemberCouponUseState.UNUSED
        AND c.couponId = :couponId
        """
)
@Table(name = "member_coupon", catalog = "coupon")
class MemberCoupon(
    val couponId: Long,
    val memberId: Long,
    @Enumerated(EnumType.STRING)
    var status: MemberCouponUseState = UNUSED,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is MemberCoupon) return false

        if (other.id == 0L) return false
        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    fun exchange(exchangedCouponId: Long): MemberCoupon {
        return MemberCoupon(
            couponId = exchangedCouponId,
            memberId = memberId,
            id = id,
        )
    }
}
