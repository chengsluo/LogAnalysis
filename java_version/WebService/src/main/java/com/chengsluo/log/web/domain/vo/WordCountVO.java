package com.chengsluo.log.web.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WordCountVO {
    String text;
    Integer value;
}
