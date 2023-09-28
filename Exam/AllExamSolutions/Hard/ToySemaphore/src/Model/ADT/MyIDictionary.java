package Model.ADT;

import Exceptions.MyException;
import Model.Values.Value;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface MyIDictionary<K, V> {

    boolean isDefined(K key);

    void put(K key, V value);

    Set<K> keySet();

    V getValue(K key) throws MyException;

    Collection<V> values();

    void update(K key, V value) throws MyException;

    void remove(K key);

    Map<K, V> getContent();

    MyIDictionary<K, V> copy() throws MyException;
}
