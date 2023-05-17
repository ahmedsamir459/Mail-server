package com.example.mailServer.Adapter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class MapAdapter {
    ObjectMapper mapper;
    public HashMap<String,String[]> getSMap(File myObj) throws IOException {
        mapper = new ObjectMapper();
        HashMap<String,String[]> users = mapper.readValue(myObj, new TypeReference<HashMap<String,String[]>>() {
        });
        return users;}

}
