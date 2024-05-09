package com.example.estdelivery.domain.event

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.bind
import io.kotest.property.checkAll
import java.util.*

class EqualDistributionRandomDoubleGeneratorKtTest : FreeSpec({
    "난수 값 생성 분포도를 확인한다." {
        checkAll<Int> {a ->
            val random = generate()
            when (random) {
                in 0.0..0.1 -> collect("statistic", "0.0..0.1")
                in 0.1..0.2 -> collect("statistic", "0.1..0.2")
                in 0.2..0.3 -> collect("statistic", "0.2..0.3")
                in 0.3..0.4 -> collect("statistic", "0.3..0.4")
                in 0.4..0.5 -> collect("statistic", "0.4..0.5")
                in 0.5..0.6 -> collect("statistic", "0.5..0.6")
                in 0.6..0.7 -> collect("statistic", "0.6..0.7")
                in 0.7..0.8 -> collect("statistic", "0.7..0.8")
                in 0.8..0.9 -> collect("statistic", "0.8..0.9")
                in 0.9..1.0 -> collect("statistic", "0.9..1.0")
            }
        }
    }
})
