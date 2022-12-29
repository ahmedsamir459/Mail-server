package com.example.mailServer.EmailsFilter;

import com.example.mailServer.Mail;
import com.google.gson.Gson;
import org.json.simple.JSONArray;

import java.util.ArrayList;

public class EmailsFilteringCustomizedCriteria implements EmailsCriteriaI {
    private String feature;
    private String target;

    public EmailsFilteringCustomizedCriteria(String feature, String target){
        this.feature = feature;
        this.target = target;
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
//                for(EmailI email:emails){
//                    if(email.getSubject().equalsIgnoreCase(this.target)){
//                        filteredEmails.add(email);
//                    }
//                }
                break;
            case "from":
                emailsList.stream().filter(email -> email.getFrom().equalsIgnoreCase(this.target)).forEach(filteredEmails::add);
//                for(EmailI email:emails){
//                    if(email.getSenderUsername().equalsIgnoreCase(this.target)){
//                        filteredEmails.add(email);
//                    }
//                }
                break;

        }
        return filteredEmails;
    }

}
