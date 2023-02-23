plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {


    implementation("org.springframework.boot:spring-boot-starter-web:2.5.4")
    implementation("org.springframework.boot:spring-boot-starter-security:2.5.4")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server:2.5.4")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}