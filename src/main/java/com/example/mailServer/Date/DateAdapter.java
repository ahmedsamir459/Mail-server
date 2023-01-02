package com.example.mailServer.Date;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateAdapter {
    public LocalDateTime aetDate(String aetDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        return LocalDateTime.parse(aetDate, formatter);
    }
}
