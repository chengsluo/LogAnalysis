package com.chengsluo.log.spark.task;

import com.chengsluo.log.spark.util.ConstUtil;
import com.chengsluo.log.spark.model.LogRecord;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.sql.*;
import java.io.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import org.springframework.stereotype.Service;


@Service
public class DataFormatTask {

    private static int packageSize = 100;

    public void parquetZip(SparkSession se, String dest, String pathName, String tmpDir) throws IOException {
        File dirFile = new File(pathName);
        if (!dirFile.exists()) {
            System.out.println("do not exit");
            return;
        }
        if (!dirFile.isDirectory()) {
            if (dirFile.isFile()) {
                System.out.println(dirFile.getCanonicalFile());
            }
            return;
        }
        String[] fileList = dirFile.list();
        Stream.of(fileList).forEach(
                s -> {
                    try {
                        parquetDir(se, dest, pathName + File.separator + s, tmpDir + File.separator + s);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
    }

    public void parquetDir(SparkSession se, String dest, String pathName, String tmpDir) throws IOException {
        String tmpPath = tmpDir + File.separator + "tmp_data";
        File destDir = new File(dest);
        File tmpDirFile = new File(tmpDir);
        if (!destDir.exists()) {
            boolean result = destDir.mkdirs();
            if (!result) {
                System.out.println("创建目标路径失败");
            }
        }
        if (!tmpDirFile.exists()) {
            boolean result = tmpDirFile.mkdirs();
            if (!result) {
                System.out.println("创建目标路径失败");
            }
        }
        File dataDirFile = new File(pathName);
        File tmpFile = new File(tmpPath);

        String[] fileList = dataDirFile.list();
        int tmpCounter = 0;
        JavaRDD<LogRecord> logRDD;
        Writer out = new FileWriter(tmpFile);
        BufferedWriter bw = new BufferedWriter(out);

        String line;
        File f;
        ZipFile zf;
        ZipInputStream zin;
        BufferedReader br;

        for (int i = 0; i < fileList.length + 1; i++) {
            if ((tmpCounter + 1) % packageSize == 0 || tmpCounter == fileList.length) {
                bw.close();
                out.close();
                System.out.println(tmpPath + " finish！ID:" + i);
                logRDD = getLogRecordJavaRDDFromTextFile(se, tmpPath);
                Dataset<Row> recodeDS = se.createDataFrame(logRDD, LogRecord.class);
                recodeDS.write()
                        .mode(SaveMode.Append)
                        .partitionBy("week", "date", "hour")
                        .parquet(dest);

                if (tmpCounter != fileList.length) {
                    out = new FileWriter(tmpFile);
                    bw = new BufferedWriter(out);
                } else {
                    break;
                }
            }
            f = new File(pathName, fileList[i]);
            zf = new ZipFile(f);
            zin = new ZipInputStream(new BufferedInputStream(new FileInputStream(f)));
            ZipEntry ze;
            System.out.println("read zip " + pathName + File.separator + fileList[i]);
            while ((ze = zin.getNextEntry()) != null) {
                br = new BufferedReader(
                        new InputStreamReader(zf.getInputStream(ze)));
                while ((line = br.readLine()) != null) {
                    bw.write(line);
                    bw.newLine();
                }
                br.close();
            }
            zin.closeEntry();
            tmpCounter++;
        }
        out = new FileWriter(tmpFile);
        bw = new BufferedWriter(out);
        out.close();
        bw.close();
    }

    private static JavaRDD<LogRecord> getLogRecordJavaRDDFromTextFile(SparkSession se, String pathName) {
        SimpleDateFormat datedf = new SimpleDateFormat("yyyy-MM-dd");
        JavaRDD<LogRecord> logRDD;
        logRDD = se.read()
                .textFile(pathName)
                .javaRDD()
                .filter(s -> s.split("\t| ").length == 16)
                .map((Function<String, LogRecord>) v1 -> {
                            String[] lists = v1.split("\t| ");
                            String[] time = lists[4].split(":");
                            LogRecord logRecord = new LogRecord();
                            logRecord.setDate(new Date(datedf.parse(lists[2]).getTime()));
                            logRecord.setWeek(ConstUtil.getWeekday(lists[3]));
                            logRecord.setHour(Short.valueOf(time[0]));
                            logRecord.setMinute(Short.valueOf(time[1]));
                            logRecord.setSecond(Short.valueOf(time[2]));
                            logRecord.setUrl(lists[5]);
                            logRecord.setWay(ConstUtil.getWayNumber(lists[6]));
                            logRecord.setRouter(ConstUtil.getRouterNumber(lists[7]));
                            logRecord.setUserIp(lists[8]);
                            logRecord.setUrlPort(Integer.valueOf(lists[9]));
                            logRecord.setCate(ConstUtil.getCateNumber(lists[10]));
                            logRecord.setUrlIp(lists[11]);
                            logRecord.setUserPort(Integer.valueOf(lists[12]));
                            return logRecord;
                        }
                );
        return logRDD;
    }
}
