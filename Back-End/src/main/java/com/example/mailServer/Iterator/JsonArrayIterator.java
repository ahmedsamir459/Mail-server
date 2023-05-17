package com.example.mailServer.Iterator;

import org.json.simple.JSONArray;

public class JsonArrayIterator<T> implements Iiterator<T> {

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
        return (T) array.get(index);
    }
    public void increment(){
        index++;
    }

}
