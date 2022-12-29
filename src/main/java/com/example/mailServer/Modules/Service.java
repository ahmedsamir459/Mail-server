package com.example.mailServer.Modules;

import java.io.IOException;

public class Service {
    private int size=0;
    public  void create_file( String path) {
        java.io.File myObj = null;
        try {
            myObj = new java.io.File(path);
            System.out.println(myObj.length());
            System.out.println(myObj);
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }} catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        size=(int)myObj.length();
    }

    public void CreateFolder(String path){
            java.io.File f = new java.io.File(path);
        if (f.mkdir()) {
            System.out.println("Folder created: " + f.getName());
        } else {
            System.out.println("Folder already exists.");
        }
    }
    public int getSize(){
        return size;
    }
}
