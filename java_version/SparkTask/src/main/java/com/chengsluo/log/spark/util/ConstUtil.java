package com.chengsluo.log.spark.util;

public class ConstUtil {
    public static short getWayNumber(String wayName) {
        switch (wayName) {
            case "ShuWlan":
                return 1;
            case "ctcpppoe":
                return 2;
            case "teacher":
                return 3;
            case "admin":
                return 4;
            case "shucc":
                return 5;
            case "test":
                return 6;
            default:
                return 0;
        }
    }

    public static short getNetmaskNumber(String maskNumber) {
        switch (maskNumber) {
            case "0.0.0.0":
                return 1;
            default:
                return 0;
        }
    }

    public static short getRouterNumber(String router) {
        switch (router) {
            case "B0A86EF7454A":
                return 1;
            case "58696C922108":
                return 2;
            default:
                return 0;
        }
    }

    public static short getWeekday(String number) {
        switch (number) {
            case "(1)":
                return 1;
            case "(2)":
                return 2;
            case "(3)":
                return 3;
            case "(4)":
                return 4;
            case "(5)":
                return 5;
            case "(6)":
                return 6;
            case "(0)":
                return 7;
            default:
                return 0;
        }
    }

    public static short getCateNumber(String number) {
        switch (number) {
            case "1":
                return 1;
            case "2":
                return 2;
            case "3":
                return 3;
            case "4":
                return 4;
            case "5":
                return 5;
            case "0":
                return 6;
            default:
                return 0;
        }
    }
}
