package com.example.mailServer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Validator {
    public String check_user_signup(String email2, String password, Map<String,String> users, File myObj) throws ParseException, IOException {
        System.out.println(email2);
        System.out.println(password);
        boolean found=false;
        ObjectMapper mapper = new ObjectMapper();

        users = mapper.readValue(myObj, new TypeReference<Map<String,String>>() {
        });

        for (Map.Entry<String, String> entry : users.entrySet()) {
            if(entry.getKey().equals(email2))
            {
                found=true;
                break;
            }
        }
        if(found==true){
            return "found";
        }
        else{
            return "not found";
        }
    }
    public String check_user(String email2, String password, Map<String,String> users, File myObj) throws ParseException, IOException {
        System.out.println(email2);
        System.out.println(password);
        boolean found=false;
        ObjectMapper mapper = new ObjectMapper();

        users = mapper.readValue(myObj, new TypeReference<Map<String,String>>() {
        });

        for (Map.Entry<String, String> entry : users.entrySet()) {
            if(entry.getKey().equals(email2) && entry.getValue().equals(password))
            {
                found=true;
                break;
            }
        }
        if(found==true){
            return "found";
        }
        else{
            return "not found";
        }
    }
}
