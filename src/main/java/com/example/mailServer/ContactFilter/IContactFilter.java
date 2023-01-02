package com.example.mailServer.ContactFilter;
import java.util.Map;
public interface IContactFilter {
    public Map<String,String[]> meetCriteria(Map<String,String[]> map);
}
