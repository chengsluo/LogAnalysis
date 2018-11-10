package com.chengsluo.log.web.repository;

import com.chengsluo.log.web.domain.model.WordCountResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;

public interface WordCountResultRepository extends JpaRepository<WordCountResult,Integer> {

    @Query(value = "select w.word,w.counter from WordCountResult w where w.type='search' and w.date=:date order by  w.counter desc ")
    public List<Object[]> baiduWordCount(@Param("date") Date date);


    @Query(value = "select w.word,w.counter from WordCountResult w where w.type='paper' and w.date=:date order by  w.counter desc ")
    public List<Object[]> paperWordCount(@Param("date") Date date);

    @Query(value = "select w.word,w.counter from WordCountResult w where w.type='research' and w.date=:date order by  w.counter desc ")
    public List<Object[]> researchWordCount(@Param("date") Date date);

    @Query(value = "select w.word,w.counter from WordCountResult w where w.type='com' and w.date=:date order by  w.counter desc ")
    public List<Object[]> comWordCount(@Param("date") Date date);


    @Query(value = "select w.word,sum(w.counter) from WordCountResult w where w.type='com' group by w.word order by sum(w.counter) DESC ")
    public List<Object[]> getCom();

}

