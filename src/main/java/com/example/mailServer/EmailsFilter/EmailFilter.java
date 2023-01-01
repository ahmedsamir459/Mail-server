package com.example.mailServer.EmailsFilter;

import com.example.mailServer.Mail;
import com.google.gson.Gson;
import org.json.simple.JSONArray;

import java.util.ArrayList;

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
    public ArrayList<Mail> meetCriteria(JSONArray emails) {
        ArrayList<Mail> filteredEmails = new ArrayList<>();
        ArrayList<Mail> emailsList = new ArrayList<>();
        for (int i=0;i<emails.size();i++){
            Gson gson = new Gson();
            Mail mail = gson.fromJson(emails.get(i).toString(), Mail.class);
            emailsList.add(mail);
        }
        switch (this.feature.toLowerCase()){
            case "subject":
                emailsList.stream().filter(email -> email.getSubject().equalsIgnoreCase(this.target)).forEach(filteredEmails::add);
                break;
            case "from":
                emailsList.stream().filter(email -> email.getFrom().equalsIgnoreCase(this.target)).forEach(filteredEmails::add);
                break;
            case "to":
                emailsList.stream().filter(email -> email.getTo().equalsIgnoreCase(this.target)).forEach(filteredEmails::add);
                break;
            case "body":
                emailsList.stream().filter(email -> email.getBody().equalsIgnoreCase(this.target)).forEach(filteredEmails::add);
                break;
            case "priority":
                emailsList.stream().filter(email ->String.valueOf(email.getPriority()).equalsIgnoreCase(this.target)).forEach(filteredEmails::add);
                break;

        }
        return filteredEmails;
    }

}
