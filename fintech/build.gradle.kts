plugins {
    id("java")
    id("org.springframework.boot") version "3.1.4"
    id("io.spring.dependency-management") version "1.1.3"
    application
}

application {
    mainClass.set("tinkoff.training.Main")
}
java {
    sourceCompatibility = JavaVersion.VERSION_17
}

group = "tinkoff.training"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}
extra["springCloudVersion"] = "2022.0.4"


dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.cloud:spring-cloud-starter-circuitbreaker-resilience4j")
//    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.liquibase:liquibase-core")
    implementation("com.h2database:h2")
    implementation("org.springframework.boot:spring-boot-starter-security")
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation ("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")

    runtimeOnly ("com.h2database:h2")

    implementation ("org.mapstruct:mapstruct:1.5.5.Final")

    annotationProcessor ("org.mapstruct:mapstruct-processor:1.5.5.Final")

    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")
    implementation("io.swagger.core.v3:swagger-annotations:2.2.16")

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    compileOnly("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")

    testCompileOnly("org.projectlombok:lombok:1.18.30")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.30")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}

tasks.test {
    useJUnitPlatform()
}

tasks.create("fatjarCompile", Jar::class) {
    group = "my tasks"
    description = "Creates a self-contained fat JAR of the application that can be run."
    manifest.attributes["Main-Class"] = "tinkoff.training.Main"
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    val dependencies = configurations
            .runtimeClasspath
            .get()
            .map(::zipTree)
    from(dependencies)
    with(tasks.jar.get())
}
