package com.example.mailServer.Iterator;

import org.json.simple.JSONArray;

import java.util.Iterator;

public class JsonArrayIterator<T> implements Iterator<T> {

    private final JSONArray array;
    private int index;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public JsonArrayIterator(JSONArray array) {
        this.array = array;
        this.index = 0;
    }

    @Override
    public boolean hasNext() {
        return index < array.size();
    }

    @Override
    public T next() {
        return (T) array.get(index++);
    }
    public void remove(T obj)
    {
        array.remove(obj);
        index--;
    }
//    public void increment()
//    {
//        index++;
//    }

}
