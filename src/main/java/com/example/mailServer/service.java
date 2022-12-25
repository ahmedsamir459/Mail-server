package com.example.mailServer;

import java.io.File;
import java.io.IOException;

public class service {
    public static int create_file( File file) {
        CreateDirectory();
        File myObj = null;
        try {
            myObj = new File("E://users//database.json");
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

    public static void CreateDirectory(){
            File f = new File("E://users");
           f.mkdir();
    }
}
