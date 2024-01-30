/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Java library project to get you started.
 * For more details on building Java & JVM projects, please refer to https://docs.gradle.org/8.5/userguide/building_java_projects.html in the Gradle documentation.
 */

plugins {
    id("com.codedifferently.studycrm.java-library")
}

group = "com.codedifferently.studycrm.organization-service.web"

dependencies {
    implementation("com.codedifferently.studycrm.common.domain:common-domain-lib")
    implementation("com.codedifferently.studycrm.organization-service.domain:organization-domain-lib")
    implementation("com.codedifferently.studycrm.organization-service.persistence:organization-persistence-lib")
    implementation("com.codedifferently.studycrm.organization-service.api.web:organization-api-web-lib")

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.security:spring-security-acl")
	implementation("org.springframework.security:spring-security-config")
    implementation("org.springframework.security:spring-security-oauth2-jose")
    implementation("org.springframework.security:spring-security-oauth2-resource-server")

	runtimeOnly("org.springframework:spring-context-support")
}