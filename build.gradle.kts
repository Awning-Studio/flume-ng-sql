plugins {
    kotlin("jvm") version "1.9.24"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "ink.awning.flume"
version = "1.0"

// Your jdk version when using Flume.
val targetJDK = 11

kotlin {
    jvmToolchain(targetJDK)
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.apache.flume:flume-ng-core:1.11.0") {
        exclude(group = "com.fasterxml.jackson.core", module = "*")
    }
    implementation("com.fasterxml.jackson.core:jackson-databind:2.10.5.1")
    implementation("org.hibernate.orm:hibernate-core:6.5.0.Final")
    implementation("org.hibernate.orm:hibernate-c3p0:6.5.0.Final")

    // MySQL driver
    implementation("com.mysql:mysql-connector-j:8.3.0")
    // SQL Server driver
    // implementation("com.microsoft.sqlserver:mssql-jdbc:12.6.1.jre11")
}

tasks {
    shadowJar {
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        archiveFileName = "flume-ng-sql-$version-jdk$targetJDK.jar"
        manifest.attributes["Main-Class"] = "ink.awning.flume.sql.SQLSource"
        dependencies {
            include(dependency("com.fasterxml.jackson.core:jackson-databind:.*"))
            include(dependency("com.fasterxml:classmate:.*"))
            include(dependency("com.mchange:.*:.*"))
            include(dependency("com.mysql:mysql-connector-j:.*"))
            include(dependency("jakarta.persistence:.*:.*"))
            include(dependency("jakarta.transaction:.*:.*"))
            include(dependency("jakarta.xml.bind:.*:.*"))
            include(dependency("net.bytebuddy:.*:.*"))
            include(dependency("org.antlr:.*:.*"))
            include(dependency("org.hibernate.*:.*:.*"))
            include(dependency("org.jboss.logging:jboss-logging:.*"))
        }
    }
}