package com.example.mailServer;

import java.io.IOException;

public class MailBuilder {
    private Mail mail;
    public MailBuilder()
    {
        mail=new Mail();
    }
    public Mail mailbuild(String sender,String reciever,String subject,String body) throws NullPointerException {
        mail.setSender(sender);
        mail.setRecievers(reciever);
        mail.setBody(body);
        mail.setSubject(subject);
        return mail;
    }
}
