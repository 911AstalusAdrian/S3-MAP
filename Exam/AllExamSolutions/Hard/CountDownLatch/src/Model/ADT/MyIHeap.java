package Model.ADT;

import Exceptions.MyException;
import Model.Values.Value;

import java.util.HashMap;

public interface MyIHeap {
    Integer getFreeValue();

    HashMap<Integer, Value> getContent();

    void setContent(HashMap<Integer, Value> newMap);

    Integer add(Value value);

    boolean isDefined(Integer key);

    void update(Integer position, Value value) throws MyException;

    Value get(Integer position) throws MyException;
}
