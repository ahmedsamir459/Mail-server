package com.example.mailServer.DateComp;

import com.example.mailServer.Adapter.DateAdapter;
import com.example.mailServer.Modules.Mail;

import java.time.LocalDateTime;
import java.util.Comparator;

public class DateComp implements Comparator<Mail> {
    @Override
    public int compare(Mail o1, Mail o2) {
        DateAdapter dateAdapter = new DateAdapter();
        LocalDateTime date1 =dateAdapter.aetDate(o1.getDate());
        LocalDateTime date2 = dateAdapter.aetDate(o2.getDate());
        return date2.compareTo(date1);
    }
}
