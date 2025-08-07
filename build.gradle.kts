plugins {
    val openapiGeneratorVersion: String by System.getProperties()

    id("java")
    id("org.openapi.generator").version(openapiGeneratorVersion)
}

group = "io.modernesteve"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    //can't seem to find these once it tries to add the .generatedsources/openapi/src/main/java directory as main source set java directory
    implementation("com.google.code.findbugs:jsr305:3.0.2")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
    implementation("com.google.code.gson:gson:2.9.1")
    implementation("io.gsonfire:gson-fire:1.9.0")
    implementation("jakarta.annotation:jakarta.annotation-api:1.3.5")
}

afterEvaluate {
    println("I'm configuring ${project.name} with version ${project.version}")
    println("project dir: ${project.projectDir}")
    println("build dir: ${layout.buildDirectory.get().asFile.absolutePath}")
}

apply(from = "gradle/openapi.gradle.kts")

