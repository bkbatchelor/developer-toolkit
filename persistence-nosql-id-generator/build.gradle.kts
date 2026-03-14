plugins {
    java
    id("org.springframework.boot") apply false
    id("io.spring.dependency-management")
}

dependencies {
    implementation(libs.spring.boot.starter.data.mongodb)

    testImplementation(libs.testcontainers.junit.jupiter)
    testImplementation(libs.testcontainers.mongodb)
    testImplementation(libs.assertj.core)
}
