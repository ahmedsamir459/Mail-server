package com.example.mailServer.Datecomp;
import com.example.mailServer.Mail;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Day30 {
   public JSONArray filter30days(JSONArray array) {
      ObjectMapper mapper = new ObjectMapper();
      DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
      LocalDateTime now = LocalDateTime.now();
      Mail[] Array = mapper.convertValue(array, Mail[].class);
      LocalDateTime thirtyDaysAgo = now.minusDays(30);
      JSONArray filteredArray = new JSONArray();
      for (int i = 0; i < array.size(); i++) {
         LocalDateTime date = LocalDateTime.parse(Array[i].getDate(), formatter);
         if (date.isAfter(thirtyDaysAgo)) {
            filteredArray.add(array.get(i));}}
      System.out.println(filteredArray);
      return filteredArray;
   }
}
