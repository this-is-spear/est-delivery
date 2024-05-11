package com.example.estdelivery.domain.event

import kotlin.random.Random

fun generateDouble(): Double {
    val nextDouble = Random.nextDouble()
    check(nextDouble in 0.0..1.0) { "난수 값은 0.0 ~ 1.0 사이여야 합니다." }
    return nextDouble
}

fun generateInt(
    min: Int,
    max: Int,
): Int {
    require(min < max) { "min은 max보다 작아야 합니다." }
    require(min >= 0) { "min은 0보다 작을 수 없습니다." }
    val nextInt = Random.nextInt(min, max + 1)
    check(nextInt in min..max) { "난수 값은 $min ~ $max 사이여야 합니다." }
    return nextInt
}
