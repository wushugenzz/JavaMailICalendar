package com.mail.icalendar.model.dto;

import lombok.Data;

import java.util.concurrent.TimeUnit;

/**
 * @author wsg
 * @date 2022/1/12 14:55
 */
@Data
public class ScheduleNoticeDto {
    /**
     * 通知提前时间单位
     **/
    private TimeUnit noticeUnit;
    /**
     * 通知提前时间值
     **/
    private int noticeTotalVal;
}
