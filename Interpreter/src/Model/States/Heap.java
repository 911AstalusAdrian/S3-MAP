package Model.States;

import Model.Exceptions.ADTException;
import Model.Values.Value;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Heap implements IHeap{

    HashMap<Integer, Value> heap;
    Integer freeValue;

    public Integer newValue(){
        Random r = new Random();
        freeValue = r.nextInt(100);
        if(freeValue == 0 || heap.containsKey(freeValue))
            freeValue = r.nextInt();
        return freeValue;
    }

    public Heap(){
        heap = new HashMap<>();
        freeValue = newValue();
    }

    public Heap(HashMap<Integer, Value> newHeap){
        heap = newHeap;
        freeValue = newValue();
    }

    @Override
    public Integer getFreeValue() {
        return this.freeValue;
    }

    @Override
    public HashMap<Integer, Value> getHeapContent() {
        return this.heap;
    }

    @Override
    public void setHeapContent(Map<Integer, Value> newHeap) {
            heap.clear();
            for(Integer i: newHeap.keySet())
                heap.put(i, newHeap.get(i));
    }

    @Override
    public Integer add(Value val) {
        heap.put(freeValue, val);
        Integer pos = freeValue;
        freeValue = newValue();
        return pos;
    }

    @Override
    public void update(Integer pos, Value newValue) throws ADTException {
        if(!heap.containsKey(pos))
            throw new ADTException("Invalid heap position for update.");
        heap.put(pos, newValue);
    }

    @Override
    public Value getValue(Integer pos) throws ADTException {
        if(!heap.containsKey(pos))
            throw new ADTException("No heap value on this position.");
        return heap.get(pos);
    }

    @Override
    public String toString(){
        return heap.toString();
    }
}
