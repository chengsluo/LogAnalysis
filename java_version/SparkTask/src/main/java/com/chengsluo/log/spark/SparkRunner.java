package com.chengsluo.log.spark;

import com.chengsluo.log.spark.task.DataFormatTask;
import com.chengsluo.log.spark.task.LogCounterTask;
import com.chengsluo.log.spark.task.WordCountTask;

import com.chengsluo.log.spark.util.MergeResult;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SparkRunner implements CommandLineRunner {
    @Autowired
    DataFormatTask dataFormatTask;
    @Autowired
    LogCounterTask logCounterTask;
    @Autowired
    WordCountTask wordCountTask;


    public static void main(String[] args) {
        SpringApplication.run(SparkRunner.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        SparkSession spark = SparkSession
                .builder()
                .master("local[*]")
                .appName("Spark Task for Log Analysis")
                .config("spark.eventLog.enabled", true)
                .config("spark.hadoop.validateOutputSpecs", false)
                .config("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
                .getOrCreate();
        String taskName = args[0];
        String originPath = args[1];
        String parquetPath = args[2];
        String resPath = args[3];
        String tmpPath = args[4];
        if (taskName.equals("parquet")) {
            dataFormatTask.parquetZip(spark, parquetPath, originPath, tmpPath);
        } else if (taskName.equals("minuteCount")) {
            logCounterTask.minuteCount(spark, parquetPath, tmpPath, resPath);
        } else if (taskName.equals("baiduSearch")) {
            wordCountTask.baiduSearch(spark, parquetPath, tmpPath, resPath);
        } else if (taskName.equals("paperSearch")) {
            wordCountTask.paperSearch(spark, parquetPath, tmpPath, resPath);
        } else if (taskName.equals("researchSearch")) {
            wordCountTask.researchSearch(spark, parquetPath, tmpPath, resPath);
        } else if (taskName.equals("minuteCountMerge")) {
            MergeResult.logCountMerge(spark, tmpPath, resPath, taskName);
        }else if(taskName.equals("baiduSearchMerge")){
            MergeResult.wordCountMerge(spark, tmpPath, resPath, taskName);
        }else if(taskName.equals("paperSearchMerge")){
            MergeResult.wordCountMerge(spark, tmpPath, resPath, taskName);
        }else if(taskName.equals("researchSearchMerge")){
            MergeResult.wordCountMerge(spark, tmpPath, resPath, taskName);
        }else if(taskName.equals("comSite")){
            wordCountTask.comSite(spark, parquetPath, tmpPath, resPath);
        }else if(taskName.equals("comSiteMerge")){
            MergeResult.wordCountMerge(spark, tmpPath, resPath, taskName);
        }
    }
}
