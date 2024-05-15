package shop

import org.springframework.cloud.contract.spec.ContractDsl.Companion.contract

contract {
    name = "get owner shop id"
    request {
        method = GET
        url = url(v(regex("\\/owners\\/shop\\/[0-9]{1,10}")))
    }
    response {
        status = OK
        headers {
            contentType = "application/json"
        }
        body = body(
            "id" to value("${fromRequest().path(2)}"),
            "shop" to mapOf(
                "royalCustomers" to listOf(1, 2, 3),
                "name" to "가게",
                "id" to value("${fromRequest().path(2)}")
            )
        )
    }
}
