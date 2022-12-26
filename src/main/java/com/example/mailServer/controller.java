package com.example.mailServer;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
@CrossOrigin
@RestController
@RequestMapping
public class controller {
    private  String database="E://users//database.json";
    java.io.File myObj = new java.io.File(database);
    Service sr = new Service();

    public static Map<String, String> users = new HashMap<>();

    public controller() throws IOException {
    }

    @GetMapping(value = "/file/{email}/{pass}")
    @CrossOrigin
    @ResponseBody
    public String sign_up(@PathVariable String email, @PathVariable String pass) throws ParseException, IOException {
        sr.CreateFolder("E://users");
        int len = sr.create_file(database);
        String filename = "";
        int index = email.indexOf("@");
        filename = email.substring(0, index);


String result="";
     try {
         if (len != 0) {
             System.out.println("try");
             result=check_user(email,pass);
             System.out.println(result);
             if(result.equals("not found")) {
                 FileBuilder f=new FileBuilder();
                 f.BuildFile(filename);
                 ObjectMapper mapper = new ObjectMapper();
                 users = new HashMap<>();
                 users = mapper.readValue(myObj, new TypeReference<Map<String, String>>() {
                 });
                 users.put(email, pass);
                 for (Map.Entry<String, String> entry : users.entrySet())
                     System.out.println("Key = " + entry.getKey() +
                             ", Value = " + entry.getValue());
                 JSONObject jsonObj = new JSONObject(users);
                 System.out.println(jsonObj + "hena");
                 FileWriter myWriter = new FileWriter("E://users//database.json");
                 myWriter.append(jsonObj.toString());
                 myWriter.close();
             }
         } else {
             System.out.println("else");
              result="not found";
             users.put(email, pass);
             for (Map.Entry<String, String> entry : users.entrySet())
                 System.out.println("Key = " + entry.getKey() +
                         ", Value = " + entry.getValue());
             JSONObject jsonObj = new JSONObject(users);
             FileWriter myWriter = new FileWriter("E://users//database.json");
             myWriter.write(jsonObj.toString());
             myWriter.close();
         }

         System.out.println("Successfully wrote to the file.");
     } catch (Exception e) {
         System.out.println("error");
     }

         return result;

    }

    @GetMapping(value = "/file1/{email2}/{password}")
    @CrossOrigin
    @ResponseBody
    public String sign_in(@PathVariable String email2, @PathVariable String password) throws ParseException, IOException {
        return check_user(email2, password);


    }
    public void save_mails(String sender,String reciever,String subject,String body) throws IOException {    String filename1,filename2;
        int index = sender.indexOf("@");
        filename1 = sender.substring(0, index);
         index = reciever.indexOf("@");
        filename2 = reciever.substring(0, index);
        MailBuilder builder=new MailBuilder();
        Mail mail=builder.mailbuild(sender, reciever, subject, body);
        JSONObject jsonObject=new JSONObject(mail);
        JSONArray array=new JSONArray();
        array.put(jsonObject);
        FileWriter writer=new FileWriter("E://users//"+filename1+"//inbox.json");
        writer.write(array.toString());
        FileWriter writer2=new FileWriter("E://users//"+filename2+"//inbox.json");
        writer2.write(array.toString());
        writer.close();
        writer2.close();
    }

    public String check_user(String email2,  String password) throws ParseException, IOException {
        System.out.println(email2);
        System.out.println(password);
        boolean found=false;
        ObjectMapper mapper = new ObjectMapper();

        users = mapper.readValue(myObj, new TypeReference<Map<String,String>>() {
        });

        for (Map.Entry<String, String> entry : users.entrySet()) {
           if(entry.getKey().equals(email2) && entry.getValue().equals(password))
           {
               found=true;
               break;
           }
        }
        if(found==true){
            return "found";
        }
        else{
            return "not found";
        }
    }
}
