package shop

import org.springframework.cloud.contract.spec.ContractDsl.Companion.contract

arrayOf(
    contract {
        request {
            method = GET
            url = url(v(regex("/owners/[0-9]{0,2}[1-9]")))
        }
        response {
            status = OK
            headers {
                contentType = "application/json"
            }
            body = body(
                "id" to value("${fromRequest().path(1)}"),
                "shop" to mapOf(
                    "royalCustomers" to listOf(1, 2, 3),
                    "name" to "가게",
                    "id" to value("${fromRequest().path(1)}")
                )
            )
        }
    },
    contract {
        request {
            method = GET
            url = url(v(regex("/owners/1000")))
        }
        response {
            status = BAD_REQUEST
        }
    },
    contract {
        request {
            method = GET
            url = url(v(regex("/owners/1001")))
        }
        response {
            status = TOO_MANY_REQUESTS
        }
    },
    contract {
        request {
            method = GET
            url = url(v(regex("/owners/1100")))
        }
        response {
            status = INTERNAL_SERVER_ERROR
        }
    },
    contract {
        request {
            method = GET
            url = url(v(regex("/owners/1101")))
        }
        response {
            status = SERVICE_UNAVAILABLE
        }
    }
)
