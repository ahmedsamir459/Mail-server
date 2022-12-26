package com.example.mailServer;

import java.io.IOException;

public class Service {
    public  int create_file( String path) {
        java.io.File myObj = null;
        try {
            myObj = new java.io.File(path);
            System.out.println(myObj.length());
            System.out.println(myObj);

            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return (int)myObj.length();
    }

    public void CreateFolder(String path){
            java.io.File f = new java.io.File(path);
        if (f.mkdir()) {
            System.out.println("Folder created: " + f.getName());
        } else {
            System.out.println("Folder already exists.");
        }
    }
}
