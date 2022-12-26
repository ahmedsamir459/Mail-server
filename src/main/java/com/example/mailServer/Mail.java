package com.example.mailServer;

public class Mail {
   private String sender;
   private String  recievers;
   private String subject;
   private String body;
   private int priority;
   private String [] attachments;

   public String getSender() {
      return sender;
   }

   public void setSender(String sender) {
      this.sender = sender;
   }

   public String getRecievers() {
      return recievers;
   }

   public void setRecievers(String recievers) {
      this.recievers = recievers;
   }

   public String getSubject() {
      return subject;
   }

   public void setSubject(String subject) {
      this.subject = subject;
   }

   public String getBody() {
      return body;
   }

   public void setBody(String body) {
      this.body = body;
   }

   public int getPriority() {
      return priority;
   }

   public void setPriority(int priority) {
      this.priority = priority;
   }

   public String[] getAttachments() {
      return attachments;
   }

   public void setAttachments(String[] attachments) {
      this.attachments = attachments;
   }
}
