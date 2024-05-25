# Flume-ng SQL(source)

Language **English** | [简体中文](README.zh.md)

Since [flume-ng-sql-source](https://github.com/keedio/flume-ng-sql-source) is no longer being updated,
we started this project to fill the gap in the new version.

## Usage

1. [Get `Jar` package](#how-to-get-jar) and import it into `Flume lib`
2. We only need a small amount of **extra** configuration (see [`SQLSource.kt`[29:33]](src/main/kotlin/ink/awning/flume/sql/SQLSource.kt)):
    
   ```
    a1.sources.r1.type = ink.awning.flume.sql.SQLSource
    a1.sources.r1.sql.url = jdbc:mysql://localhost:3306/[database name]
    a1.sources.r1.sql.username = root // optional, defaults root
    a1.sources.r1.sql.password = [your password]
    a1.sources.r1.sql.query = SELECT * FROM [table name]
    a1.sources.r1.sql.delay = 1000 // optional, defaults to 1000 (ms = 1 second)
    ```
   
3. Run `Flume` and if it runs successfully, you will see our [Logo](src/main/kotlin/ink/awning/flume/sql/Logo.kt).
   Because our data is only a source, you can do things like [flume-ng-sql-source](https://github.com/keedio/flume-ng-sql-source)
   sending it to `Kafka` consumer

## How to get Jar

We have packaged the popular `JDK` (8, 11, 17, 21) versions of `Flume v1.11.0` in the [releases](https://github.com/Awning-Studio/flume-ng-sql/releases).

If you need to customize the package, please refer to the following:

1. Clone the project to a local computer.
   - Change `flumeVersion` in [`build.gradle.kts`[7]](build.gradle.kts)
   - Keep the driver dependencies in [`build.gradle.kts`[29:32]](build.gradle.kts).
   
2. Run `build/jar-all` in the `Gradle` task to generate a `Jar` package with all versions of the driver, or `build/jar-all-no-driver` to generate a `Jar` package without a driver.
   You can also run the corresponding `build/jar[version]` or `build/jar[version]-no-driver` to generate a `Jar` package with a specified `JDK` version.
3. Find the generated `Jar` package in `build/libs`.
