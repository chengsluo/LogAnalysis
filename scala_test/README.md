# LogAnalysis

本项目利用Spark集群对海量日志进行分析

## 项目配置

### 组件版本

* hadoop:2.6.0
* spark:2.2.1
* scala:2.11.8

### 集群大小

Namenode:

* 20GB,24cores

Datanode×４:

* 16GB,24cores


## 本地运行调试

```shell
./run_local.sh
```

## 集群运行

### 上传必要信息

上传数据到HDFS,上传jar包和run_cluster.sh到master节点

### 登录master节点，运行任务

```shell
./run_cluster.sh
```

注意:重复提交需要将结果文件夹清零

```shell
hadoop fs -rm -r /user/root/result/wordCount
```

### 查看结果

```shell
hadoop fs -get /user/root/result/wordCount
```