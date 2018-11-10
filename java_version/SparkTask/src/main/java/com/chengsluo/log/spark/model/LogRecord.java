package com.chengsluo.log.spark.model;

import java.sql.Date;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogRecord {
    short week;
    Date date;
    short hour;
    short minute;
    short second;
    String url;
    short way;
    short router;
    String userIp;
    int urlPort;
    short cate;
    String urlIp;
    int userPort;

}
