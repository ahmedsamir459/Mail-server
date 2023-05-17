package com.example.mailServer.Sort;

import java.util.*;

public class ContactSort {

    public static <K, V> Map<K, V> sortByKeysD(Map<K, V> map) {
        PriorityQueue<K> queue = new PriorityQueue<>(Collections.reverseOrder());
        queue.addAll(map.keySet());
        Map<K, V> sortedMap = new LinkedHashMap<>();
        while (!queue.isEmpty()) {
            K key = queue.poll();
            sortedMap.put(key, map.get(key));
        }
        return sortedMap;
    }
    public static <K, V> Map<K, V> sortByKeysA(Map<K, V> map) {
        PriorityQueue<K> q = new PriorityQueue<>(map.keySet());
        Map<K, V> sortedMap = new LinkedHashMap<>();
        while (!q.isEmpty()) {
            K key = q.poll();
            sortedMap.put(key, map.get(key));
        }
        return sortedMap;
    }
    public Map<String, String[]> sort (Map<String, String[]> map,boolean value) {
        Map<String, String[]> sortedMap ;
        if(value){sortedMap=sortByKeysA(map);}
        else {sortedMap=sortByKeysD(map);}
        return sortedMap;
    }
}
