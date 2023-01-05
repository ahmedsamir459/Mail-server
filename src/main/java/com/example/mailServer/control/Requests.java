package com.example.mailServer.control;

import com.example.mailServer.Modules.Mail;
import com.example.mailServer.Modules.result;
import org.json.simple.JSONArray;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping
public class Requests {
    Control controll=new Control();
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
    @RequestMapping(value="/adddraft/{email}",method = RequestMethod.POST)
    @ResponseBody
    public result adddraft(@RequestBody Mail mail, @PathVariable String email) throws Exception {
        return controll.adddraft(email,mail);
    }
    @RequestMapping(value = "/load/{email}/{fileName}",method = RequestMethod.GET)
    public JSONArray load_mail(@PathVariable String email,@PathVariable String fileName) throws Exception {
        return controll.load_file(email,fileName);
    }
    @RequestMapping( value = "/filter/{email}/{filename}/{feature}/{target}",method = RequestMethod.GET)
    @ResponseBody
    public JSONArray filter(@PathVariable String email,@PathVariable String filename,@PathVariable String feature,@PathVariable String target ) throws Exception {
        String path=controll.myfile.getDir_path()+File.separator+controll.sr.get_name(email)+File.separator+filename+".json";
        JSONArray json1=controll.filter(feature,target,path);
        return json1;
    }
    @RequestMapping(value ="/sort/{email}/{filename}/{feature}/{value}",method = RequestMethod.GET)
    @ResponseBody
    public JSONArray sort(@PathVariable String email,@PathVariable String filename,@PathVariable String feature,@PathVariable boolean value) throws Exception {
        JSONArray array=controll.sort(email,feature,filename,value);
        return array;
    }
    @RequestMapping(value = "/addfolder/{mail}/{name}", method = RequestMethod.GET)
    public String addfolder(@PathVariable String mail,@PathVariable String name) throws Exception {
        return controll.addfolder(mail,name);
    }
    @RequestMapping(value = "/deletemail/{email}/{filename}", method = RequestMethod.DELETE)
    public result deletemail(@RequestBody Mail[] mail,@PathVariable String email,@PathVariable String filename) throws Exception {
        return controll.delete_mail(filename,email,mail);}
    @RequestMapping(value = "/deleteall/{email}/{filename}", method = RequestMethod.DELETE)
    public result deleteall(@PathVariable String email,@PathVariable String filename) throws Exception {;
        return controll.delete_all(filename,email);}
    @RequestMapping(value = "/moveemail/{email}/{filename}/{filename1}", method = RequestMethod.POST)
    public result moveemail(@RequestBody Mail[] mail,@PathVariable String email,@PathVariable String filename,@PathVariable String filename1) throws Exception {;
        return controll.move_mail(filename,filename1,email,mail);
    }
    @RequestMapping(value = "/addcontact/{mail}/{name}", method = RequestMethod.POST)
    public  result addcontact(@PathVariable String mail,@PathVariable String name, @RequestBody String[] mail2) throws Exception {
        return controll.addcontact(mail,name,mail2);
    }
    @RequestMapping(value = "/renamecontact/{mail}/{name}/{name2}", method = RequestMethod.GET)
    public  result renamecontact(@PathVariable String mail,@PathVariable String name, @PathVariable String name2) throws Exception {
        return controll.rename_contact(mail,name,name2);
    }
    @RequestMapping(value = "/deletecontact/{mail}/{name}", method = RequestMethod.DELETE)
    public  result deletecontact(@PathVariable String mail, @PathVariable String name) throws Exception {
        return controll.deletecontact(mail,name);
    }
    @RequestMapping(value = "/loadcontact/{mail}", method = RequestMethod.GET)
    public Map<String, String[]> loadcontact(@PathVariable String mail) throws Exception {
        String path=controll.myfile.getDir_path()+"\\"+controll.sr.get_name(mail)+"\\"+"contacts.json";
        return controll.load_contact(path);
    }
    @RequestMapping(value = "/sortcontact/{mail}/{value}", method = RequestMethod.GET)
    public Map<String, String[]> sortcontact(@PathVariable String mail,@PathVariable boolean value) throws Exception {
        String path=controll.myfile.getDir_path()+"\\"+controll.sr.get_name(mail)+"\\"+"contacts.json";
        return controll.sort_contact(path,value);
    }
    @RequestMapping(value = "/deletefolder/{mail}/{name}", method = RequestMethod.DELETE)
    public  result deletefolder(@PathVariable String mail, @PathVariable String name) throws Exception {
        return controll.deletefolder(mail,name);
    }
    @RequestMapping(value="/filtercontact/{email}/{target}",method = RequestMethod.GET)
    public Map<String, String[]> filtercontact(@PathVariable String email,@PathVariable String target) throws Exception {
        return controll.filtercontact(email,target);
    }
    @RequestMapping(value = "/renamefolder/{mail}/{name}/{name1}", method = RequestMethod.GET)
    public  result renamefolder(@PathVariable String mail, @PathVariable String name,@PathVariable String name1) throws Exception {
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
        return controll.handleattachmnets1(attachments,to,from);
    }
    @RequestMapping(value="/filesreload/{email}",method = RequestMethod.GET)
    public Map<String, Integer> reload(@PathVariable String email) throws Exception {
        return controll.reload(email);
    }

}
