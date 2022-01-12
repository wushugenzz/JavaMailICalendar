package com.mail.icalendar.model.dto;

import lombok.Getter;

import javax.mail.Address;

/**
 * @author wsg
 * @date 2022/1/12 14:56
 */
@Getter
public class ICalendarAccountDto {
    /**
     * 日程的组织者，也就是邮件发送人
     */
    private String organizerName;
    /**
     * 日程的组织者，也就是邮件发送人
     */
    private String organizerEmail;
    /**
     * 日程的参与者，也就是邮件接收者
     */
    private Address[] attendees;

    public ICalendarAccountDto(String organizerName, String organizerEmail, Address[] attendees) {
        this.organizerName = organizerName;
        this.organizerEmail = organizerEmail;
        this.attendees = attendees;
    }
}
