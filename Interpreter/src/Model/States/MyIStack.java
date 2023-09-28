package Model.States;

import java.util.Stack;

public interface MyIStack<T> {
    T pop();
    void push(T el);
    T peek();
    Boolean isEmpty();
    String toString();
    Stack<T> cloneStack();
}
