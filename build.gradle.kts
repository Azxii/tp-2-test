plugins {
    id 'java'
    id 'jacoco'
    id 'org.sonarqube' version '7.2.2.6593'
    id 'maven-publish'
}

group = 'com.example'
version = '1.0-SNAPSHOT'

repositories {
    mavenCentral()
}

sonar {
    properties {
        property 'sonar.projectKey', 'Tp_Jenkins'
        property 'sonar.projectName', 'My Gradle Project_Jenkins'
        property 'sonar.host.url', 'http://localhost:9000'
        property 'sonar.login', 'sqa_9d7dfca1275ac35f2620073c6efdeeec6b7808cb'
    }
}

dependencies {
    testImplementation 'io.cucumber:cucumber-java:6.0.0'
    testImplementation 'junit:junit:4.12'
}