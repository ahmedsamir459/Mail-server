package com.example.mailServer.Modules;

public class result {

    String message;
    Boolean error;
    public result(String massage, Boolean error) {
        this.message = massage;
        this.error = error;}
    public String getMessage() {
        return message;
    }
    public void setMassage(String massage) {
        this.message = massage;
    }
    public Boolean getError() {
        return error;
    }
    public void setError(Boolean error) {
        this.error = error;
    }
}
