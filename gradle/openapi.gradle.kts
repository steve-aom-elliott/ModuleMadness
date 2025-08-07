import org.openapitools.generator.gradle.plugin.tasks.GenerateTask
import org.gradle.api.tasks.SourceSetContainer

buildscript {
    repositories {
        mavenLocal()
        mavenCentral()
    }
    val openapiGeneratorVersion: String by System.getProperties()
    dependencies {
        classpath("org.openapitools:openapi-generator-gradle-plugin:$openapiGeneratorVersion")
    }
}

/// github client api generation BEGIN
tasks.register<GenerateTask>("generateGithubClient") {
    generatorName.set("java")
    // Changed to just a generic YAML OpenApi description
    inputSpec.set("${project.projectDir}/src/main/resources/api.yaml")
    outputDir.set("${project.projectDir}/.generatedsources/openapi")
    apiPackage.set("io.modernesteve.client.github.api")
    modelPackage.set("io.modernesteve.client.github.model")
    invokerPackage.set("io.modernesteve.client.github.invoker")
    // I don't know what the contents of these files would have been
//    configFile.set("${project.projectDir}/src/main/resources/openapi/consumer/github-config.json")
//    ignoreFileOverride.set("${project.projectDir}/src/main/resources/openapi/consumer/github.ignore")
    globalProperties.set(mapOf("tags" to "repos"))
}

tasks.getByName("compileJava").dependsOn("generateGithubClient")
/// github client api generation END

//openapi cleanup job
tasks.getByName("clean").doFirst {
    project.file("${project.projectDir}/.generatedsources/openapi").deleteRecursively()
}

//openapi src configuration
val sourceSets = the<SourceSetContainer>()
sourceSets["main"].java.srcDir("${project.projectDir}/.generatedsources/openapi/src/main/java")
