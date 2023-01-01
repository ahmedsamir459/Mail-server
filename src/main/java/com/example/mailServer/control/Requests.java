package com.example.mailServer.control;
import com.example.mailServer.Mail;
import com.example.mailServer.Modules.result;

import org.json.simple.JSONArray;
import org.springframework.core.io.UrlResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Map;

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
    @RequestMapping( value = "/mailing",method = RequestMethod.POST)
    @ResponseBody
    public result mailing(@RequestBody Mail mail) throws Exception {
       return controll.save_mail(mail);
    }
    @RequestMapping(value = "/load/{email}/{fileName}",method = RequestMethod.GET)
    public JSONArray load_mail(@PathVariable String email,@PathVariable String fileName) throws Exception {
        String path=controll.myfile.getDir_path()+File.separator+controll.get_name(email)+File.separator+fileName+".json";
        return controll.load_mails(path);
    }
    @RequestMapping( value = "/filter/{email}/{filename}/{feature}/{target}",method = RequestMethod.POST)
    @ResponseBody
    public JSONArray filter(@PathVariable String email,@PathVariable String filename,@PathVariable String feature,@PathVariable String target ) throws Exception {
        String path=controll.myfile.getDir_path()+File.separator+controll.get_name(email)+File.separator+filename+".json";
        JSONArray json1=controll.filter(feature,target,path);
        return json1;
    }
   @RequestMapping(value = "/addfolder/{mail}/{name}", method = RequestMethod.GET)
    public String addfolder(@PathVariable String mail,@PathVariable String name) throws Exception {
       System.out.println(mail);
         System.out.println(name);
        return controll.addfolder(mail,name);
    }
    @RequestMapping(value = "/moveall/{mail}/{name}/{name1}", method = RequestMethod.GET)
    public String moveall(@PathVariable String mail,@PathVariable String name,@PathVariable String name1) throws Exception {
        return controll.move_all(mail,name,name1);
    }
    @RequestMapping(value = "/deletemail/{email}/{filename}", method = RequestMethod.DELETE)
    public result deletemail(@RequestBody Mail mail,@PathVariable String email,@PathVariable String filename) throws Exception {
        return controll.delete_mail(filename,email,mail);
    }
    @RequestMapping(value = "/deleteall/{email}/{filename}", method = RequestMethod.DELETE)
    public result deleteall(@PathVariable String email,@PathVariable String filename) throws Exception {;
        return controll.delete_all(filename,email);
    }
    @RequestMapping(value = "/moveemail/{email}/{filename}/{filename1}", method = RequestMethod.POST)
    public String moveemail(@RequestBody Mail mail,@PathVariable String email,@PathVariable String filename,@PathVariable String filename1) throws Exception {;
        return controll.move_mail(filename,filename1,email,mail);
    }

    @RequestMapping(value = "/addcontact/{mail}/{name}", method = RequestMethod.POST)
    public  String addcontact(@PathVariable String mail,@PathVariable String name, @RequestBody String[] mail2) throws Exception {
        return controll.addcontact(mail,name,mail2);
    }

    @RequestMapping(value = "/renamecontact/{mail}/{name}/{name2}", method = RequestMethod.POST)
    public  String renamecontact(@PathVariable String mail,@PathVariable String name, @PathVariable String name2) throws Exception {
        return controll.rename_contact(mail,name,name2);
    }
    @RequestMapping(value = "/deletecontact/{mail}/{name}", method = RequestMethod.DELETE)
    public  result deletecontact(@PathVariable String mail, @PathVariable String name) throws Exception {
        return controll.deletecontact(mail,name);
    }
    @RequestMapping(value = "/loadcontact/{mail}", method = RequestMethod.GET)
    public Map<String, String[]> loadcontact(@PathVariable String mail) throws Exception {
        String path=controll.myfile.getDir_path()+"\\"+controll.get_name(mail)+"\\"+"contacts.json";
        return controll.load_contact(path);
    }

    @RequestMapping(value = "/deletefolder/{mail}/{name}", method = RequestMethod.DELETE)
    public  result deletefolder(@PathVariable String mail, @PathVariable String name) throws Exception {
        return controll.deletefolder(mail,name);
    }
    @RequestMapping(value = "/renamefolder/{mail}/{name}/{name1}", method = RequestMethod.GET)
    public  String renamefolder(@PathVariable String mail, @PathVariable String name,@PathVariable String name1) throws Exception {
        return controll.renamefolder(mail,name,name1);
    }

    @GetMapping("/getfiles/{fileName}/{from}")
    public ResponseEntity<UrlResource>  getFiles (@PathVariable String fileName,@PathVariable String from) throws Exception {
        Path paths = controll.getfiles(fileName,from);
        try {
            UrlResource resource = new UrlResource(paths.toUri());
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("File-Name", fileName);
            httpHeaders.add(HttpHeaders.CONTENT_DISPOSITION, "attachment;File-Name=" + resource.getFilename());
            return  ResponseEntity.ok().contentType(MediaType.parseMediaType(Files.probeContentType(paths))).headers(httpHeaders).body( resource) ;
        } catch (Exception e) {
            e.printStackTrace();
            return null ;
        }
    }
    @PostMapping("/attachments/{to}/{from}")
    public ArrayList<String> handleattachmnets(@RequestParam("attachment") MultipartFile [] attachments, @PathVariable  String to, @PathVariable String from) throws Exception {
        ArrayList<String> names= controll.handleattachmnets1(attachments,to,from);
        return names;
    }




}
