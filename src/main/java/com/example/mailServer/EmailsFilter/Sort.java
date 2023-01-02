package com.example.mailServer.EmailsFilter;

import com.example.mailServer.Mail;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.json.JSONObject;
import org.json.simple.JSONArray;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

//    public JSONArray sort(JSONArray array, String s) throws JsonProcessingException {
//        ObjectMapper mapper = new ObjectMapper();
////        Mail Array = mapper.readValue(array.toString(), Arrays.class);
//        ArrayList<Mail> emailsList = new ArrayList<>();
//        Mail[] Array= new Mail[array.size()];
//        for (int i=0;i<array.size();i++){
//            Gson gson = new Gson();
//            Mail mail = gson.fromJson(array.get(i).toString(), Mail.class);
//            Array[i]=mail;
//        }
//        System.out.println(Array[0]);
//        System.out.println("ana hna");
//        PriorityQueue<Mail> queue=new PriorityQueue<>();
//        if(s.equalsIgnoreCase("subject")) {
//            queue=new PriorityQueue<Mail>(Comparator.comparing(Mail::getSubject));
//        }
//        else if(s.equalsIgnoreCase("body")) {
//            queue=new PriorityQueue<Mail>(Comparator.comparing(Mail::getBody));
//        }
//        else if(s.equalsIgnoreCase("priority")) {
//            queue=new PriorityQueue<Mail>(Comparator.comparing(Mail::getPriority));
//        }
//        else if(s.equalsIgnoreCase("date")){
//            queue=new PriorityQueue<Mail>(Comparator.comparing(Mail::getDate));
//        }
//
//        queue.addAll(Arrays.asList(Array));
//        Mail [] sorted_array=new Mail[Array.length];
//        JSONArray result=new JSONArray();
//        for(int i=0;i<Array.length;i++)
//        {
//            sorted_array[i]=queue.poll();
//        }
//        for(Mail mail:sorted_array)
//        {
//            JSONObject jsonObject=new JSONObject(mail);
//            result.add(jsonObject);
//        }
//        System.out.println(result);
//        return result;
//    }
}

