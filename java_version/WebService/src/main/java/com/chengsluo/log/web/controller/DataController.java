package com.chengsluo.log.web.controller;

import com.chengsluo.log.web.domain.Response;
import com.chengsluo.log.web.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.text.ParseException;

@CrossOrigin
@RestController
@RequestMapping("/data")
public class DataController {
    @Autowired
    DataService dataService;

    @GetMapping("/findDateAndCount")
    Response findDateAndCount() {
        return Response.ok(dataService.findDateAndCount());
    }

    @GetMapping("/total")
    String total() throws ParseException {
        return dataService.total().toString();
    }

    @GetMapping("/findDateAndWayAndCount")
    Response getDateAndCount() {
        return Response.ok(dataService.findDateAndWayAndCount());
    }

    @GetMapping("/baiduWordCount/{date}")
    Response baiduWordCount(@PathVariable("date") Date date) {
        return Response.ok(dataService.baiduWordCount(date));
    }

    @GetMapping("/paperWordCount/{date}")
    Response paperWordCount(@PathVariable("date") Date date) {
        return Response.ok(dataService.paperWordCount(date));
    }

    @GetMapping("/researchWordCount/{date}")
    Response researchWordCount(@PathVariable("date") Date date) {
        return Response.ok(dataService.researchWordCount(date));
    }

    @GetMapping("/comWordCount/{date}")
    Response comWordCount(@PathVariable("date") Date date) {
        return Response.ok(dataService.comWordCount(date));
    }


    @GetMapping("/getOneDay")
    Response getOneDay() {
        return Response.ok(dataService.getOneDay());
    }

    @GetMapping("/getPie")
    Response getPie() {
        return Response.ok(dataService.getPie());
    }


}
