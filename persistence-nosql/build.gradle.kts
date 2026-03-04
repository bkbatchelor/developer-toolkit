dependencies {
    implementation(project(":persistence-common-api"))
    implementation(libs.spring.boot.starter.data.mongodb)
    
    testImplementation(libs.testcontainers.junit.jupiter)
    testImplementation(libs.testcontainers.mongodb)
}
