package com.example.mailServer.EmailsFilter;

import com.example.mailServer.Mail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Date;

public class DateComp implements Comparator<Mail> {
    @Override
    public int compare(Mail o1, Mail o2) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        LocalDateTime date1 = LocalDateTime.parse(o1.getDate(), formatter);
        LocalDateTime date2 = LocalDateTime.parse(o2.getDate(), formatter);
        return date2.compareTo(date1);
    }
}
