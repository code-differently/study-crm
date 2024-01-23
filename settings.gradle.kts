rootProject.name = "study-crm"

includeBuild("platforms")
includeBuild("build-logic")

includeBuild("contact-service/contact-service-main")
includeBuild("contact-service/contact-service-web")
includeBuild("contact-service/contact-service-persistence")
includeBuild("contact-service/contact-service-domain")
includeBuild("contact-service/contact-service-api-web")
includeBuild("organization-service/organization-service-main")
includeBuild("organization-service/organization-service-web")
includeBuild("organization-service/organization-service-persistence")
includeBuild("organization-service/organization-service-domain")
includeBuild("organization-service/organization-service-api-web")