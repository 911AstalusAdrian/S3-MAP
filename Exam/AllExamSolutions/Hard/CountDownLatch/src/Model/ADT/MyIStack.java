package Model.ADT;

import Exceptions.MyException;

import java.util.List;

public interface MyIStack<T> extends Iterable<T>{
    T pop() throws MyException;

    T peek() throws MyException;

    void push(T v);

    boolean isEmpty();

    List<T> getReversed();
}
