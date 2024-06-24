package com.example.estdelivery

import com.example.estdelivery.domain.Coupon
import com.example.estdelivery.domain.MemberCoupon
import com.example.estdelivery.domain.MemberCouponUseState
import com.navercorp.fixturemonkey.FixtureMonkey
import com.navercorp.fixturemonkey.api.introspector.ConstructorPropertiesArbitraryIntrospector
import com.navercorp.fixturemonkey.kotlin.KotlinPlugin
import com.navercorp.fixturemonkey.kotlin.giveMeBuilder
import com.navercorp.fixturemonkey.kotlin.set
import net.jqwik.api.Arbitraries

var SUT: FixtureMonkey = FixtureMonkey.builder()
    .objectIntrospector(ConstructorPropertiesArbitraryIntrospector.INSTANCE)
    .plugin(KotlinPlugin())
    .build()

val couponBuilder = SUT.giveMeBuilder<Coupon>()
    .set(Coupon::id, 0)
    .set(Coupon::amount, Arbitraries.integers().between(1, 100))

val couponMemberBuilder = SUT.giveMeBuilder<MemberCoupon>()
    .set(MemberCoupon::id, 0)
    .set(MemberCoupon::couponId, Arbitraries.longs().between(1, 100))
