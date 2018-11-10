package com.chengsluo.log.spark.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;

@Data
@AllArgsConstructor
public class WordCount {
    Date date;
    Long counter;
    String word;
    String type;
}
