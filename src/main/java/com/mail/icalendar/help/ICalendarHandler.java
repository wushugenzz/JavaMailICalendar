package com.mail.icalendar.help;

import biweekly.ICalendar;
import biweekly.component.VAlarm;
import biweekly.component.VEvent;
import biweekly.parameter.CalendarUserType;
import biweekly.parameter.ParticipationStatus;
import biweekly.parameter.Related;
import biweekly.property.*;
import biweekly.util.Duration;
import com.mail.icalendar.model.dto.ICalendarAccountDto;
import com.mail.icalendar.model.dto.ICalendarDto;
import com.mail.icalendar.model.dto.ScheduleNoticeDto;
import lombok.extern.slf4j.Slf4j;

import javax.mail.Address;
import javax.mail.internet.InternetAddress;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author wsg
 * @date 2022/1/12 14:52
 */
public class ICalendarHandler {

    /**
     * 构建日程对象
     *
     * @param calendarDto
     * @param iCalendarAccountDto
     * @return
     */
    public static ICalendar buildICalendar(ICalendarDto calendarDto,
                                           ICalendarAccountDto iCalendarAccountDto) {
        String title = calendarDto.getTitle();

        ICalendar calendar = new ICalendar();
        calendar.setMethod(Method.request());
        VEvent event = new VEvent();
        event.setDateStart(calendarDto.getStartTime());
        event.setDateEnd(calendarDto.getEndTime());
        event.setCreated(new Date());
        event.setLocation(calendarDto.getAddress());
        event.setDescription(calendarDto.getDescription());
        event.setSummary(title);

        event.setClassification(Classification.public_());
        event.setOrganizer(new Organizer(iCalendarAccountDto.getOrganizerName(), iCalendarAccountDto.getOrganizerEmail()));

        if (calendarDto.getScheduleNotice() != null) {
            // 通知
            Trigger trigger = getTrigger(calendarDto.getScheduleNotice());
            VAlarm alarm = VAlarm.display(trigger, title);
            event.addAlarm(alarm);
        }
        // 多个收件人情况
        for (Address address : iCalendarAccountDto.getAttendees()) {
            InternetAddress attendeeAddress = (InternetAddress) address;

            // 接收者回复邮件，需要有attendee
            Attendee attendee = new Attendee(attendeeAddress.getPersonal(), attendeeAddress.getAddress());
            attendee.setCalendarUserType(CalendarUserType.INDIVIDUAL);
            attendee.setCommonName(attendeeAddress.getPersonal());
            attendee.setParticipationStatus(ParticipationStatus.NEEDS_ACTION);

            event.addAttendee(attendee);
        }
        calendar.addEvent(event);
        return calendar;
    }

    /**
     * 根据通知类型构建Trigger
     *
     * @param scheduleNoticesDto
     * @return
     */
    public static Trigger getTrigger(ScheduleNoticeDto scheduleNoticesDto) {
        int noticeTotalVal = scheduleNoticesDto.getNoticeTotalVal();
        Duration.Builder builder = Duration.builder().prior(true);
        TimeUnit noticeUnit = scheduleNoticesDto.getNoticeUnit();
        switch (noticeUnit) {
            case MINUTES:
                builder.minutes(noticeTotalVal);
                break;
            case HOURS:
                builder.hours(noticeTotalVal);
                break;
            case DAYS:
                builder.days(noticeTotalVal);
                break;
            default:
                builder.minutes(noticeTotalVal);
                break;
        }

        Duration duration = builder.build();
        // 在日程开始前提醒，提醒持续一定时间
        return new Trigger(duration, Related.START);
    }
}
