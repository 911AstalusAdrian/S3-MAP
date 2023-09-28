package Model.ADT;

import Exceptions.MyException;
import Model.Values.Value;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MyList<TElem> implements MyIList<TElem> {
    private final List<TElem> list;

    public MyList() {
        list = new ArrayList<>();
    }

    public MyList(ArrayList <TElem> list) {
        this.list = list;
    }

    @Override
    public void add(TElem element) {
        synchronized (list) {
            list.add(element);
        }
    }

    @Override
    public TElem pop() throws MyException {
        synchronized (list) {
            if (list.isEmpty()) {
                throw new MyException("Trying to pop from an empty list!");
            }
//        TElem poppedElement = list.get(list.size() - 1);
//        list.remove(list.size() - 1);
//        return poppedElement;
            return list.remove(0);
        }
    }

    @Override
    public boolean isEmpty() {
        synchronized (list) {
            return list.isEmpty();
        }
    }

    @Override
    public List<TElem> getList() {
        synchronized (list) {
            return list;
        }
    }

    @Override
    public String toString() {
        synchronized (list) {
            return list.toString();
        }
    }

    @Override
    public Iterator<TElem> iterator() {
        synchronized (list) {
            return list.iterator();
        }
    }
}
