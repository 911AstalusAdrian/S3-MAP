package Model.States;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class MyDictionary<T, K> implements MyIDictionary<T,K>{
    private final HashMap<T,K> dict;

    public MyDictionary(){dict = new HashMap<T,K>();}


    @Override
    public boolean isEmpty() {
        return dict.isEmpty();
    }

    @Override
    public K get(T key) {
        return dict.get(key);
    }

    @Override
    public boolean isDefined(T key) {
        return dict.containsKey(key);
    }

    @Override
    public void update(T key, K val) {
        dict.put(key,val);
    }

    @Override
    public K remove(T key) {
        return dict.remove(key);
    }

    public String toString(){
        return dict.toString();
    }

    @Override
    public Map<T, K> getContent() {
        return dict;
    }

    @Override
    public MyIDictionary<T, K> clone() {
        MyIDictionary<T, K> clone = new MyDictionary<>();
        for(T key: dict.keySet())
            clone.update(key, dict.get(key));
        return clone;
    }

    @Override
    public int size() {
        return dict.size();
    }


}
