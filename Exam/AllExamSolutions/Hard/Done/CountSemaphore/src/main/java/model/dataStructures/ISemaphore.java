package model.dataStructures;

import javafx.util.Pair;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ISemaphore {
    boolean isDefined(int address);

    int allocate(Integer value);

    Pair<Integer, List<Integer>> lookup(int id);

    void update(int id, Integer value);

    int size();

    ISemaphore deep_copy();

    String toString();

    Set<Map.Entry<Integer, Pair<Integer, List<Integer>>>> entrySet();

    Map<Integer, Pair<Integer, List<Integer>>> getContent();

    void setContent(Map<Integer, Pair<Integer, List<Integer>>> content);

    Set<Integer> keys();
}
