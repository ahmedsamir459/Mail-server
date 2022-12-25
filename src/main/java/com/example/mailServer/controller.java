package com.example.mailServer;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.json.simple.parser.JSONParser;
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

    @RequestMapping(value = "/file/{email}/{pass}", method = RequestMethod.POST)
    public void write_to_file(@PathVariable String email, @PathVariable String pass) {


        try {

            String filename = "";
            int index = email.indexOf("@");
            filename = email.substring(0, index);

                if (len!= 0) {

                    ObjectMapper mapper = new ObjectMapper();
                    users = new HashMap<>();
                    users = mapper.readValue(myObj, new TypeReference<Map<String,String>>() {
                            });
                    users.put(email, pass);
                                        for (Map.Entry<String,String> entry : users.entrySet())
                        System.out.println("Key = " + entry.getKey() +
                                ", Value = " + entry.getValue());
                    JSONObject jsonObj = new JSONObject(users);
                    System.out.println(jsonObj+"hena");
                    FileWriter myWriter = new FileWriter("E://users//database.json");
                    myWriter.append(jsonObj.toString());
myWriter.close();
                } else {

                    users.put(email, pass);
                    for (Map.Entry<String, String> entry : users.entrySet())
                        System.out.println("Key = " + entry.getKey() +
                                ", Value = " + entry.getValue());
                    JSONObject jsonObj = new JSONObject(users);
                    FileWriter myWriter = new FileWriter("E://users//database.json");
                    myWriter.write(jsonObj.toString());
                    myWriter.close();
                }
            for (Map.Entry<String, String> entry : users.entrySet())
                System.out.println("Key = " + entry.getKey() +
                        ", Value = " + entry.getValue());





//                    ObjectMapper mapper = new ObjectMapper();
//                    File fileObj = new File("E://users//database.json");
//                    users.put(email, pass);
//                    JSONObject jsonObj = new JSONObject(users);
//                    myWriter.write(jsonObj.toString());
//                    for (Map.Entry<String,String> entry : users.entrySet())
//                        System.out.println("Key = " + entry.getKey() +
//                                ", Value = " + entry.getValue());


//            BufferedReader br = new BufferedReader(new FileReader("E://users//database.json"));
//            if (br.readLine() == null) {
//                users.put(email, pass);
//                JSONObject jsonObj = new JSONObject(users);
//                myWriter.write(jsonObj.toString());
//            }
//            for (Map.Entry<String,String> entry : users.entrySet())
//////                        System.out.println("Key = " + entry.getKey() +
//////                                ", Value = " + entry.getValue());

//            else
//            {
////                JSONParser parser = new JSONParser();
////                ObjectMapper mapper = new ObjectMapper();
////
////                try {
////                    File fileObj = new File("E://users//database.json");
////                    users = mapper.readValue(
////                            fileObj, new TypeReference<Map<String,String>>() {
////                            });
////                    for (Map.Entry<String,String> entry : users.entrySet())
////                        System.out.println("Key = " + entry.getKey() +
////                                ", Value = " + entry.getValue());
////
////                } catch (Exception e) {
////                    e.printStackTrace();
////                }
//            }

                System.out.println("Successfully wrote to the file.");
            }

     catch (Exception e)
     {

     }

    }
}
