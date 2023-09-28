package Model.States;

import java.util.ArrayList;

public class MyList<T> implements MyIList<T> {
    private final ArrayList<T> arr;

    public MyList(){arr = new ArrayList<T>();}

    @Override
    public int size() {
        return arr.size();
    }

    @Override
    public void add(T el) {arr.add(el);}

    @Override
    public boolean contains(T el) {return arr.contains(el);}

    @Override
    public boolean remove(T el) {return arr.remove(el);}

    @Override
    public T get(T el) {return arr.get(getIndex(el));}

    @Override
    public int getIndex(T el) {return arr.indexOf(el);}

    @Override
    public T getElByIndex(int index) {return arr.get(index);}

    @Override
    public boolean isEmpty() {return arr.isEmpty();}

    @Override
    public String toString() {return arr.toString();}
}
