plugins {
    kotlin("jvm") version "2.0.0"
}

group = "ink.awning.flume"
version = "1.0.1"
val flumeVersion = "1.11.0"

kotlin {
    jvmToolchain(21)
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    compileOnly("org.apache.flume:flume-ng-core:$flumeVersion") {
        exclude(group = "com.fasterxml.jackson.core", module = "*")
    }
    implementation("com.fasterxml.jackson.core:jackson-databind:2.10.5.1")
    implementation(
        if (java.toolchain.languageVersion.get().asInt() == 8)
            "org.hibernate:hibernate-core:5.6.0.Final"
        else "org.hibernate.orm:hibernate-core:6.5.0.Final"
    )

    // MySQL driver
    implementation("com.mysql:mysql-connector-j:8.3.0")
    // SQL Server driver
//    implementation("com.microsoft.sqlserver:mssql-jdbc:12.6.1.jre11")
}

tasks {
    register("jar8", Jar::class) { forJDK(8); group = "build" }
    register("jar11", Jar::class) { forJDK(11); group = "build" }
    register("jar17", Jar::class) { forJDK(17); group = "build" }
    register("jar21", Jar::class) { forJDK(21); group = "build" }
    register("jar8-no-driver", Jar::class) { forJDK(8, false); group = "build" }
    register("jar11-no-driver", Jar::class) { forJDK(11, false); group = "build" }
    register("jar17-no-driver", Jar::class) { forJDK(17, false); group = "build" }
    register("jar21-no-driver", Jar::class) { forJDK(21, false); group = "build" }
    register("jar-all") {
        dependsOn("jar8", "jar11", "jar17", "jar21")
        group = "build"
    }
    register("jar-all-no-driver") {
        dependsOn("jar8-no-driver", "jar11-no-driver", "jar17-no-driver", "jar21-no-driver")
        group = "build"
    }
    jar { forJDK(java.toolchain.languageVersion.get().asInt()) }
}


fun Jar.forJDK(versionCode: Int, withDriver: Boolean = true) {
    manifest.attributes["Main-Class"] = "ink.awning.flume.sql.SQLSource"
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE

    val excludedJars = mutableListOf(
        "kotlin-stdlib-2.0.0.jar",
        "annotations-13.0.jar",
        "jackson-annotations-2.10.5.jar",
        "jackson-core-2.10.5.jar",
        "jandex-3.1.2.jar",
        "jaxb-runtime-4.0.2.jar",
        "jaxb-core-4.0.2.jar",
        "jakarta.inject-api-2.0.1.jar",
        "protobuf-java-3.25.1.jar",
        "angus-activation-2.0.0.jar",
        "jakarta.activation-api-2.1.1.jar",
        "txw2-4.0.2.jar",
        "istack-commons-runtime-4.1.1.jar"
    ).apply {
        if (!withDriver) {
            add("mysql-connector-j-8.3.0.jar")
            add("mssql-jdbc-12.6.1.jre11.jar")
        }
    }

    val drivers = mutableListOf<String>()

    from(
        configurations.runtimeClasspath.get()
            .filter { it.name.endsWith(".jar") }
            .onEach {
                // get drivers
                if (withDriver) it.name.run {
                    if(contains("mysql-connector-j") || contains("mssql-jdbc"))
                        drivers.add(replace(".jar", ""))
                }
            }
            .filter { !excludedJars.contains(it.name) }
            .map { zipTree(it) }
    )
    archiveFileName =
        "flume-ng-sql$version-$flumeVersion-jdk$versionCode-${
            if (withDriver) drivers.ifEmpty { listOf("unknown-driver") }.joinToString("&")
            else "no-driver"
        }.jar"
}