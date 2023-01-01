package com.example.mailServer.control;
import com.example.mailServer.Mail;
import com.example.mailServer.MailBuilder;
import com.example.mailServer.Modules.Service;
import com.example.mailServer.Modules.result;
import com.example.mailServer.Validator;

import com.google.gson.Gson;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin
@RestController
@RequestMapping
public class Requests {
    Controll controll=new Controll();

    @CrossOrigin
    @ResponseBody
    @GetMapping(value = "/signup/{email}/{pass}")
    public String sign_up(@PathVariable String email, @PathVariable String pass) throws Exception {
        return controll.signup(email,pass);
    }

    @GetMapping(value = "/signin/{email2}/{password}")
    @CrossOrigin
    @ResponseBody
    public String sign_in(@PathVariable String email2, @PathVariable String password) throws Exception {
        return controll.signin(email2,password);
    }
    @RequestMapping( value = "/mailling",method = RequestMethod.POST)
    @ResponseBody
    public JSONArray mailling(@RequestBody Mail mail) throws Exception {
//        for(int i=0;i< list.length;i++) {
//            System.out.println(list[i]);
//        }
//        mail.setAttachments(list);
        Mail m=new Mail();
        String [] at={"jo","kk"};
        m.setAttachments(at);
        m.setBody("gg");
        m.setSubject("uu");
        m.setTo("pp");
        m.setFrom("io");
        m.setPriority(1);
        JSONObject jsonObject1=new JSONObject(m);
         JSONObject jsonObject=new JSONObject(mail);
        System.out.println(jsonObject);
        System.out.println(jsonObject1);
//                for(int i=0;i<names.size();i++)
//        {
//            System.out.println(names.get(i));
//        }

     JSONArray json1=controll.save_mail(mail);

return  json1;
    }
    @RequestMapping( value = "/filter/{feature}/{target}",method = RequestMethod.POST)
    @ResponseBody
    public JSONArray filter(@RequestBody String filename,@PathVariable String feature,@PathVariable String target ) throws Exception {
        System.out.println(feature);
        System.out.println(target);
        System.out.println(filename);
        JSONArray json1=controll.filter(feature,target,filename);
        return json1;
    }
   @PostMapping("/attachments/{to}/{from}")
    public ArrayList<String> handleattachmnets(@RequestParam("attachment") MultipartFile [] attachments,@PathVariable  String to,@PathVariable String from) throws Exception {

//      for(MultipartFile attachment:attachments){
//          attachment.transferTo(new File("E://trial//"+attachment.getOriginalFilename()));
//      }
       System.out.println(to);
       System.out.println(from);
       Controll c=new Controll();
      ArrayList<String> names= c.handleattachmnets1(attachments,to,from);

        return names;
    }
    @PostMapping("/homepage/{type}/{email}")
    public JSONArray load(@PathVariable String type,@PathVariable String email) throws Exception {
        JSONArray array=new JSONArray();
       array= controll.load_mails_general(type,email);
       return array;
    }
    @GetMapping("/getfiles/{fileName}/{from}")
    public ResponseEntity<UrlResource>  getFiles (@PathVariable String fileName,@PathVariable String from) throws Exception {
            Path paths = controll.getfiles(fileName,from);
            System.out.println(paths);

        try {
                UrlResource resource = new UrlResource(paths.toUri());
                 HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.add("File-Name", fileName);
                httpHeaders.add(HttpHeaders.CONTENT_DISPOSITION, "attachment;File-Name=" + resource.getFilename());
                System.out.println("hena");

            return  ResponseEntity.ok().contentType(MediaType.parseMediaType(Files.probeContentType(paths))).headers(httpHeaders).body( resource) ;
        } catch (Exception e) {
            e.printStackTrace();
            return null ;
        }
    }





}
