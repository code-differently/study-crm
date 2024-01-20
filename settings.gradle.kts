rootProject.name = "study-crm"

includeBuild("platforms")
includeBuild("build-logic")

includeBuild("contact-service/contact-service-main")
includeBuild("contact-service/contact-service-web")
includeBuild("contact-service/contact-service-persistence")
includeBuild("contact-service/contact-service-domain")