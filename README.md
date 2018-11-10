# 利用Spark Java API和Spring Boot进行简单的日志分析

## 功能
1. 利用路由器日志进行用户网站访问统计；
2. 利用WordCount提取门户网站的访问排名等；

## 主要技术方案
0. scala版本仅仅用来做测试用；
1. 利用Java脚本对各个zip数据压缩包进行解压缩，并聚合压缩成效率更高的parquet格式;
2. 利用Spark的Java API进行Map、Reduce等操作来获取信息；

## 注意事项：
0. 代码仅供参考，不保证能在任何用户的环境中跑起来；
1. 代码中数据样例占用内存较大，下载时可能需要一定时间；
2. SparkTask中结果会输出CSV文件中;
3. 将CSV文件导入到数据库中，利用WebService给展示界面提供Web API；