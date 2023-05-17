package com.example.mailServer.Singlton;

import com.example.mailServer.Services.Service;

public class FileSinglton {
    private static FileSinglton instance = null;
    private String path = "src\\main\\java\\com\\example\\mailServer\\Database\\users\\Mail.json";
    private String dir_path = "src\\main\\java\\com\\example\\mailServer\\Database\\users";
    private java.io.File myObj= new java.io.File(path);;

    private int size=0;
    private Service sr=new Service();

    private FileSinglton() {
        sr.CreateFolder(dir_path);
        sr.create_file(path);
    }
    public static FileSinglton getInstance() {
        if(instance == null) {
            instance = new FileSinglton();
        }
        return instance;
    }
    public String getPath(){
        return path;
    }
    public java.io.File getMyObj(){
        return myObj;
    }
    public int getSize(){
        return sr.getSize();
    }

    public Service getSr() {
        return sr;
    }
    public String getDir_path() {
        return dir_path;
    }

    public void setDir_path(String dir_path) {
        this.dir_path = dir_path;
    }


}
