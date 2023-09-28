package model.dataStructures;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class MyLockTable<T2> implements ITable<T2>{
    private Map<Integer, T2> lockTable = new HashMap<Integer, T2>();
    private AtomicInteger next_address = new AtomicInteger(1);

    public MyLockTable() {
    }

    private MyLockTable(Map<Integer, T2> copy_table, int next_address) {
        this.lockTable = copy_table;
        this.next_address.set(next_address);
    }

    @Override
    public boolean isDefined(int address) {
        synchronized (this) {
            return lockTable.containsKey(address);
        }
    }

    @Override
    public int allocate(T2 value) {
        int addr = next_address.getAndIncrement();
        synchronized (this) {
            lockTable.put(addr, value);
        }
        return addr;
    }


    @Override
    public T2 lookup(int address) {
        synchronized (this) {
            return lockTable.get(address);
        }
    }

    @Override
    public void update(int address, T2 value) {
        synchronized (this) {
            lockTable.replace(address, value);
        }
    }

    @Override
    synchronized public int size() {
        return lockTable.size();
    }

    @Override
    synchronized public ITable<T2> deep_copy() {
        Map<Integer, T2> copy_table = new HashMap<>(lockTable);
        return new MyLockTable<T2>(copy_table, next_address.get());
    }

    @Override
    synchronized public Set<Map.Entry<Integer, T2>> entrySet() {
        return lockTable.entrySet();
    }

    @Override
    synchronized public Map<Integer, T2> getContent() {
        return this.lockTable;
    }

    @Override
    public void setContent(Map<Integer, T2> content) {
        synchronized (this) {
            this.lockTable = content;
        }
    }

    @Override
    synchronized public Set<Integer> keys() {
        return this.lockTable.keySet();
    }

    @Override
    public String toString() {
        return lockTable.toString();
    }
}

