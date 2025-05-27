plugins {
    `maven-publish`
}

publishing {
    publications {
        withType<MavenPublication> {
            pom {
                name = Project.NAME
                description = Project.DESCRIPTION
                inceptionYear = "2024"

                licenses {
                    license {
                        name = Project.LICENSE
                    }
                }

                developers {
                    developer {
                        id = "dragongod65"
                        name = Project.AUTHOR
                        timezone = "Europe/Berlin"
                        email = "dev.dragongod65"
                    }
                }

                ciManagement {
                    system = "Github"
                }

                issueManagement {
                    system = "Github"
                }
            }
        }

        repositories {
            if (System.getenv(/* name = */ "CI_JOB_TOKEN") != null) {
                maven {
                    name = "GitLab"

                    val projectId = System.getenv("CI_PROJECT_ID")
                    val apiV4 = System.getenv("CI_API_V4_URL")
                    url = uri("$apiV4/projects/$projectId/packages/maven")

                    authentication {
                        create(/* name = */ "token", /* type = */ HttpHeaderAuthentication::class.java) {
                            credentials(HttpHeaderCredentials::class.java) {
                                name = "Job-Token"
                                value = System.getenv(/* name = */ "CI_JOB_TOKEN")
                            }
                        }
                    }
                }
            }
        }
    }
}
