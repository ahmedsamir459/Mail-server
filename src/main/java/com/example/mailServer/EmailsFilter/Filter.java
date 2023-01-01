package com.example.mailServer.EmailsFilter;
import com.example.mailServer.Mail;
import org.json.simple.JSONArray;

import java.util.ArrayList;

public interface Filter {
    ArrayList<Mail> meetCriteria(JSONArray emails);

}
