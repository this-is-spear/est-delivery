package event

import org.springframework.cloud.contract.spec.ContractDsl.Companion.contract

arrayOf(
    contract {
        request {
            method = GET
            url = url(v(regex("/events/[0-9]{1,3}")))
        }
        response {
            status = OK
            headers {
                contentType = "application/json"
            }
            body = body(
                "id" to value("${fromRequest().path(1)}"),
                "description" to "이벤트 설명",
                "isProgress" to true,
                "discountType" to "FIXED",
                "intervalsProbability" to listOf(
                    mapOf(
                        "min" to 1000,
                        "max" to 1500,
                        "probability" to 0.5
                    ),
                    mapOf(
                        "min" to 1600,
                        "max" to 2000,
                        "probability" to 0.3
                    ),
                    mapOf(
                        "min" to 2100,
                        "max" to 2500,
                        "probability" to 0.2
                    )
                ),
                "discountRangeMin" to 1000,
                "discountRangeMax" to 2500,
                "participatedMembers" to listOf(1, 2, 3)
            )
        }
    },
    contract {
        request {
            method = GET
            url = url(v(regex("/events/1000")))
        }
        response {
            status = BAD_REQUEST
        }
    },
    contract {
        request {
            method = GET
            url = url(v(regex("/events/1001")))
        }
        response {
            status = TOO_MANY_REQUESTS
        }
    },
    contract {
        request {
            method = GET
            url = url(v(regex("/events/1100")))
        }
        response {
            status = INTERNAL_SERVER_ERROR
        }
    },
    contract {
        request {
            method = GET
            url = url(v(regex("/events/1101")))
        }
        response {
            status = SERVICE_UNAVAILABLE
        }
    }
)
