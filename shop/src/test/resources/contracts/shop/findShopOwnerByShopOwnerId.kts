package shop

import org.springframework.cloud.contract.spec.ContractDsl.Companion.contract

listOf(
    contract {
        name = "get owner id is 1"
        request {
            method = GET
            url = url("/owners/1")
        }
        response {
            status = OK
            headers {
                contentType = "application/json"
            }
            body = body(
                "shopOwnerId" to 1,
                "shopId" to 1,
                "shopName" to "첫 번째 가게"
            )
        }
    },
    contract {
        name = "get owner id is 2"
        request {
            method = GET
            url = url("/owners/2")
        }
        response {
            status = OK
            headers {
                contentType = "application/json"
            }
            body = body(
                "shopOwnerId" to 2,
                "shopId" to 2,
                "shopName" to "두 번째 가게"
            )
        }
    }
)
