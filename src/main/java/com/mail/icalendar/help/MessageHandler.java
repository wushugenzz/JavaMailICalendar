package com.mail.icalendar.help;

import biweekly.ICalendar;
import com.mail.icalendar.constant.ICalConstants;
import com.mail.icalendar.model.dto.ICalendarAccountDto;
import com.mail.icalendar.model.dto.ICalendarDto;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import java.io.IOException;

/**
 * @author wsg
 * @date 2022/1/12 15:18
 */
public class MessageHandler {

    /**
     * 把日程内嵌到邮件消息体内
     *
     * @param calendarDto
     * @throws MessagingException
     * @throws IOException
     */
    public static void addICalendarBodyPart2Message(ICalendarDto calendarDto, MimeMessage message) throws MessagingException, IOException {
        Address[] fromAddress = message.getFrom();
        InternetAddress address = (InternetAddress) fromAddress[0];
        Address[] toAddress = message.getRecipients(Message.RecipientType.TO);

        ICalendar calendar = ICalendarHandler.buildICalendar(calendarDto,
                new ICalendarAccountDto(address.getPersonal(), address.getAddress(), toAddress));

        // 把日程内嵌到邮件消息体内
        MimeBodyPart calendarBodyPart = new MimeBodyPart();
        DataSource calendarDataSource = new ByteArrayDataSource(calendar.write(), ICalConstants.MAIL_CONTENT_TYPE_CALENDAR);
        calendarBodyPart.setDataHandler(new DataHandler(calendarDataSource));
        MimeMultipart mimeMultipart = new MimeMultipart();
        mimeMultipart.addBodyPart(calendarBodyPart);
        message.setContent(mimeMultipart);
    }
}
