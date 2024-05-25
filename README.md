# Flume-ng SQL(source)

Language **English** | [简体中文](README.zh.md)

Since [flume-ng-sql-source](https://github.com/keedio/flume-ng-sql-source) is no longer being updated,
we started this project to fill the gap in the new version.


Note: The project only supports `JDK 11+`, if your `Java`
version does not meet this requirement, please go to [flume-ng-sql-source](https://github.com/keedio/flume-ng-sql-source)

## Usage

1. [Get `Jar` package](#how-to-get-jar) and import it into `Flume`
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
   sending it to `Kafka`

## How to get Jar

We have packaged the mainstream `JDK` (11, 17, 21) versions of `Flume v1.11.0` in the releases, and the supporting driver is `MySQL`.

If you need to customize the package, please refer to the following:

1. Clone the project to a local computer, and change the `targetJDK` in [`build.gradle.kts`[9]](build.gradle.kts) to your `JDK` version
   
   ```kotlin
      val targetJDK = Version
   ```
   
2. Run `build/build` in the `Gradel` task
3. Find the generated `Jar` package in `build/libs`
