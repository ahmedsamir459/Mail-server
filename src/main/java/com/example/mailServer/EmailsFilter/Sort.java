package com.example.mailServer.EmailsFilter;

import com.example.mailServer.Datecomp.DateComp;
import com.example.mailServer.Modules.Mail;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;

import java.util.*;

public class Sort {
    public ArrayList<Mail> sort(JSONArray array, String s) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Mail [] Array = mapper.readValue(array.toString(), Mail[].class);
        for(int i=0;i<Array.length;i++)
        {
            System.out.println(Array[i].getSubject());
        }
        PriorityQueue<Mail> queue=new PriorityQueue<>();
        if(s.equals("subject")) {
            queue=new PriorityQueue<>(Comparator.comparing(Mail::getSubject));
        }
        else if(s.equals("body")) {
            queue=new PriorityQueue<>(Comparator.comparing(Mail::getBody));
        }
        else if(s.equals("priority")) {
            queue=new PriorityQueue<>(Comparator.comparing(Mail::getPriority));
        }
        else if(s.equals("date")) {queue=new PriorityQueue<>(new DateComp());}
        queue.addAll(Arrays.asList(Array));
        ArrayList<Mail>sorted_array=new ArrayList<>();
        int size=queue.size();
        for(int i=0;i<size;i++){sorted_array.add(queue.poll());}
        return sorted_array;
    }

}

