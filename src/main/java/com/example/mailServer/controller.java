package com.example.mailServer;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.*;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
@CrossOrigin
@RestController
@RequestMapping
public class controller {
    File myObj = new File("E://users//database.json");
    service sr = new service();
    int len = sr.create_file(myObj);

    public static Map<String, String> users = new HashMap<>();

    public controller() throws IOException {
    }

    @GetMapping(value = "/file/{email}/{pass}")
    @CrossOrigin
    @ResponseBody
    public String sign_up(@PathVariable String email, @PathVariable String pass) throws ParseException, IOException {


String result="";
     try {

         String filename = "";
         int index = email.indexOf("@");
         filename = email.substring(0, index);

         if (len != 0) {
             System.out.println("try");
             result=check_user(email,pass);
             System.out.println(result);
             if(result.equals("not found")) {
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
    public String check_user(@PathVariable String email2, @PathVariable String password) throws ParseException, IOException {
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
