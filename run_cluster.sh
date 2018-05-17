#!/usr/bin/env bash
/home/hadoop/spark2/bin/spark-submit \
--master yarn \
--deploy-mode cluster \
--num-executors 4 \
--driver-memory 4g \
--executor-memory 12g \
--executor-cores 6 \
loganalysis_2.11-0.1.jar \
/user/chengsluo/Log/ \
/user/chengsluo/Data/



/home/hadoop/spark2/bin/spark-shell --master yarn --deploy-mode client --num-executors 39  --driver-memory 2g --executor-memory 1g --executor-cores 1
