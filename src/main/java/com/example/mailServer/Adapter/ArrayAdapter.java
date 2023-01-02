package com.example.mailServer.Adapter;

import com.example.mailServer.Modules.Mail;
import com.google.gson.Gson;
import org.json.simple.JSONArray;

import java.util.ArrayList;

public class ArrayAdapter {
    private ArrayList<Mail> myarray;
    public ArrayList<Mail> getMyarray(JSONArray emails) {
        myarray= new ArrayList<>();
        for (int i=0;i<emails.size();i++){
            Gson gson = new Gson();
            Mail mail = gson.fromJson(emails.get(i).toString(), Mail.class);
            myarray.add(mail);
        }
        return myarray;
    }
}
