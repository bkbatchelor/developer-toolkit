plugins {
    alias(libs.plugins.spring.boot)
}

dependencies {
    implementation(platform(libs.mongock.bom))
    implementation(libs.mongock.springboot.v3)
    implementation(libs.mongock.mongodb.springdata.v4.driver)

    implementation(libs.spring.boot.starter.data.mongodb)
    implementation(libs.spring.boot.starter.web)
    implementation(project(":persistence-nosql-id-generator"))
    
    testImplementation(libs.testcontainers.junit.jupiter)
    testImplementation(libs.testcontainers.mongodb)
}
