import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("kapt") version "1.5.31"
    id("org.springframework.boot") version "2.5.2"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.5.20"
    kotlin("plugin.spring") version "1.5.20"
    kotlin("plugin.jpa") version "1.5.20"
}

group = "tvz.lukec"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

kapt {
    useBuildCache = false
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "tvz.lukec.autoservice.AutoserviceApplicationKt"
    }

    duplicatesStrategy = DuplicatesStrategy.WARN

    // To add all of the dependencies
    from(sourceSets.main.get().output)

    dependsOn(configurations.runtimeClasspath)
    from({
        configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
    })
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.security:spring-security-config")
    implementation("org.liquibase:liquibase-core")
    implementation("org.postgresql:postgresql")
    implementation("org.postgresql:postgresql")
    implementation("org.mapstruct:mapstruct:1.3.1.Final")
//    testImplementation ("org.testng:testng:6.10", "org.easytesting:fest-assert:1.4")
    kapt("org.mapstruct:mapstruct-processor:1.3.1.Final")
    compileOnly("org.springframework.security:spring-security-oauth2-core")
    compileOnly("org.springframework.security.oauth:spring-security-oauth2:2.4.0.RELEASE")
    compileOnly("org.springframework.boot:spring-boot-starter-mail")
    compileOnly("org.springframework.security:spring-security-jwt:1.1.0.RELEASE")
    compileOnly("com.querydsl:querydsl-core:4.2.2")
    compileOnly("com.querydsl:querydsl-jpa:4.2.2")
    runtimeOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("org.postgresql:postgresql")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:sspring-security-test")
}

dependencies {
    compileOnly("com.querydsl:querydsl-jpa:4.2.2")
    kapt("com.querydsl:querydsl-apt:4.2.2:jpa")
    kapt("org.hibernate.javax.persistence:hibernate-jpa-2.1-api:1.0.2.Final")
    kapt("javax.annotation:javax.annotation-api:1.3.2")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
