import org.springframework.cloud.contract.spec.ContractDsl.Companion.contract

arrayOf(
    contract {
        request {
            method = PUT
            url = url(v(regex("/events/[0-9]{1,9}/participants/[1-9]{0,7}[13579]")))
        }
        response {
            status = OK
        }
    },
    contract {
        request {
            method = PUT
            url = url(v(regex("/events/[0-9]{1,9}/participants/[1-9]{0,7}[02468]")))
        }
        response {
            status = BAD_REQUEST
        }
    }
)
