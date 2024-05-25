plugins {
    kotlin("jvm") version "2.0.0"
}

group = "ink.awning.flume"
version = "1.0.1"

// Your jdk version when using Flume.
val targetJDK = 21

kotlin {
    jvmToolchain(targetJDK)
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    compileOnly("org.apache.flume:flume-ng-core:1.11.0") {
        exclude(group = "com.fasterxml.jackson.core", module = "*")
    }
    implementation("com.fasterxml.jackson.core:jackson-databind:2.10.5.1")
    implementation("org.hibernate.orm:hibernate-core:6.5.0.Final")

    // MySQL driver
    implementation("com.mysql:mysql-connector-j:8.3.0")
    // SQL Server driver
    // implementation("com.microsoft.sqlserver:mssql-jdbc:12.6.1.jre11")
}

tasks {
    jar {
        archiveFileName = "flume-ng-sql-$version-jdk$targetJDK.jar"
        manifest.attributes["Main-Class"] = "ink.awning.flume.sql.SQLSource"
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE

        val excludedJars = listOf(
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
        )
        from(
            configurations.runtimeClasspath.get()
                .filter { it.name.endsWith(".jar") }
                .filter { !excludedJars.contains(it.name) }
                .map { zipTree(it) }
        )
    }
}