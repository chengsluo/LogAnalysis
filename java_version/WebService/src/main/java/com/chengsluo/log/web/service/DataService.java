package com.chengsluo.log.web.service;

import com.chengsluo.log.web.domain.vo.PieVO;
import com.chengsluo.log.web.domain.vo.WordCountVO;
import com.chengsluo.log.web.repository.MinuteRecordRepository;
import com.chengsluo.log.web.repository.WordCountResultRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class DataService {
    @Autowired
    MinuteRecordRepository minuteRecordRepository;
    @Autowired
    WordCountResultRepository wordCountResultRepository;

    public Object findDateAndCount() {
        return minuteRecordRepository.findDateAndCount();
    }

    public JSONArray total() throws ParseException {
        JSONArray res = new JSONArray();
        List<Object[]> dateAndCount = minuteRecordRepository.findDateAndCount();
        Calendar c = Calendar.getInstance();
        DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date beginDate = dateFormat1.parse("2017-07-14");
        java.util.Date endDate = dateFormat1.parse("2017-12-31");
        java.util.Date date = beginDate;
        int i = 0;
        while (!date.equals(endDate)) {
            if (i < dateAndCount.size()) {
                Object[] resItems = dateAndCount.get(i);
                Date d = (Date) resItems[0];
                if (d.equals(new Date(date.getTime()))) {
                    i++;
                    res.put(new JSONObject().put("date", d).put("count", (Long) resItems[1]));
                } else {
                    res.put(new JSONObject().put("date", new Date(date.getTime())));
                }
            } else {
                res.put(new JSONObject().put("date", new Date(date.getTime())));
            }
            c.setTime(date);
            c.add(Calendar.DATE, 1); // 日期加1天
            date = c.getTime();
        }
        return res;
    }

    public Object findDateAndWayAndCount() {
        return minuteRecordRepository.findDateAndWayAndCount();
    }


    public List<WordCountVO> baiduWordCount(Date date) {
        List<Object[]> res = wordCountResultRepository.baiduWordCount(date);
        List<WordCountVO> wc = new ArrayList<>();
        for (Object[] items : res) {
            wc.add(new WordCountVO(items[0].toString(), (Integer) items[1]));
        }
        return wc;
    }

    public List<WordCountVO> paperWordCount(Date date) {
        List<Object[]> res = wordCountResultRepository.paperWordCount(date);
        List<WordCountVO> wc = new ArrayList<>();
        for (Object[] items : res) {
            wc.add(new WordCountVO(items[0].toString(), (Integer) items[1]));
        }
        return wc;
    }

    public List<WordCountVO> researchWordCount(Date date) {
        List<Object[]> res = wordCountResultRepository.researchWordCount(date);
        List<WordCountVO> wc = new ArrayList<>();
        for (Object[] items : res) {
            wc.add(new WordCountVO(items[0].toString(), (Integer) items[1]));
        }
        return wc;
    }

    public List<WordCountVO> comWordCount(Date date) {
        List<Object[]> res = wordCountResultRepository.comWordCount(date);
        List<WordCountVO> wc = new ArrayList<>();
        for (Object[] items : res) {
            wc.add(new WordCountVO(items[0].toString(), (Integer) items[1]));
        }
        return wc;
    }

    public List<?> getOneDay() {
        return minuteRecordRepository.getOneDay();
    }

    public List<PieVO> getPie() {
        String total = (minuteRecordRepository.getLogNumber()).toString();
        List<Object[]> list = wordCountResultRepository.getCom();
        List<PieVO> res = new ArrayList<>();
        Long ct = 0L;
        for (int i = 0; i < list.size() && i < 20; i++) {
            res.add(new PieVO((list.get(i)[0]).toString(), (Long) (list.get(i)[1])));
            ct += (Long) (list.get(i)[1]);
        }
        res.add(new PieVO("其他", Long.parseLong(total) / 7 - ct));
        return res;
    }
}
