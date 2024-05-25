# Flume-ng SQL（源）

语言 [English](README.md) | **简体中文**

因为[flume-ng-sql-source](https://github.com/keedio/flume-ng-sql-source)已经不再更新，为了填补在新版本的空缺，我们开启了这个项目。

注意：该项目只支持`JDK 11+`，如果您的`Java`
版本不符合此要求，请移步[flume-ng-sql-source](https://github.com/keedio/flume-ng-sql-source)

## 使用

1. [获取`Jar`包](#如何获取-jar)，导入`Flume`中
   ```kotlin
      val targetJDK = 版本
   ```
2. 我们只需要少量**额外**的配置即可（参考[`SQLSource.kt`[29:33]](src/main/kotlin/ink/awning/flume/sql/SQLSource.kt)）：

   ```
    a1.sources.r1.type = ink.awning.flume.sql.SQLSource
    a1.sources.r1.sql.url = jdbc:mysql://localhost:3306/[数据库名称]
    a1.sources.r1.sql.username = root // 可选，默认为 root
    a1.sources.r1.sql.password = [你的密码]
    a1.sources.r1.sql.query = SELECT * FROM [表名]
    a1.sources.r1.sql.delay = 1000 // 可选，默认为 1000（毫秒 = 1 秒）
    ```
3. 运行`Flume`，若成功运行，您将在日志中看到我们的[Logo](src/main/kotlin/ink/awning/flume/sql/Logo.kt)。
   因为我们的数据仅仅作为源，您可以像[flume-ng-sql-source](https://github.com/keedio/flume-ng-sql-source)
   将其发送至`Kafka`

## 如何获取 Jar

我们在发行版中已经打包了`Flume v1.11.0`的主流`JDK`（11、17、21）版本，配套的驱动为`MySQL`。

如果您有自定义打包的需求，请参照以下内容：

1. 将项目克隆至本地，更改[`build.gradle.kts`[9]](build.gradle.kts)中的`targetJDK`为您的`JDK`版本

   ```kotlin
      val targetJDK = Version
   ```

2. 运行`Gradel`任务中的`build/build`
3. 在`build/libs`中找到生成的`Jar`包
