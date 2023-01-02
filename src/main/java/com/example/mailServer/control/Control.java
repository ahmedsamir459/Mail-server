package com.example.mailServer.control;

import com.example.mailServer.ContactFilter.ContactFilter;
import com.example.mailServer.Date.Day30;
import com.example.mailServer.EmailsFilter.Sort;
import com.example.mailServer.Singlton.FileSinglton;
import com.example.mailServer.EmailsFilter.EmailFilter;
import com.example.mailServer.Builder.FileBuilder;
import com.example.mailServer.Modules.Mail;
import com.example.mailServer.Services.Service;
import com.example.mailServer.Modules.result;
import com.example.mailServer.Vaildator.Validator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

import static org.junit.Assert.assertEquals;

public class Control {
    Validator vl=new Validator();
    Service sr = new Service();

    FileSinglton myfile;
    public Control(){myfile=FileSinglton.getInstance();}
    public static Map<String, String> users = new HashMap<>();
    public Map<String, String[]> load_contact(String filename) throws IOException{
        Map<String, String[]> map=new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(filename);
        if(file.length()!=0){
            map = mapper.readValue(file, new TypeReference<Map<String, String[]>>() {
            });
            return map;
        }
        else return map;
    }
    public JSONArray load_file(String email,String filename) throws Exception {
        String path=myfile.getDir_path()+File.separator+sr.get_name(email)+File.separator+filename+".json";
        System.out.println(filename);
        if(filename.equalsIgnoreCase("trash")){
            try{
                System.out.println("in trash");
                JSONParser parser = new JSONParser();
                Day30 day30=new Day30();
                org.json.simple.JSONArray array;
                array = (org.json.simple.JSONArray) parser.parse(new FileReader(path));
                JSONArray array1=day30.filter30days(array);
                System.out.println(array1);
                return day30.filter30days(array);}
            catch(Exception e){
                return new JSONArray();
            }
        }
        else{
            try{
                JSONParser parser = new JSONParser();
                org.json.simple.JSONArray array;
                array = (org.json.simple.JSONArray) parser.parse(new FileReader(path));
                return array;}
            catch(Exception e){
                return new JSONArray();}
        }
    }
    public void save_contact(String filename,  Map<String,String[]> m) throws IOException {
        FileWriter myWriter = new FileWriter(filename);
        myWriter.write(new JSONObject(m).toString());
        myWriter.close();
    }
    public void trashed(String email,Mail[] mail) throws Exception {
        String path=myfile.getDir_path()+"\\"+sr.get_name(email)+"\\trash.json";
        JSONArray array=sr.load_mails(path);
        for(Mail m:mail) {System.out.println(m.getFrom());}
        for(int i=0;i<mail.length;i++){
            JSONObject jsonObject=new JSONObject(mail[i]);
            array.add(jsonObject);}
        sr.save_mails(path,array);
    }
    
