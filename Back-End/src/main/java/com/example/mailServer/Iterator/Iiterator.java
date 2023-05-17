package com.example.mailServer.Iterator;

public interface Iiterator<T>{
    boolean hasNext();
    T next();
    void increment();
}
