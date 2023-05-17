package com.example.mailServer.Iterator;

public class ArrayIterator<T> implements Iiterator<T> {
private final T[] array;
private int index;

    public ArrayIterator(T[] array) {
        this.array = array;
        this.index = 0;
    }
    @Override
    public boolean hasNext() {
        return index< array.length;
    }
    @Override
    public T next() {
        return array[index];
    }
    public void increment(){
        index++;
    }
}
