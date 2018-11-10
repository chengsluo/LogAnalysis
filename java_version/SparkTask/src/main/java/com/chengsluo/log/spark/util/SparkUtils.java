package com.chengsluo.log.spark.util;

import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SparkUtils {

    public static void wordCountMerge(SparkSession se, String tmp, String dest) {
        se.read().parquet(tmp).coalesce(1).write().mode(SaveMode.Overwrite).json(dest);
        deleteDir(new File(tmp));
    }

    public static void logCountMerge(SparkSession se, String tmp, String dest) {
        se.read().csv(tmp).coalesce(1).write().mode(SaveMode.Overwrite).csv(dest);
        deleteDir(new File(tmp));
    }

    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                if (!deleteDir(new File(dir, children[i]))) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

    public static boolean haveKeyWord(String url) {
        if (url.contains("www.baidu.com") || url.contains("m.baidu.com")) {
            int begin = url.indexOf("&wd=");
            if (begin != -1) {
                int end = url.indexOf("&", begin + 4);
                if (end > begin + 5) {
                    return true;
                }
            }
        }
        return false;
    }

    public static String getKeyWord(String url) throws UnsupportedEncodingException {
        int begin = url.indexOf("&wd=");
        int end = url.indexOf("&", begin + 4);
        return URLDecoder.decode(url.substring(begin + 4, end).replaceAll("%(?![0-9a-fA-F]{2})", "%25"), "utf-8");
    }

    public static boolean havePaper(String url) {
        if (url.contains("xueshu.baidu.com") && url.contains("&sc_ks_para=q%3D")) {
            int begin = url.indexOf("&sc_ks_para=q%3D");
            if (begin != -1) {
                int end = url.indexOf("&", begin + 16);
                if (end > begin + 16) {
                    return true;
                }
            }
        }
        return false;
    }

    public static String getPaper(String url) throws UnsupportedEncodingException {
        int begin = url.indexOf("&sc_ks_para=q%3D");
        int end = url.indexOf("&", begin + 16);
        return URLDecoder.decode(url.substring(begin + 16, end).replaceAll("%(?![0-9a-fA-F]{2})", "%25"), "utf-8");
    }


    public static boolean haveResearch(String url) {
        if (!url.contains("xueshu.baidu.com/s?wd=paperuri")) {
            int begin = url.indexOf("xueshu.baidu.com/s?wd=");
            if (begin == 0) {
                int end = url.indexOf("&", 22);
                if (end > begin + 22) {
                    return true;
                }
            }
        }
        return false;
    }

    public static String getResearch(String url) throws UnsupportedEncodingException {
        int end = url.indexOf("&", 22);
        return URLDecoder.decode(url.substring(22, end).replaceAll("%(?![0-9a-fA-F]{2})", "%25"), "utf-8");
    }

    public static boolean haveComSite(String url) {
        if (url.contains(".com")) {
            Matcher matcher = Pattern.compile("[a-z0-9]+\\.com").matcher(url);
            if (matcher.find()) {
                return true;
            }
        }
        return false;
    }

    public static String getComSite(String url) {
        Matcher matcher = Pattern.compile("[a-z0-9]+\\.com").matcher(url);
        if (matcher.find()) {
            return matcher.group().replaceAll(".com","");
        } else {
            return "null";
        }
    }
}
