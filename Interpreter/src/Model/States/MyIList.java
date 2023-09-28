package Model.States;

public interface MyIList<T> {
    int size();
    void add(T el);
    boolean contains(T el);
    boolean remove(T el);
    T get(T el);
    int getIndex(T el);
    T getElByIndex(int index);
    boolean isEmpty();
    String toString();
}
