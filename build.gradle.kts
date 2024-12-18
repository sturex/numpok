plugins {
	java
	id("org.springframework.boot") version "3.0.5"
	id("io.spring.dependency-management") version ("1.1.0")
	id("application")
}
group = "com.numpok"
version = "0.0.1"


repositories {
	mavenCentral()
}

dependencies {
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
	implementation("org.springframework.boot:spring-boot-starter-graphql")
	implementation("org.springframework.boot:spring-boot-starter-web")

//	implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
//	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
//	implementation("org.liquibase:liquibase-core")
//	runtimeOnly("org.postgresql:postgresql")
}
