plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
rootProject.name = "NeutronEngine"

// Libraries
include("libraries:vulkan")

include("modules:core:neutron-core")
include("tools")
include("tests")
