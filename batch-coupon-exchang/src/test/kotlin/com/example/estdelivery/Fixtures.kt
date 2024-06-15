package com.example.estdelivery

import com.example.estdelivery.domain.Coupon
import com.navercorp.fixturemonkey.FixtureMonkey
import com.navercorp.fixturemonkey.api.introspector.ConstructorPropertiesArbitraryIntrospector
import com.navercorp.fixturemonkey.kotlin.KotlinPlugin
import com.navercorp.fixturemonkey.kotlin.giveMeBuilder
import com.navercorp.fixturemonkey.kotlin.set

var SUT: FixtureMonkey = FixtureMonkey.builder()
    .objectIntrospector(ConstructorPropertiesArbitraryIntrospector.INSTANCE)
    .plugin(KotlinPlugin())
    .build()

val couponBuilder = SUT.giveMeBuilder<Coupon>()
    .set(Coupon::id, 0)
