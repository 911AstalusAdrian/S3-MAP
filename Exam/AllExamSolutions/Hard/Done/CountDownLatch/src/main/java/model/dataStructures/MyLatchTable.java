package model.dataStructures;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class MyLatchTable<T2> implements ITable<T2>{
    private Map<Integer, T2> latchTable = new HashMap<Integer, T2>();
    private AtomicInteger next_address = new AtomicInteger(1);

    public MyLatchTable() {
    }

    private MyLatchTable(Map<Integer, T2> copy_table, int next_address) {
        this.latchTable = copy_table;
        this.next_address.set(next_address);
    }

    @Override
    public boolean isDefined(int address) {
        synchronized (this) {
            return latchTable.containsKey(address);
        }
    }

    @Override
    public int allocate(T2 value) {
        int addr = next_address.getAndIncrement();
        synchronized (this) {
            latchTable.put(addr, value);
        }
        return addr;
    }


    @Override
    public T2 lookup(int address) {
        synchronized (this) {
            return latchTable.get(address);
        }
    }

    @Override
    public void update(int address, T2 value) {
        synchronized (this) {
            latchTable.replace(address, value);
        }
    }

    @Override
    synchronized public int size() {
        return latchTable.size();
    }

    @Override
    synchronized public ITable<T2> deep_copy() {
        Map<Integer, T2> copy_table = new HashMap<>(latchTable);
        return new MyLatchTable<T2>(copy_table, next_address.get());
    }

    @Override
    synchronized public Set<Map.Entry<Integer, T2>> entrySet() {
        return latchTable.entrySet();
    }

    @Override
    synchronized public Map<Integer, T2> getContent() {
        return this.latchTable;
    }

    @Override
    public void setContent(Map<Integer, T2> content) {
        synchronized (this) {
            this.latchTable = content;
        }
    }

    @Override
    synchronized public Set<Integer> keys() {
        return this.latchTable.keySet();
    }

    @Override
    public String toString() {
        return latchTable.toString();
    }
}

