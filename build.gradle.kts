plugins {
    // Apply the Spring Boot and Dependency Management plugins at the root
    alias(libs.plugins.spring.boot) apply false
    alias(libs.plugins.spring.dependency.management)
    java
}

allprojects {
    group = "com.developer.toolkit"
    version = "0.0.1-SNAPSHOT"

    repositories {
        mavenCentral()
    }
}

// Capture the version catalog to make it accessible in subprojects block
val versionCatalog = libs

subprojects {
    apply(plugin = "java")
    apply(plugin = "io.spring.dependency-management")

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(versionCatalog.versions.java.get()))
        }
    }

    // Configure dependency management for Spring Boot BOM
    extensions.configure<io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension> {
        imports {
            mavenBom(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES)
        }
    }

    dependencies {
        // Common dependencies for all subprojects
        compileOnly(versionCatalog.lombok)
        annotationProcessor(versionCatalog.lombok)
        
        testImplementation(versionCatalog.spring.boot.starter.test)
        testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}
