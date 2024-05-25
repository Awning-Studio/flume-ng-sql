# Flume-ng SQL（源）

语言 [English](README.md) | **简体中文**

因为[flume-ng-sql-source](https://github.com/keedio/flume-ng-sql-source)已经不再更新，为了填补在新版本的空缺，我们开启了这个项目。

## 使用

1. [获取`Jar`包](#如何获取-jar)，导入`Flume lib`文件夹中
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
   将其发送至`Kafka` 消费者。

## 如何获取 Jar

我们在[发行版](https://github.com/Awning-Studio/flume-ng-sql/releases)中已经打包了`Flume v1.11.0`的主流`JDK`（8、11、17、21）版本。

如果您有自定义打包的需求，请参照以下内容：

1. 将项目克隆至本地。
   - 更改[`build.gradle.kts`[7]](build.gradle.kts)中`flumeVersion`
   - 保留[`build.gradle.kts`[29:32]](build.gradle.kts)中的驱动依赖。

2. 运行`Gradle`任务中的`build/jar-all`生成包含驱动的所有版本的`Jar`包，或者运行`build/jar-all-no-driver`生成不带驱动的`Jar`包。
你也可以运行对应的`build/jar[版本号]`或`build/jar[版本号]-no-driver`生成指定`JDK`版本的`Jar`包。

3. 在`build/libs`中找到生成的`Jar`包。
