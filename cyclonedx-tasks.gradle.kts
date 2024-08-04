import org.springframework.boot.gradle.tasks.bundling.BootBuildImage

plugins {
	val kotlinVersion = "2.0.0"
	id("org.springframework.boot") version "3.3.1"
	id("io.spring.dependency-management") version "1.1.5"
	kotlin("jvm") version kotlinVersion
	kotlin("plugin.spring") version kotlinVersion
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
