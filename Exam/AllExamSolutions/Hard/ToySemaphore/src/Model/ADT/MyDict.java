package Model.ADT;


import Exceptions.MyException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MyDict<K, V> implements MyIDictionary<K, V> {
    private final HashMap<K, V> dictionary;

    public MyDict() {
        dictionary = new HashMap<>();
    }

    @Override
    public void put(K key, V value) {
        synchronized (dictionary) {
            dictionary.put(key, value);
        }
    }

    @Override
    public boolean isDefined(K key) {
        synchronized (dictionary) {
            return dictionary.containsKey(key);
        }
    }

    @Override
    public V getValue(K key) throws MyException {
        synchronized (dictionary) {
            if (dictionary.containsKey(key)) {
                return dictionary.get(key);
            }
        }
        throw new MyException("The key " + key + " is not defined");
    }

    @Override
    public Collection<V> values() {
        synchronized (dictionary) {
            return dictionary.values();
        }
    }

    @Override
    public void update(K key, V value) throws MyException {
        synchronized (dictionary) {
            if (dictionary.containsKey(key)) {
                dictionary.put(key, value);
            } else {
                throw new MyException("The key " + key + " is not defined");
            }
        }
    }

    @Override
    public String toString() {
        synchronized (dictionary) {
            return dictionary.toString();
        }
    }

    @Override
    public void remove(K key) {
        synchronized (dictionary) {
            dictionary.remove(key);
        }
    }

    @Override
    public Map<K, V> getContent() {
        synchronized (dictionary) {
            return dictionary;
        }
    }

    @Override
    public MyIDictionary<K, V> copy() throws MyException {
        synchronized (dictionary) {
            MyIDictionary<K, V> copyDict = new MyDict<>();
            for (K key : keySet())
                copyDict.put(key, getValue(key));
            return copyDict;
        }
    }

    public Set<K> keySet() {
        synchronized (dictionary) {
            return dictionary.keySet();
        }
    }
}

