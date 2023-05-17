package com.example.mailServer.Sort;

import com.example.mailServer.DateComp.DateComp;
import com.example.mailServer.Modules.Mail;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;

import java.util.*;

public class EmailSort {
    public ArrayList<Mail> sort(JSONArray array, String s,boolean value) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Mail [] Array = mapper.readValue(array.toString(), Mail[].class);
        PriorityQueue<Mail> queue=new PriorityQueue<>();
        if(value){
            if(s.equals("subject")) {queue=new PriorityQueue<>(Comparator.comparing(Mail::getSubject));}
            else if(s.equalsIgnoreCase("from")) {queue=new PriorityQueue<>(Comparator.comparing(Mail::getFrom));}
            else if(s.equals("body")) {queue=new PriorityQueue<>(Comparator.comparing(Mail::getBody));}
            else if(s.equals("priority")) {queue=new PriorityQueue<>(Comparator.comparing(Mail::getPriority));}
            else if(s.equals("date")) {queue=new PriorityQueue<>(new DateComp().reversed());}
            else if(s.equals("attachment")){queue=new PriorityQueue<>(new Comparator<Mail>() {
                @Override
                public int compare(Mail o1, Mail o2) {
                    return o1.getAttachment().length-o2.getAttachment().length;
                }
            });}
            else if(s.equals("to")){queue=new PriorityQueue<>(Comparator.comparing(Mail::getTo));}
        }
        else {
            if (s.equals("subject")) {
                queue = new PriorityQueue<>(Comparator.comparing(Mail::getSubject).reversed());
            } else if (s.equalsIgnoreCase("from")) {
                queue = new PriorityQueue<>(Comparator.comparing(Mail::getFrom).reversed());
            } else if (s.equals("body")) {
                queue = new PriorityQueue<>(Comparator.comparing(Mail::getBody).reversed());
            } else if (s.equals("priority")) {
                queue = new PriorityQueue<>(Comparator.comparing(Mail::getPriority).reversed());
            } else if (s.equals("date")) {
                queue = new PriorityQueue<>(new DateComp());
            }
            else if(s.equals("attachment")){queue=new PriorityQueue<>(new Comparator<Mail>() {
                @Override
                public int compare(Mail o1, Mail o2) {
                    return o2.getAttachment().length-o1.getAttachment().length;
                }
            });}
            else if(s.equals("to")){queue=new PriorityQueue<>(Comparator.comparing(Mail::getTo).reversed());}
        }
        queue.addAll(Arrays.asList(Array));
        ArrayList<Mail>sorted_array=new ArrayList<>();
        int size=queue.size();
        for(int i=0;i<size;i++){sorted_array.add(queue.poll());}
        return sorted_array;
    }
}

