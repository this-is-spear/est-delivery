package shop

import org.springframework.cloud.contract.spec.ContractDsl.Companion.contract


arrayOf(
    contract {
        name = "add royal customers"
        request {
            method = POST
            url = url(v(regex("/owners/shop/[0-9]{1,8}/royal-customers/[1-9]{0,7}[13579]")))
        }
        response {
            status = OK
        }
    },
    contract {
        request {
            method = POST
            url = url(v(regex("/owners/shop/[0-9]{1,8}/royal-customers/[1-9]{0,7}[02468]")))
        }
        response {
            status = BAD_REQUEST
        }
    }
)
