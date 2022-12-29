package com.example.mailServer.control;

import com.example.mailServer.Database.FileSinglton;
import com.example.mailServer.EmailsFilter.EmailsFilteringCustomizedCriteria;
import com.example.mailServer.FileBuilder;
import com.example.mailServer.Mail;
import com.example.mailServer.Modules.Service;
import com.example.mailServer.Validator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Controll {

    Validator vl=new Validator();
    Service sr = new Service();
    FileSinglton myfile=null;
    public Controll(){myfile=FileSinglton.getInstance();}
    public static Map<String, String> users = new HashMap<>();
    public String get_name(String email) throws Exception {
        String filename = "";
        int index = email.indexOf("@");
        filename = email.substring(0, index);
        return filename;
    }

    public String signin(String email2, String password) throws Exception {
        return vl.check_user(email2,password,users,myfile.getMyObj());
    }
    public String signup(String email,String password) throws Exception {
        String result="";
        try {
            Path path = Paths.get(myfile.getPath());
            int size = (int) Files.size(path);
            System.out.println(size);
            if (size != 0) {
                System.out.println("try");
                result = vl.check_user_signup(email,password,users,myfile.getMyObj());
                System.out.println(result);
                if (result.equals("not found")) {
                    FileBuilder f = new FileBuilder();
                    f.BuildFile(get_name(email), myfile.getDir_path());
                    ObjectMapper mapper = new ObjectMapper();
                    users = new HashMap<>();
                    users = mapper.readValue(myfile.getMyObj(), new TypeReference<Map<String, String>>() {
                    });
                    users.put(email, password);
                    for (Map.Entry<String, String> entry : users.entrySet())
                        System.out.println("Key = " + entry.getKey() +
                                ", Value = " + entry.getValue());
                    JSONObject jsonObj = new JSONObject(users);
                    System.out.println(jsonObj + "hena");
                    FileWriter myWriter = new FileWriter(myfile.getPath());
                    myWriter.append(jsonObj.toString());
                    myWriter.close();}}
            else {

                FileBuilder f = new FileBuilder();
                f.BuildFile(get_name(email), myfile.getDir_path());
                System.out.println("else");
                result = "not found";
                users.put(email, password);
                for (Map.Entry<String, String> entry : users.entrySet())
                    System.out.println("Key = " + entry.getKey() +
                            ", Value = " + entry.getValue());
                JSONObject jsonObj = new JSONObject(users);
                FileWriter myWriter = new FileWriter(myfile.getPath());
                myWriter.write(jsonObj.toString());
                myWriter.close();
            }

            System.out.println("Successfully wrote to the file.");
        } catch(Exception e){
            System.out.println("error");
        }

        return result;
    }
    public org.json.simple.JSONArray load_mails(String filename) throws IOException, ParseException {
        System.out.println(filename);
        System.out.println("ok");
        JSONParser parser = new JSONParser();
        org.json.simple.JSONArray array;
        array = (org.json.simple.JSONArray) parser.parse(new FileReader("C:\\Users\\Ahmed Samir\\Desktop\\Mail\\src\\main\\java\\com\\example\\mailServer\\Database\\users\\ahmed\\inbox.json"));
        System.out.println(array);
        return array;

    }
    public void save_mails(String filename, org.json.simple.JSONArray array) throws IOException {
        FileWriter myWriter = new FileWriter(filename);
        myWriter.write(array.toString());
        myWriter.close();
    }

    public JSONArray save_mail(Mail mail) throws Exception {
        JSONObject json1=new JSONObject(mail);
        System.out.println(mail);
        String filename1,filename2;
        filename1=get_name(mail.getFrom());
        filename2 = get_name(mail.getTo());
        String path1=myfile.getDir_path()+"\\"+filename2+"\\inbox.json";
        String path2=myfile.getDir_path()+"\\"+filename1+"\\sent.json";
        JSONObject jsonObject=new JSONObject(mail);
        org.json.simple.JSONArray array=new org.json.simple.JSONArray();
        Path path= Paths.get(path1);


        int sz=(int) Files.size(path);
        if(sz!=0) {
            array = load_mails(path1);
        }
        array.add(jsonObject);
        save_mails(path1,array);
        save_mails(path2,array);
        load_mails(path1);
        load_mails(path2);
        return array;
    }

    public JSONArray filter(String feature, String target, String filename) throws IOException, ParseException {
        JSONArray array=load_mails(filename);
        System.out.println("array"+array);
        EmailsFilteringCustomizedCriteria filter=new EmailsFilteringCustomizedCriteria(feature,target);
        ArrayList result=filter.meetCriteria(array);
        JSONArray array1=new JSONArray();
        array1.addAll(result);
        return array1;
    }
    public void save_attachments(ArrayList<File> array)
    {
        for (int i = 0; i < array.size(); i++)

            System.out.print(array.get(i) + " ");
    }



}
