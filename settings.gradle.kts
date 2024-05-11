plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}
rootProject.name = "est-delivery"
include("coupon")
include("shop")
include("event")
include("member")
