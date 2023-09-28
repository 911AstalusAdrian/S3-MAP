package model.dataStructures;

import java.util.Iterator;

public interface IStack<T> extends Iterable<T> {
    T top() throws Exception;

    T pop() throws Exception;

    void push(T v);

    boolean isEmpty();

    int size();

    IStack<T> deep_copy();

    String toString();

    Iterator<T> iterator();
}
