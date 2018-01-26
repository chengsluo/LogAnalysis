# 项目编译运行步骤

## 本地运行调试

sbt clean package &&\
\
~/tmp/spark-2.2.0-bin-hadoop2.7/bin/spark-submit \
--master local[2] \
\
target/scala-2.11/\
log-analysis_2.11-1.0.jar \
\
~/data/shakespeare.txt \
~/tmp/wordCount 

## 删除结果目录

sudo rm -r \
~/tmp/wordCount

=============远程测试===============

## 上传data目录和jar包到master,并且上传到hdfs

scp -r ~/data/shakespeare.txt \
root@master:~/data/ &&\
scp target/scala-2.10/\
scala-cluster-test_2.10-1.0.jar root@master:~ &&\
ssh root@master 

hadoop fs -put ~/data/ /user/root/ 

## 在master上运行任务

注意:重复提交需要将结果文件夹清零
hadoop fs -rm -r /user/root/result/wordCount

spark-submit \
--master yarn \
--deploy-mode cluster \
--executor-memory 1g \
--executor-cores 2 \
\
log-analysis_2.11-1.0.jar \
\
hdfs:///user/root/data/shakespeare.txt \
hdfs:///user/root/result/wordCount 



## 查看结果

hadoop fs -get /user/root/result/wordCount




============初始配置=================

## 本地安装好对应版本Spark

## 配置好环境变量

export SPARK_HOME=/opt/spark-2.2.0-bin-hadoop2.7
export PATH=$PATH:$SPARK_HOME/bin
export PYSPARK_PYTHON=python3

## 利用官网安装sbt

sudo apt-get install sbt

## 用sbt手动编译过程

sbt clean package