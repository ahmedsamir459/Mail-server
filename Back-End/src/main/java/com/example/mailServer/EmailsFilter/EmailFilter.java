package com.example.mailServer.EmailsFilter;

import com.example.mailServer.Adapter.ArrayAdapter;
import com.example.mailServer.Modules.Mail;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.simple.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;

public class EmailFilter implements Filter {
    private String feature;
    private String target;

    public EmailFilter(String feature, String target){
        this.feature = feature;
        this.target = target;
    }
    public EmailFilter(String feature,int target){
        this.feature = feature;
        this.target = String.valueOf(target);
    }

    public String getFeature() {
        return feature;
    }

    public String getTarget() {
        return target;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    @Override
    public ArrayList<Mail> meetCriteria(JSONArray emails) throws JsonProcessingException {
        ArrayAdapter arrayAdapter = new ArrayAdapter();
        ArrayList<Mail> filteredEmails = new ArrayList<>();
        ArrayList<Mail> emailsList =arrayAdapter.getMyarray(emails);
        switch (this.feature.toLowerCase()){
            case "subject":
                emailsList.stream().filter(email -> email.getSubject().toLowerCase().contains(this.target.toLowerCase())).forEach(filteredEmails::add);
                break;
            case "from":
                emailsList.stream().filter(email -> email.getFrom().toLowerCase().contains(this.target.toLowerCase())).forEach(filteredEmails::add);
                break;
            case "to":
                emailsList.stream().filter(email -> email.getTo().toLowerCase().contains(this.target.toLowerCase())).forEach(filteredEmails::add);
                break;
            case "body":
                emailsList.stream().filter(email -> email.getBody().toLowerCase().contains(this.target.toLowerCase())).forEach(filteredEmails::add);
                break;
            case "priority":
                emailsList.stream().filter(email ->String.valueOf(email.getPriority()).equalsIgnoreCase(this.target)).forEach(filteredEmails::add);
                break;
            case "attachment":
                emailsList.stream().filter(email -> Arrays.stream(email.getAttachment()).filter(attachment -> attachment.toLowerCase().contains(this.target.toLowerCase())).count() > 0).forEach(filteredEmails::add);
                break;
            case "date":
                emailsList.stream().filter(email -> email.getDate().toLowerCase().contains(this.target.toLowerCase())).forEach(filteredEmails::add);
                break;

        }
        return filteredEmails;
    }

}
