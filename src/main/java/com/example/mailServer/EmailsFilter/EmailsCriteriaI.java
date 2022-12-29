package com.example.mailServer.EmailsFilter;
import com.example.mailServer.Mail;
import org.json.simple.JSONArray;

import java.util.ArrayList;

public interface EmailsCriteriaI {
    ArrayList<Mail> meetCriteria(JSONArray emails);

}
