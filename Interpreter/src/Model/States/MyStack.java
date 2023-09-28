package Model.States;

import java.util.*;

public class MyStack<T> implements  MyIStack<T>{

    private final Stack<T> arr;

    public MyStack(){arr = new Stack<T>();}

    @Override
    public T pop() {return arr.pop();}

    @Override
    public void push(T el) {arr.push(el);}

    @Override
    public T peek() {
        return arr.peek();
    }

    @Override
    public Boolean isEmpty() {
        return arr.isEmpty();
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        List<T> listForm = Arrays.asList((T[])arr.toArray());
        Collections.reverse(listForm);
        for(T el: listForm)
            sb.append('\t' + el.toString() + '\n');

        return sb.toString();
    }

    @Override
    public Stack<T> cloneStack(){
        Stack<T> clone = (Stack<T>) arr.clone();
        return clone;
        }
}