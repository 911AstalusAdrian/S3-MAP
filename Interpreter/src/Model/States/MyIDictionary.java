package Model.States;

import java.util.HashSet;
import java.util.Map;

public interface MyIDictionary<T, K> {
    boolean isEmpty();
    K get(T key);
    K remove(T key);
    boolean isDefined(T key);
    void update(T key, K val);
    String toString();
    Map<T,K> getContent();
    MyIDictionary<T, K> clone();
    int size();
}
