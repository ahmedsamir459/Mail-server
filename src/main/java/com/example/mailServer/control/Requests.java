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
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
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
        System.out.println(mail);
       JSONArray json1=controll.save_mail(mail);
        return json1;
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




}
