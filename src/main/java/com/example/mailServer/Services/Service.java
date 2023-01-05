package com.example.mailServer.Services;

import com.example.mailServer.Adapter.MapAdapter;
import com.example.mailServer.DateComp.Day30;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class Service {
    private int size=0;
    public  void create_file( String path) {
        java.io.File myObj = null;
        try {
            myObj = new java.io.File(path);
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }} catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        size=(int)myObj.length();
    }
    public void CreateFolder(String path){
            java.io.File f = new java.io.File(path);
        if (f.mkdir()) {
            System.out.println("Folder created: " + f.getName());
        } else {
            System.out.println("Folder already exists.");
        }
    }
    public int getSize(){
        return size;
    }
    public Map<String, Integer> getfiles(String foldername) throws IOException, ParseException {
        Path folderPath = Paths.get(foldername);
        HashMap<String, Integer> files = new HashMap<>();
        try (Stream<Path> paths = Files.walk(folderPath)) {
            paths.filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".json"))
                    .forEach(path -> {
                        try {
                            if(path.toString().contains("contact")){
                                File file = new File(path.toString());
                                MapAdapter mapAdapter=new MapAdapter();
                                if(file.length()!=0){
                                    HashMap<String,String[]>m=mapAdapter.getSMap(file);
                                    files.put(path.getFileName().toString().replaceFirst("[.][^.]+$", ""),m.size());
                                }
                                else{
                                    files.put(path.getFileName().toString().replaceFirst("[.][^.]+$", ""),0);

                                }

                            }
                            else {
                                Day30 day30 = new Day30();
                                JSONArray jsonArray = load_mails(path.toString());

                                if(path.toString().contains("trash")) {
                                    System.out.println("trash"+jsonArray);
                                    files.put(path.getFileName().toString().replaceFirst("[.][^.]+$", ""), day30.filter30days(jsonArray).size());
                                }
                                else
                                    files.put(path.getFileName().toString().replaceFirst("[.][^.]+$", ""),jsonArray.size());
                            };

                        } catch (ParseException | IOException e) {
                            throw new RuntimeException(e);
                        }
                    });

        }
//        File folder = new File(foldername);
//        File[] listOfFiles = folder.listFiles();
//        for (int i = 0; i < listOfFiles.length; i++) {
//            if (listOfFiles[i].isFile()) {
//                files.put(listOfFiles[i].getName().replaceFirst("[.][^.]+$", ""), load_mails(listOfFiles[i].getPath()).size());
//                System.out.println(listOfFiles[i].canExecute());
//            } else if (listOfFiles[i].isDirectory()) {
//                System.out.println("Directory " + listOfFiles[i].getName());
//            }
//        }
        return files;
    }
    public String get_name(String email) {
        String filename;
        int index = email.indexOf("@");
        filename = email.substring(0, index);
        return filename;
    }
    public JSONArray load_mails(String filename) throws ParseException {
        try{
            org.json.simple.JSONArray array;
            ObjectMapper mapper = new ObjectMapper();
            array =mapper.readValue(new File(filename), JSONArray.class);
            if (filename.contains("trash"))System.out.println("el3"+array);
            return array;}
        catch(Exception e){
            return new JSONArray();

        }

    }
    public void save_mails(String filename, org.json.simple.JSONArray array) throws IOException {
        FileWriter myWriter = new FileWriter(filename);
        myWriter.write(array.toString());
        myWriter.close();
    }

}

