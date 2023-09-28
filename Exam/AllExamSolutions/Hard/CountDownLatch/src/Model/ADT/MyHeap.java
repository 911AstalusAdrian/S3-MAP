package Model.ADT;

import Exceptions.MyException;
import Model.Values.Value;

import java.util.HashMap;
import java.util.Random;

public class MyHeap implements MyIHeap {
    private final HashMap<Integer, Value> map;
    private Integer freeValue;

    public MyHeap() {
        this.map = new HashMap<>();
        this.freeValue = 1;
    }

    @Override
    public Integer getFreeValue() {
        synchronized (this) {
            return freeValue;
        }
    }

    @Override
    public HashMap<Integer, Value> getContent() {
        synchronized (this) {
            return map;
        }
    }

    @Override
    public void setContent(HashMap<Integer, Value> newMap) {
        synchronized (this) {
            map.clear();
            for (Integer i : newMap.keySet()) {
                map.put(i, newMap.get(i));
            }
        }
    }

    @Override
    public Integer add(Value value) {
        synchronized (this) {
            map.put(freeValue, value);
            Integer toReturn = freeValue;
            freeValue += 1;
            return toReturn;
        }
    }

    @Override
    public boolean isDefined(Integer key) {
        synchronized (this) {
            return map.containsKey(key);
        }
    }

    @Override
    public void update(Integer position, Value value) throws MyException {
        synchronized (this) {
            if (!map.containsKey(position))
                throw new MyException(String.format("%d is not present in the heap", position));
            map.put(position, value);
        }
    }

    @Override
    public Value get(Integer position) throws MyException {
        synchronized (this) {
            if (!map.containsKey(position))
                throw new MyException(String.format("%d is not present in the heap", position));
            return map.get(position);
        }
    }
}
