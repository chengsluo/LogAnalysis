package com.chengsluo.log.web.domain.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Entity
@Data
public class MinuteRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer week;
    private Date date;
    private Integer hour;
    private Integer minute;
    private Integer way;
    private Integer count;
}
