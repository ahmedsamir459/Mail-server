package com.example.mailServer.Iterator;
import java.util.Iterator;
import java.util.Map;

public class MapIterator<K,V> implements Iiterator<Map.Entry<K,V>> {
    public final Iterator<Map.Entry<K,V>> iterator;
    public MapIterator(Map<K, V> map) {
        this.iterator = map.entrySet().iterator();
    }
    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }
    @Override
    public Map.Entry<K, V> next() {
        return iterator.next();
    }
    @Override
    public void increment() {
        iterator.next();
    }
}