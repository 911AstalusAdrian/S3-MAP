package Model.ADT;

import Exceptions.MyException;

import java.util.*;

public class MyStack<Elem> implements MyIStack<Elem>{
    private final Stack<Elem> stack;

    public MyStack() {
        stack = new Stack<>();
    }

    @Override
    public Elem pop() throws MyException {
        if (stack.isEmpty())
            throw new MyException("Stack is empty");
        return stack.pop();
    }

    @Override
    public Elem peek() throws MyException {
        if (stack.isEmpty())
            throw new MyException("Stack is empty");
        return stack.peek();
    }

    @Override
    public void push(Elem v) {
        stack.push(v);
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public List<Elem> getReversed() {
        List<Elem> list = Arrays.asList((Elem[]) stack.toArray());
        Collections.reverse(list);
        return list;
    }

    @Override
    public Iterator<Elem> iterator() {
        return stack.iterator();
    }

    @Override
    public int size() {
        return stack.size();
    }

    @Override
    public MyIStack<Elem> deep_copy() {
        MyIStack<Elem> copy_stack = new MyStack<>();
        //Uses iterator internally
        for (Elem elem : stack)
            copy_stack.push(elem);
        return copy_stack;
    }

}
