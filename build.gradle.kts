val ktorVersion: String by project
val kotlinVersion: String by project
val logbackVersion: String by project
val kmongoVersion: String by project
val koinVersion: String by project

plugins {
    application
    kotlin("jvm") version "1.7.21"
    id("io.ktor.plugin") version "2.1.3"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.7.21"
}

group = "de.vexxes"
version = "0.0.1"
application {
    //mainClass.set("io.ktor.server.netty.EngineMain")
    mainClass.set("de.vexxes.Application")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-call-logging-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-netty-jvm:$ktorVersion")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    testImplementation("io.ktor:ktor-server-tests-jvm:$ktorVersion")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:1.7.21")

    // Content Negotiation
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktorVersion")

    // Sessions
    implementation("io.ktor:ktor-server-sessions-jvm:$ktorVersion")

    // Auth
    implementation("io.ktor:ktor-server-auth-jvm:$ktorVersion")

    // KMongo
    implementation("org.litote.kmongo:kmongo-async:$kmongoVersion")
    implementation("org.litote.kmongo:kmongo-coroutine-serialization:$kmongoVersion")
    implementation("org.litote.kmongo:kmongo-id:$kmongoVersion")

    // Koin core features
    implementation("io.insert-koin:koin-core:$koinVersion")
    implementation("io.insert-koin:koin-ktor:$koinVersion")
    implementation("io.insert-koin:koin-logger-slf4j:$koinVersion")

    // Google Client API Library
    implementation("com.google.api-client:google-api-client:2.0.1")

    implementation("io.ktor:ktor-client-core-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-locations-jvm:$ktorVersion")
    implementation("io.ktor:ktor-client-apache-jvm:$ktorVersion")

    implementation("io.ktor:ktor-serialization-kotlinx-xml:$ktorVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")

}

ktor {
    docker {
        localImageName.set("penaltycatalogserver-docker-image")
        imageTag.set("$version-preview")


        portMappings.set(listOf(
            io.ktor.plugin.features.DockerPortMapping(
                8088,
                8088,
                io.ktor.plugin.features.DockerPortMappingProtocol.TCP
            )
        ))
    }
}

tasks.withType<Jar> {
    // Otherwise you'll get a "No main manifest attribute" error
    manifest {
        attributes["Main-Class"] = "de.vexxes.ApplicationKt"
    }

    // To avoid the duplicate handling strategy error
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE

    // To add all of the dependencies
    from(sourceSets.main.get().output)

    dependsOn(configurations.runtimeClasspath)
    from({
        configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
    })
}
