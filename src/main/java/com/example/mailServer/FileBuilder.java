package com.example.mailServer;

import com.example.mailServer.Modules.Service;

public class FileBuilder {
public void BuildFile(String filename,String s)
{
    Service sr=new Service();
    String path=s+"\\"+filename;
    sr.CreateFolder(path);
    sr.create_file(path+"//inbox.json");
    sr.create_file(path+"//trash.json");
    sr.create_file(path+"//draft.json");
    sr.create_file(path+"//sent.json");
}
}
