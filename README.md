安装gradle
-----------------
1.\ 下载gradle并解压安装到相关目录（例如d:\tools\gradle）

2.\ 追加环境变量"GRADLE_HOME"，值为"d:\tools\gradle"（注：值为gradle安装目录）

3.\ 将"d:\tools\gradle\bin"加到环境变量"PATH"

使用gradle
-----------------------------
清除编译生成的文件
```
gradle clean
```

编译文件
```
gradle build -x test
```

打包项目
```
gradle shadowJar
```

配置项目
------------------------------
修改"gradle.properties"
"org.gradle.java.home"（指向jdk安装目录）

intellij idea 开发项目
----------------------------
1.\ 导入gradle项目


spark-submit
----------------------------
1.\ local模式
spark-submit --class com.github.laomie.mapreduce.SparkPi --master local[*] /home/laomie/hadoop-sample-all.jar 10

2.\ standalone模式
spark-submit --class com.github.laomie.mapreduce.SparkPi --master spark://localhost:7077 /home/laomie/hadoop-sample-all.jar 10

3.\ yarn模式
spark-submit --class com.github.laomie.mapreduce.SparkPi --master yarn --deploy-mode cluster /home/laomie/hadoop-sample-all.jar 10
