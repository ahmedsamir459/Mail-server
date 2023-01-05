package com.example.mailServer.Adapter;

import com.example.mailServer.Modules.Mail;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.json.simple.JSONArray;
import java.util.ArrayList;
public class ArrayAdapter {
    private ArrayList<Mail> myarray;
    public ArrayList<Mail> getMyarray(JSONArray emails){
        myarray= new ArrayList<>();
        ObjectMapper    mapper = new ObjectMapper();
        Mail[] Array = mapper.convertValue(emails, Mail[].class);
        JSONArray array = new JSONArray();
        for(int i=0;i<Array.length;i++){
            myarray.add(Array[i]);
            JSONObject obj = new JSONObject(Array[i]);
            array.add(obj);
        }
        return myarray;
    }
    public JSONArray getJsonArray(ArrayList emails){
        JSONArray array = new JSONArray();
        for(int i=0;i<emails.size();i++){
            JSONObject obj = new JSONObject(emails.get(i));
            array.add(obj);
        }
        return array;
    }
}