    public String signin(String email, String password) throws Exception {
        return vl.check_user(email,password,users,myfile.getMyObj());
    }
    public String signup(String email,String password) throws Exception {
        String result="";
        try {
            Path path = Paths.get(myfile.getPath());
            int size = (int) Files.size(path);
            if (size != 0) {
                result = vl.check_user_signup(email,password,users,myfile.getMyObj());
                System.out.println(result);
                if (result.equals("not found")) {
                    FileBuilder f = new FileBuilder();
                    f.BuildFile(sr.get_name(email), myfile.getDir_path());
                    ObjectMapper mapper = new ObjectMapper();
                    users = new HashMap<>();
                    users = mapper.readValue(myfile.getMyObj(), new TypeReference<Map<String, String>>() {
                    });
                    users.put(email, password);
                    for (Map.Entry<String, String> entry : users.entrySet())
                        System.out.println("Key = " + entry.getKey() +
                                ", Value = " + entry.getValue());
                    JSONObject jsonObj = new JSONObject(users);
//                    System.out.println(jsonObj + "hena");
                    FileWriter myWriter = new FileWriter(myfile.getPath());
                    myWriter.append(jsonObj.toString());
                    myWriter.close();}}
            else {

                FileBuilder f = new FileBuilder();
                f.BuildFile(sr.get_name(email), myfile.getDir_path());
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

    public result save_mail(Mail mail) throws Exception {
        String filename1,filename2;
        filename1=sr.get_name(mail.getFrom());
        filename2 = sr.get_name(mail.getTo());
        String path1=myfile.getDir_path()+"\\"+filename2+"\\inbox.json";
        String path2=myfile.getDir_path()+"\\"+filename1+"\\sent.json";
        JSONObject jsonObject=new JSONObject(mail);
        org.json.simple.JSONArray array=new org.json.simple.JSONArray();
        JSONArray array1=new JSONArray();
        Path path= Paths.get(path1);
        Path path_2= Paths.get(path2);
        try{
            int sz1=(int) Files.size(path);
            int sz2=(int) Files.size(path_2);
            if(sz1!=0) {array = sr.load_mails(path1);}
            if(sz2!=0){array1=sr.load_mails(path2);}
            array.add(jsonObject);
            array1.add(jsonObject);
            sr.save_mails(path1,array);
            sr.save_mails(path2,array1);
            sr.load_mails(path1);
            sr.load_mails(path2);
            return new result("saved",true);
        }
        catch(Exception e){
            return new result("error",false);
        }
    }
    public JSONArray filter(String feature, String target, String filename) throws IOException, ParseException {
        JSONArray array=sr.load_mails(filename);
        System.out.println("array"+array);
        EmailFilter filter=new EmailFilter(feature,target);
        ArrayList result=filter.meetCriteria(array);
        JSONArray array1=new JSONArray();
        array1.addAll(result);
        return array1;
    }
    public JSONArray sort(String email,String feature, String filename) throws Exception {
        String path=myfile.getDir_path()+File.separator+sr.get_name(email)+File.separator+filename+".json";
        JSONArray array=sr.load_mails(path);
        Sort sort=new Sort();
        ArrayList array1=sort.sort(array,feature);
        JSONArray array2=new JSONArray();
        array2.addAll(array1);
        System.out.println(array2);
        return array2;
    }
    public result delete_mail(String filename, String email, Mail[] index) throws Exception {
        String path1=myfile.getDir_path()+"\\"+sr.get_name(email)+"\\"+filename+".json";
        JSONArray array=sr.load_mails(path1);
        if(filename!="trash"){
            trashed(email,index);
        }
        try {
            for (int j = 0; j < index.length; j++) {
                JSONObject json = new JSONObject(index[j]);
                for (int i = 0; i < array.size(); i++) {
                    if (array.get(i).toString().equals(json.toString())) {
                        array.remove(i);
                        break;}}}
            sr.save_mails(path1, array);
            return new result("deleted", true);
        }
        catch(Exception e){
            return new result("error",false);
        }
    }
    public result delete_all(String filename, String email) throws Exception {
        String path1=myfile.getDir_path()+"\\"+sr.get_name(email)+"\\"+filename+".json";
        FileWriter myWriter = new FileWriter(path1);
        myWriter.write("");
        myWriter.close();
        return new result("deleted",true);
    }
    public Map<String,String[]> filtercontact(String email,String target) throws Exception {
        String path=myfile.getDir_path()+"\\"+sr.get_name(email)+"\\contacts.json";
        File file=new File(path);
        ObjectMapper mapper = new ObjectMapper();
        ContactFilter filter=new ContactFilter(target);
        Map<String,String[]> contacts = mapper.readValue(file, new TypeReference<Map<String, String[]>>() {});
        Map<String,String[]> result=filter.meetCriteria(contacts);
        return result;
    }
    public String move_mail(String filename1, String filename2, String email, Mail[] index) throws Exception {
        String path1=myfile.getDir_path()+"\\"+sr.get_name(email)+"\\"+filename1+".json";
        String path2=myfile.getDir_path()+"\\"+sr.get_name(email)+"\\"+filename2+".json";
        try {
            JSONArray array1 = sr.load_mails(path1);
            JSONArray array2 = sr.load_mails(path2);
            for (int j = 0; j < index.length; j++) {
                JSONObject json = new JSONObject(index[j]);
                for (int i = 0; i < array1.size(); i++) {
                    if (array1.get(i).toString().equals(json.toString())) {
                        array2.add(array1.get(i));
                        array1.remove(i);
                        break;
                    }
                }
            }
            sr.save_mails(path1, array1);
            sr.save_mails(path2, array2);
            return "moved";
        }
        catch(Exception e){
            return "error";
        }
    }
    public String move_all(String email,String filename1, String filename2) throws Exception {
        String path1=myfile.getDir_path()+"\\"+sr.get_name(email)+"\\"+filename1+".json";
        String path2=myfile.getDir_path()+"\\"+sr.get_name(email)+"\\"+filename2+".json";
        JSONArray array1=sr.load_mails(path1);
        JSONArray array2=sr.load_mails(path2);
        for(int i=0;i<array1.size();i++)
        {
            System.out.println(array1.get(i));
            array2.add(array1.get(i));
            array1.remove(i);
        }
        sr.save_mails(path1,array1);
        sr.save_mails(path2,array2);
        return "done";
    }
    public String addfolder(String mail, String filename) throws Exception {
        String path=myfile.getDir_path()+"\\"+sr.get_name(mail)+"\\"+filename+".json";
        File file = new File(path);
        if (file.createNewFile()) {
            System.out.println("File created: " + file.getName());
            return "File created: " + file.getName();
        } else {
            System.out.println("File already exists.");
            return "File already exists.";

        }
    }
    public result deletefolder(String mail, String filename) throws Exception {
        String path=myfile.getDir_path()+"\\"+sr.get_name(mail)+"\\"+filename+".json";
        File file = new File(path);
        if (file.delete()) {
            System.out.println("File deleted: " + file.getName());
            return new result("File deleted: " + file.getName(),true);
        } else {
            System.out.println("File not found.");
            return new result("File not found.",false);

        }
    }
    public String addcontact(String mail, String name,String [] mail2) throws Exception {
        String path=myfile.getDir_path()+"\\"+sr.get_name(mail)+"\\"+"contacts.json";
        sr.create_file(path);
        Map<String,String[]>m=new HashMap<>();
        if(sr.getSize()!=0){
            m=load_contact(path);
            if(m.containsKey(name)){
                return "contact already exists";
            }
        }
        m.put(name,mail2);
        save_contact(path,m);
        return "done";
    }
    public String rename_contact(String mail, String name,String name2) throws Exception {
        String path=myfile.getDir_path()+"\\"+sr.get_name(mail)+"\\"+"contacts.json";
        Map<String,String[]> m=load_contact(path);
        if(m.containsKey(name)){
            String [] m2=m.get(name);
            m.remove(name);
            m.put(name2,m2);
            save_contact(path,m);
            return "done";
        }
        return "contact not found";
    }
    public result deletecontact(String mail, String name) throws Exception {
        String path=myfile.getDir_path()+"\\"+sr.get_name(mail)+"\\"+"contacts.json";
        Map<String,String[]> map=load_contact(path);
        if(map.containsKey(name)){
            map.remove(name);
            save_contact(path,map);
            return new result("done",false);
        }
        return new result("contact not found",true);
    }
    public String renamefolder(String mail, String filename1, String filename2) throws Exception {
        String path1=myfile.getDir_path()+"\\"+sr.get_name(mail)+"\\"+filename1+".json";
        String path2=myfile.getDir_path()+"\\"+sr.get_name(mail)+"\\"+filename2+".json";
        File file1 = new File(path1);
        File file2 = new File(path2);
        if (file1.renameTo(file2)) {
            System.out.println("File renamed: " + file1.getName());
            return "File renamed: " + file1.getName();
        } else {
            System.out.println("File not found.");
            return "File not found.";

        }
    }
    public String adddraft(String mail, Mail index) throws Exception {
        String path=myfile.getDir_path()+"\\"+sr.get_name(mail)+"\\"+"draft.json";
        JSONArray array=sr.load_mails(path);
        JSONObject json=new JSONObject(index);
        array.add(json);
        sr.save_mails(path,array);
        return "done";
    }
    public Path getfiles(String fileName,String from2) throws Exception {
        String from=sr.get_name(from2);
        Path res = Paths.get(myfile.getDir_path()+"\\"+from).toAbsolutePath().normalize().resolve(fileName) ;
        System.out.println(res);
        if(Files.exists(res)){
            System.out.println("will send");
            return res ;
        }
        System.out.println("will NOT send");
        return null ;

    }
    public ArrayList<String> handleattachmnets1(MultipartFile[] attachments, String to, String from) throws Exception {
        ArrayList<String> names=new ArrayList<>();
        String filename1=sr.get_name(to);
        String filename2=sr.get_name(from);
        for(MultipartFile attachment:attachments){
            try {
                names.add(attachment.getOriginalFilename());
                Path copyLocation = Paths.get(myfile.getDir_path() +File.separator+filename1 +File.separator + StringUtils.cleanPath(attachment.getOriginalFilename()));
                Files.copy(attachment.getInputStream(),copyLocation, StandardCopyOption.REPLACE_EXISTING);
                try{
                Path copyLocation1 = Paths.get(myfile.getDir_path() +File.separator+filename2 +File.separator + StringUtils.cleanPath(attachment.getOriginalFilename()));
                Files.copy(attachment.getInputStream(),copyLocation1, StandardCopyOption.REPLACE_EXISTING);}
                catch (Exception e)
                {
                    System.out.println("File already exists");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return names;
    }
    public Map<String,Integer> reload (String email) throws Exception {
        String path=myfile.getDir_path()+"\\"+sr.get_name(email);
        return sr.getfiles(path);
    }


}
