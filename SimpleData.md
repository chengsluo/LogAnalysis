## 数据概要
|      date|   count|
|--------|----------|
|2017-10-26|19446455|
|2017-11-03|46666021|
|2017-11-02|  110905|
|2017-10-25|   30204|

### 数据描述

几个服务器的400多亿条上网日志

数据疑问:

1. 什么地方的服务器:
1. 上网记录是否完整:
1. 上大的网络结构是什么:
1. 这是哪一部分的上网记录,HTTP ? GET ? or Others?
1. 每个房间的内网IP是否是固定的
1. forall是通过怎么连的
1. 校内以太网是怎么连的
1. 外网网段是什么？
1. 内网内配的大致网段的什么？
1. ctcpppoe , ShuWlan , test , teacher代表什么？
1. 时间是什么时间，请求完成,还是请求开始？

### 推测字段名

```
t1:Int,
t2:Int,
date:Date,
t3:String,
time:String,
targetUrl:String,
JoinWay:String,
MAC:String,
joinIP:String,
targetPort:Int,
t4:Int,
targetIP:String,
userPort:Int,
t5:Int,
mask:String,
t6:Int
```

字段描述:
```shell
t1:未知,几乎全为0;
t2:未知,几乎全为0;
date:不确定,格式为yyyy-mm-dd
t3:不确定，如(3)
time:不确定,格式为hh:mm:ss
targetUrl:访问的URl
JoinWay:访问的网络途径,只有少数几种,不完全统计为:{ctcpppoe , ShuWlan , test , teacher }
MAC:不确定,也许是路由器的MAC地址,只有少数几种,不完全统计为:{B0A86EF7454A,,}
joinIP:访问者被分配的IP地址,部分为局域网的IP地址，部分为属于上海大学的公网IP,需要写代码分析一下
targetPort:访问Url的哪个端口号，因为多为80和443
t4:有1,2,3三种标记,未知
targetIP:Url被解析到的IP地址
userPort:不确定,应该是用户的端口号
t5:未知,几乎全为0
mask:不确定,几乎全为0,0,0,0
t6:未知,几乎全为0
```

## 10条数据示例


```shell
0	0	2017-10-25 (3) 23:59:55	dl.op.wpscdn.cn/odimg/web/2017-10-13/094216/%E7%AB%A0%E5%AD%90%E6%80%A1_%E5%89%AF%E6%9C%AC180-110.jpg	ShuWlan	B0A86EF7454A	10.88.1.206	80	2	122.205.110.2	56321	0	0.0.0.0	0
0	0	2017-10-25 (3) 23:59:55	sqt.gtimg.cn/app%3Dmacqq%26sdk_version%3D1.1.1%26os_version%3D%E7%89%88%E6%9C%AC%2010.12.5%EF%BC%88%E7%89%88%E5%8F%B7%2016F73%EF%BC%89%26fmt%3Djson%26q%3Dsh0000	ShuWlan	B0A86EF7454A	10.89.1.5	80	2	182.254.38.19	58150	0	0.0.0.0	0
0	0	2017-10-25 (3) 23:59:55	60.205.120.164	ctcpppoe	B0A86EF7454A	10.90.45.51	80	1	60.205.120.164	29559	0	0.0.0.0	0
0	0	2017-10-25 (3) 23:59:55	139.224.234.172	ShuWlan	B0A86EF7454A	10.95.214.248	8888	1	139.224.234.172	35991	0	0.0.0.0	0
0	0	2017-10-25 (3) 23:59:55	64.233.189.113	test	58696C922108	58.198.76.244	443	1	64.233.189.113	2833	0	0.0.0.0	0
0	0	2017-10-25 (3) 23:59:55	ifzq.gtimg.cn/appstock/app/minute/query?app=macqq&sdk_version=1.1.1&p=1&code=sh000001&os_version=%E7%89%88%E6%9C%AC%2010.12.5%EF%BC%88%E7%89%88%E5%8F%B7%2016F73	ShuWlan	B0A86EF7454A	10.89.1.5	80	2	182.254.78.34	58157	0	0.0.0.0	0
0	0	2017-10-25 (3) 23:59:55	music.163.com/api/feedback/client/log?MUSIC_U=9de0b492647e466127f8b29aebb61fa0cfb18a9920fb0683c2a0c2afda68d5a5ea8b908a798b15b0e6edcfc687655cf56edc39e57de913c50f	ShuWlan	B0A86EF7454A	10.95.217.209	80	3	103.211.228.142	53989	0	0.0.0.0	0
0	0	2017-10-25 (3) 23:59:55	103.211.228.143	ShuWlan	B0A86EF7454A	10.95.217.209	80	1	103.211.228.143	53995	0	0.0.0.0	0
0	0	2017-10-25 (3) 23:59:55	153.3.236.34	ctcpppoe	B0A86EF7454A	10.90.56.89	80	1	153.3.236.34	57802	0	0.0.0.0	0
0	0	2017-10-25 (3) 23:59:55	123.125.142.29	ctcpppoe	B0A86EF7454A	10.90.56.89	80	1	123.125.142.29	57803	0	0.0.0.0	0
```