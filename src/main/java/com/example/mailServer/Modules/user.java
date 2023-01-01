package com.example.mailServer.Modules;

import com.example.mailServer.Mail;

import java.util.Map;

public class user {
   private   String email;
   private String pass;
   private Mail[] inbox;
   private String [] contacts;
    private Mail[] trash;
    private Mail[] draft;
    private Mail[] sent;
    private Map<String,Mail[]> folders;


    public user(String email, String pass, Mail[] inbox, String[] contacts, Mail[] trash, Mail[] draft, Mail[] sent, Map<String, Mail[]> folders) {
        this.email = email;
        this.pass = pass;
        this.inbox = inbox;
        this.contacts = contacts;
        this.trash = trash;
        this.draft = draft;
        this.sent = sent;
        this.folders = folders;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public Mail[] getInbox() {
        return inbox;
    }

    public void setInbox(Mail[] inbox) {
        this.inbox = inbox;
    }

    public String[] getContacts() {
        return contacts;
    }

    public void setContacts(String[] contacts) {
        this.contacts = contacts;
    }

    public Mail[] getTrash() {
        return trash;
    }

    public void setTrash(Mail[] trash) {
        this.trash = trash;
    }

    public Mail[] getDraft() {
        return draft;
    }

    public void setDraft(Mail[] draft) {
        this.draft = draft;
    }

    public Mail[] getSent() {
        return sent;
    }

    public void setSent(Mail[] sent) {
        this.sent = sent;
    }
    public Map<String, Mail[]> getFolders() {
        return folders;
    }
    public void setFolders(Map<String, Mail[]> folders) {
        this.folders = folders;
    }
    public void addFolder(String name,Mail[] mails)
    {
        folders.put(name,mails);
    }
    public void removeFolder(String name)
    {
        folders.remove(name);
    }
}
