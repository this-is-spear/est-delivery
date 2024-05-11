package com.example.estdelivery.domain.event

/**
 * 구간을 가지고 구간에는 확률이 존재한다.
 */
class DiscountAmountProbability(
    private val intervalsProbability: List<ProbabilityRange>,
    private val discountRange: DiscountRange,
) {
    init {
        require(intervalsProbability.sumOf { it.probability } == 1.0) { "확률의 합은 1이 되어야 합니다." }
        intervalsProbability.forEach {
            require(it.min >= discountRange.min) { "DiscountRange의 최소값보다 작을 수 없습니다." }
            require(it.max <= discountRange.max) { "DiscountRange의 최대값보다 클 수 없습니다." }
        }
    }

    fun getAmountBetween(): Int {
        val randomBetween0to1 = generateDouble()
        var sum = 0.0
        intervalsProbability.forEach {
            sum += it.probability
            if (randomBetween0to1 <= sum) {
                return generateInt(it.min, it.max)
            }
        }

        throw IllegalStateException("할인 금액을 선정할 수 없습니다.")
    }
}
