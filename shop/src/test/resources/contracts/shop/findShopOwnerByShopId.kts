package shop

import org.springframework.cloud.contract.spec.ContractDsl.Companion.contract

arrayOf(
    contract {
        request {
            method = GET
            url = url(v(regex("/owners/shop/[1-9]{0,7}[13579]")))
        }
        response {
            status = OK
            headers {
                contentType = "application/json"
            }
            body = body(
                "id" to value("${fromRequest().path(2)}"),
                "shop" to mapOf(
                    "royalCustomers" to listOf(1, 3, 5),
                    "name" to "가게",
                    "id" to value("${fromRequest().path(2)}")
                )
            )
        }
    },
    contract {
        request {
            method = GET
            url = url(v(regex("/owners/shop/[1-9]{0,7}[02468]")))
        }
        response {
            status = BAD_REQUEST
        }
    }
)
