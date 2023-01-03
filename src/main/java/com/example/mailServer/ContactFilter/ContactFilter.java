package com.example.mailServer.ContactFilter;
import java.util.HashMap;
import java.util.Map;

public class ContactFilter implements IContactFilter {
    private String name;
    public ContactFilter(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    @Override
    public Map<String, String[]> meetCriteria(Map<String, String[]> map) {
        Map<String,String[]> filterd_map=new HashMap<>();
        for (Map.Entry<String,String[]> entry : map.entrySet())
            if(entry.getKey().toLowerCase().contains(name)){
                filterd_map.put(entry.getKey(), entry.getValue());
            }
        return filterd_map;
    }
}