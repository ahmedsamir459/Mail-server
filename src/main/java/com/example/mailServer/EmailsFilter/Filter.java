package com.example.mailServer.EmailsFilter;
import com.example.mailServer.Modules.Mail;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.simple.JSONArray;

import java.util.ArrayList;

public interface Filter {
    ArrayList<Mail> meetCriteria(JSONArray emails) throws JsonProcessingException;

}
