package com.example.mailServer;

public class result {
    String massage;
    Boolean error;


    public result(String massage, Boolean error) {
        this.massage = massage;
        this.error = error;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }
}
