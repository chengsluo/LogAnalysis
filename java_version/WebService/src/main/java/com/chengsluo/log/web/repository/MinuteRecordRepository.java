package com.chengsluo.log.web.repository;

import com.chengsluo.log.web.domain.model.MinuteRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;

public interface MinuteRecordRepository extends JpaRepository<MinuteRecord,Integer> {
    @Query(value = "select m.date,sum(m.count) as cnt from MinuteRecord m group by m.date")
    public List<Object[]>  findDateAndCount();


    @Query(value = "select m.date,m.way,sum(m.count) as cnt from MinuteRecord m group by m.date,m.way order by m.date")
    public List<?> findDateAndWayAndCount();

    @Query(value = "select m.hour,m.minute,sum(m.count) as cnt from MinuteRecord m group by m.hour,m.minute order by m.hour,m.minute")
    public List<?> getOneDay();


    @Query(value = "select sum(m.count)  from MinuteRecord m ")
    public Object getLogNumber();
}