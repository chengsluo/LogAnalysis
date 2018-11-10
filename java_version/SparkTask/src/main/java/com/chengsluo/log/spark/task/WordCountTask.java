package com.chengsluo.log.spark.task;

import com.chengsluo.log.spark.model.LogRecord;
import com.chengsluo.log.spark.model.WordCount;
import com.chengsluo.log.spark.util.SparkUtils;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.sql.*;
import org.springframework.stereotype.Service;
import scala.Tuple2;

import static org.apache.spark.sql.functions.col;
import java.io.File;
import java.sql.Date;
import java.util.List;


@Service
public class WordCountTask {

    public void baiduSearch(SparkSession se, String parquetPath, String tmpPath, String resPath) throws AnalysisException {
        String tmp = tmpPath + File.separator + "baiduSearch";
        String res = resPath + File.separator + "baiduSearch";
        Dataset<LogRecord> ds = se.read().parquet(parquetPath).as(Encoders.bean(LogRecord.class));
        ds.createTempView("logs");
        Dataset<Row> result = se.sql("SELECT distinct date FROM logs");
        List<Row> rowList = result.collectAsList();
        for (Row row : rowList) {
            Date date = row.getDate(0);
            JavaRDD<WordCount> rdd=ds.filter(col("date").equalTo(date))
                    .select("url").toJavaRDD()
                    .filter((Function<Row, Boolean>) v1 -> SparkUtils.haveKeyWord(v1.getString(0)))
                    .mapToPair((PairFunction<Row, String, Long>) row1 ->
                            (Tuple2<String, Long>) new Tuple2<>(SparkUtils.getKeyWord(row1.getString(0)), 1L))
                    .reduceByKey((a, b) -> a + b)
                    .filter((Function<Tuple2<String, Long>, Boolean>) v1 -> v1._2>2)
                    .map((Function<Tuple2<String,Long>, WordCount>) v1 ->
                            new WordCount(date,v1._2,v1._1,"search"));
            se.createDataFrame(rdd,WordCount.class).write().mode(SaveMode.Append).parquet(tmp);
        }
        SparkUtils.wordCountMerge(se, tmp, res);
    }
    public void paperSearch(SparkSession se, String parquetPath, String tmpPath, String resPath) throws AnalysisException {
        String tmp = tmpPath + File.separator + "paperSearch";
        String res = resPath + File.separator + "paperSearch";
        Dataset<LogRecord> ds = se.read().parquet(parquetPath).as(Encoders.bean(LogRecord.class));
        ds.createTempView("logs");
        Dataset<Row> result = se.sql("SELECT distinct date FROM logs");
        List<Row> rowList = result.collectAsList();

        for (Row row : rowList) {
            Date date = row.getDate(0);
            JavaRDD<WordCount> rdd=ds.filter(col("date").equalTo(date))
                    .select("url").toJavaRDD()
                    .filter((Function<Row, Boolean>) v1 -> SparkUtils.havePaper(v1.getString(0)))
                    .mapToPair((PairFunction<Row, String, Long>) row1 ->
                            (Tuple2<String, Long>) new Tuple2<>(SparkUtils.getPaper(row1.getString(0)), 1L))
                    .reduceByKey((a, b) -> a + b)
                    .filter((Function<Tuple2<String, Long>, Boolean>) v1 -> v1._2>1)
                    .mapToPair((PairFunction<Tuple2<String, Long>, Long, String>) stringLongTuple2 ->
                            (Tuple2<Long, String>) new Tuple2<>(stringLongTuple2._2, stringLongTuple2._1))
                    .map((Function<Tuple2<Long, String>, WordCount>) v1 ->
                            new WordCount(date,v1._1,v1._2,"paper"));

            se.createDataFrame(rdd,WordCount.class).write().mode(SaveMode.Append).parquet(tmp);
        }
        SparkUtils.wordCountMerge(se, tmp, res);
    }
    public void researchSearch(SparkSession se, String parquetPath, String tmpPath, String resPath) throws AnalysisException {
        String tmp = tmpPath + File.separator + "researchSearch";
        String res = resPath + File.separator + "researchSearch";
        Dataset<LogRecord> ds = se.read().parquet(parquetPath).as(Encoders.bean(LogRecord.class));
        ds.createTempView("logs");
        Dataset<Row> result = se.sql("SELECT distinct date FROM logs");
        List<Row> rowList = result.collectAsList();

        for (Row row : rowList) {
            Date date = row.getDate(0);
            JavaRDD<WordCount> rdd=ds.filter(col("date").equalTo(date))
                    .select("url").toJavaRDD()
                    .filter((Function<Row, Boolean>) v1 -> SparkUtils.haveResearch(v1.getString(0)))
                    .mapToPair((PairFunction<Row, String, Long>) row1 ->
                            (Tuple2<String, Long>) new Tuple2<>(SparkUtils.getResearch(row1.getString(0)), 1L))
                    .reduceByKey((a, b) -> a + b)
                    .filter((Function<Tuple2<String, Long>, Boolean>) v1 -> v1._2>1)
                    .mapToPair((PairFunction<Tuple2<String, Long>, Long, String>) stringLongTuple2 ->
                            (Tuple2<Long, String>) new Tuple2<>(stringLongTuple2._2, stringLongTuple2._1))
                    .map((Function<Tuple2<Long, String>, WordCount>) v1 ->
                            new WordCount(date,v1._1,v1._2,"research"));
            se.createDataFrame(rdd,WordCount.class).write().mode(SaveMode.Append).parquet(tmp);
        }
        SparkUtils.wordCountMerge(se, tmp, res);
    }

    public void comSite(SparkSession se, String parquetPath, String tmpPath, String resPath) throws AnalysisException {
        String tmp = tmpPath + File.separator + "comSite";
        String res = resPath + File.separator + "comSite";
        Dataset<LogRecord> ds = se.read().parquet(parquetPath).as(Encoders.bean(LogRecord.class));
        ds.createTempView("logs");
        Dataset<Row> result = se.sql("SELECT distinct date FROM logs");
        List<Row> rowList = result.collectAsList();
        for (Row row : rowList) {
            Date date = row.getDate(0);
            JavaRDD<WordCount> rdd=ds.filter(col("date").equalTo(date))
                    .select("url").toJavaRDD()
                    .filter((Function<Row, Boolean>) v1 -> SparkUtils.haveComSite(v1.getString(0)))
                    .mapToPair((PairFunction<Row, String, Long>) row1 ->
                            (Tuple2<String, Long>) new Tuple2<>(SparkUtils.getComSite(row1.getString(0)), 1L))
                    .reduceByKey((a, b) -> a + b)
                    .filter((Function<Tuple2<String, Long>, Boolean>) v1 -> v1._2>1000)
                    .mapToPair((PairFunction<Tuple2<String, Long>, Long, String>) stringLongTuple2 ->
                            (Tuple2<Long, String>) new Tuple2<>(stringLongTuple2._2, stringLongTuple2._1))
                    .map((Function<Tuple2<Long, String>, WordCount>) v1 ->
                            new WordCount(date,v1._1,v1._2,"com"));
            se.createDataFrame(rdd,WordCount.class).write().mode(SaveMode.Append).parquet(tmp);
        }
        SparkUtils.wordCountMerge(se, tmp, res);
    }
}
