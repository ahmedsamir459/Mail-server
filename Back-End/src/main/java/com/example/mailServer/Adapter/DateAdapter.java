package com.example.mailServer.Adapter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateAdapter {
    public LocalDateTime aetDate(String aetDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy, hh:mm:ss a");
        LocalDateTime date = LocalDateTime.parse(aetDate.toLowerCase(), formatter);
        return date;
    }
}
