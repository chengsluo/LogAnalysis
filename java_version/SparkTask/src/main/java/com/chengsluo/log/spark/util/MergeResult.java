package com.chengsluo.log.spark.util;

import com.chengsluo.log.spark.model.WordCount;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.FilterFunction;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;
import scala.Function1;

import java.io.File;
import java.net.URLDecoder;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Properties;

import static org.apache.spark.sql.functions.col;

public class MergeResult {

    public static void wordCountMerge(SparkSession se, String tmpPath, String resPath, String taskName) {
        String res = resPath + File.separator + taskName.replace("Merge", "");
        String tmp1 = tmpPath + File.separator + "res1" + File.separator + taskName.replace("Merge", "");
        String tmp2 = tmpPath + File.separator + "res2" + File.separator + taskName.replace("Merge", "");
        String tmp3 = tmpPath + File.separator + "res3" + File.separator + taskName.replace("Merge", "");
        String tmp = tmpPath + File.separator + taskName.replace("Merge", "");

//        Properties user = new Properties();
//        user.put("user", "root");
//        user.put("password", "12345");
//        SimpleDateFormat datedf = new SimpleDateFormat("yyyy-MM-dd");
//                .jdbc("jdbc:mysql://127.0.0.1:3306/logdb?useUnicode=true&characterEncoding=utf8",
//                "logdb.word_count_result", user);



//                        .toDF().filter((FilterFunction<Row>) value -> {
//            String str = value.getString(3);
//            return !(str.length() < 5 && str.matches("[\\.a-z0-9]+")) && !(str.length() > 4 && str.substring(0, 4).equals("http"));
//        })


//                        .filter(col("counter").gt(10000))


        se.read().json(tmp1).coalesce(1).write().mode(SaveMode.Overwrite).json(tmp);
        se.read().json(tmp2).coalesce(1).write().mode(SaveMode.Append).json(tmp);
        se.read().json(tmp3).coalesce(1).write().mode(SaveMode.Append).json(tmp);

        se.read().json(tmp).coalesce(1)
                .write().mode(SaveMode.Overwrite).csv(res);

    }

    public static void logCountMerge(SparkSession se, String tmpPath, String resPath, String taskName) {
        String res = resPath + File.separator + taskName.replace("Merge", "");
        String tmp1 = tmpPath + File.separator + "res1" + File.separator + taskName.replace("Merge", "");
        String tmp2 = tmpPath + File.separator + "res2" + File.separator + taskName.replace("Merge", "");
        String tmp3 = tmpPath + File.separator + "res3" + File.separator + taskName.replace("Merge", "");
        String tmp = tmpPath + File.separator + taskName.replace("Merge", "");
        se.read().csv(tmp1).coalesce(1).write().mode(SaveMode.Overwrite).csv(tmp);
        se.read().csv(tmp2).coalesce(1).write().mode(SaveMode.Append).csv(tmp);
        se.read().csv(tmp3).coalesce(1).write().mode(SaveMode.Append).csv(tmp);
        se.read().csv(tmp).coalesce(1).write().mode(SaveMode.Append).csv(res);
        SparkUtils.deleteDir(new File(tmp));
    }
}
