package com.example.mailServer;

public class FileBuilder {
public void BuildFile(String filename)
{ Service sr=new Service();
    String path="E://users//"+filename;
    sr.CreateFolder(path);
    sr.create_file(path+"//inbox.json");
    sr.create_file(path+"//trash.json");
    sr.create_file(path+"//draft.json");
    sr.create_file(path+"//sent.json");

}
}
