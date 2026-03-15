plugins {
    java
    id("org.springframework.boot") apply false
    id("io.spring.dependency-management")
}

dependencies {
    implementation(libs.spring.boot.starter.data.mongodb)
    implementation(libs.spring.boot.starter.web)

    testImplementation(libs.testcontainers.junit.jupiter)
    testImplementation(libs.testcontainers.mongodb)
    testImplementation(libs.assertj.core)
    testImplementation(libs.archunit.junit5)
}
