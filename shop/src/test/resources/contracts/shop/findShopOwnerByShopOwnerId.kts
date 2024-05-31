package shop

import org.springframework.cloud.contract.spec.ContractDsl.Companion.contract

arrayOf(
    contract {
        request {
            method = GET
            url = url(v(regex("\\/owners\\/[0-9]{1,7}[13579]")))
        }
        response {
            status = OK
            headers {
                contentType = "application/json"
            }
            body = body(
                "id" to value("${fromRequest().path(1)}"),
                "shop" to mapOf(
                    "royalCustomers" to listOf(1, 3, 5),
                    "name" to "가게",
                    "id" to value("${fromRequest().path(1)}")
                )
            )
        }
    },
    contract {
        request {
            method = GET
            url = url(v(regex("\\/owners\\/[0-9]{1,7}[02468]")))
        }
        response {
            status = BAD_REQUEST
        }
    }
)
