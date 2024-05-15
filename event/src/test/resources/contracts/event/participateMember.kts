
import org.springframework.cloud.contract.spec.ContractDsl.Companion.contract

contract {
    request {
        method = PUT
        url = url(v(regex("/events/[0-9]{1,9}/participants/[0-9]{1,9}")))
    }
    response {
        status = OK
    }
}
