#!/usr/bin/env sh

sbt package &&\
../spark-2.2.1-bin-hadoop2.6/bin/spark-submit \
--master local[2] \
target/scala-2.11/loganalysis_2.11-0.1.jar \
../dataset/original-zip/   \
../dataset/processing/