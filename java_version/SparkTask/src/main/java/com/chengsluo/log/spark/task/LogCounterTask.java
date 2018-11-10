package com.chengsluo.log.spark.task;

import com.chengsluo.log.spark.util.SparkUtils;
import org.apache.spark.sql.*;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class LogCounterTask
{
    public void minuteCount(SparkSession se, String parquetPath, String tmpPath, String resPath) throws AnalysisException {
        String tmp=tmpPath+ File.separator+"minuteCount";
        String res=resPath+ File.separator+"minuteCount";
        Dataset<Row> ds = se.read().parquet(parquetPath);
        ds.createTempView("logs");
        Dataset<Row> result=se.sql("SELECT  week,date,hour,minute,way,count(1) FROM logs WHERE GROUP BY week,date,hour,minute,way");
        result.write().mode(SaveMode.Overwrite).csv(tmp);
        SparkUtils.logCountMerge(se,tmp,res);
    }
}
