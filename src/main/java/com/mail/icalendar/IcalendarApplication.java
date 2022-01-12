package com.mail.icalendar;

import com.mail.icalendar.help.MessageHandler;
import com.mail.icalendar.model.dto.ICalendarDto;
import com.mail.icalendar.model.dto.ScheduleNoticeDto;
import com.sun.mail.smtp.SMTPTransport;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class IcalendarApplication {

    public static void main(String[] args) throws Exception {
        // 发件人账号
        String account = "";
        String password = "";
        String host = "smtp.qq.com";
        Integer port = 465;

        // 收件人账号
        String to = "";

        Session session = getSession();
        SMTPTransport transport = (SMTPTransport) session.getTransport("smtps");
        transport.connect(host, port, account, password);

        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(account));
        // 邮件标题
        message.setSubject("测试日程" + System.currentTimeMillis());

        InternetAddress address = new InternetAddress(to);
        message.setRecipient(Message.RecipientType.TO, address);

        MessageHandler.addICalendarBodyPart2Message(initICalendarDto(), message);

        // 发送邮件
        transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
        transport.close();
    }

    private static ICalendarDto initICalendarDto() {
        ICalendarDto calendarDto = new ICalendarDto();
        calendarDto.setTitle("标题");
        calendarDto.setStartTime(new Date());
        calendarDto.setEndTime(new Date(System.currentTimeMillis() + 1000 * 60 * 60));
        calendarDto.setDescription("描述");
        calendarDto.setAddress("地点");
        ScheduleNoticeDto scheduleNotice = new ScheduleNoticeDto();
        scheduleNotice.setNoticeUnit(TimeUnit.MINUTES);
        scheduleNotice.setNoticeTotalVal(20);
        calendarDto.setScheduleNotice(scheduleNotice);
        return calendarDto;
    }

    private static Session getSession() {
        // configure javamail
        Properties props = System.getProperties();
        props.setProperty("mail.transport.protocol", "smtps");
        props.put("mail.smtps.ssl.enable", true);
        props.setProperty("mail.smtps.auth", "true");
        props.put("mail.smtps.connectiontimeout", 180);
        props.put("mail.smtps.timeout", 600);
        props.setProperty("mail.mime.encodefilename", "true");

        Session mailSession = Session.getInstance(props);
        return mailSession;
    }

}
