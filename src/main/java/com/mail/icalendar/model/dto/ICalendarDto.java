package com.mail.icalendar.model.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author wsg
 * @date 2022/1/12 14:54
 */
@Data
public class ICalendarDto implements Serializable {

    private static final long serialVersionUID = 4882323321639302536L;
    /**
     * 主题
     */
    private String title;
    /**
     * 开始时间
     */
    private Date startTime;
    /**
     * 结束时间
     */
    private Date endTime;
    /**
     * 地址
     */
    private String address;
    /**
     * 备注
     */
    private String description;
    /**
     * 提醒事件
     */
    private ScheduleNoticeDto scheduleNotice;

}
