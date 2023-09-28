package Model.States;

import Model.Exceptions.ADTException;
import Model.Values.Value;

import java.util.HashMap;
import java.util.Map;

public interface IHeap {
    Integer getFreeValue();
    HashMap<Integer, Value> getHeapContent();
    void setHeapContent(Map<Integer, Value> newHeap);
    Integer add(Value val);
    void update(Integer pos, Value newValue) throws ADTException;
    Value getValue(Integer pos) throws ADTException;
}
