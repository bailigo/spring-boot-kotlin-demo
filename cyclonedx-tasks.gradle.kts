plugins {
    id("org.cyclonedx.bom") version "1.8.2"
    id("org.springframework.boot") version "3.3.1"
}

tasks.named<Jar>("bootJar") {
    dependsOn(tasks.named("cyclonedxBom"))
    from(file("$buildDir/reports")) {
        include("application.cdx.json")
        into("META-INF/sbom")
    }
}

tasks.named<Jar>("jar") {
    dependsOn(tasks.named("cyclonedxBom"))
    from(file("$buildDir/reports")) {
        include("application.cdx.json")
        into("META-INF/sbom")
    }
}

tasks.named<org.cyclonedx.gradle.CycloneDxTask>("cyclonedxBom") {
    outputName.set("application.cdx")
    outputFormat.set("json")
    schemaVersion.set("1.5")
    destination.set(file("build/reports"))
    includeConfigs.set(listOf("runtimeClasspath", "compileClasspath"))
    skipConfigs.set(emptyList())
    skipProjects.set(emptyList())
}
