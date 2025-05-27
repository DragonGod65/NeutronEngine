plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
rootProject.name = "NeutronEngine"

// Libraries
include("libraries:directX")
include("libraries:jbullet")
include("libraries:metal")
include("libraries:physX")
include("libraries:spir-v")

include("libraries:vulkan")

include("modules:core:neutron-core")
include("modules:core:neutron-physics")
include("tools")
include("tests")
