package shop

import org.springframework.cloud.contract.spec.ContractDsl.Companion.contract

contract {
    name = "add royal customers"
    request {
        method = POST
        url = url(v(regex("\\/owners\\/shop\\/[0-9]{1,10}\\/royal-customers\\/[0-9]{1,10}")))
    }
    response {
        status = OK
    }
}
