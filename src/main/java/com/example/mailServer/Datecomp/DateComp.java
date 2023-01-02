package com.example.mailServer.Datecomp;

import com.example.mailServer.Modules.Mail;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

public class DateComp implements Comparator<Mail> {
    @Override
    public int compare(Mail o1, Mail o2) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        LocalDateTime date1 = LocalDateTime.parse(o1.getDate(), formatter);
        LocalDateTime date2 = LocalDateTime.parse(o2.getDate(), formatter);
        return date2.compareTo(date1);
    }
}
