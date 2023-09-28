package model.dataStructures;

import javafx.util.Pair;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class MyCountSemaphore implements ISemaphore{
    private Map<Integer, Pair<Integer, List<Integer>>> semaphoreTable = new HashMap<Integer, Pair<Integer, List<Integer>>>();
    private AtomicInteger next_address = new AtomicInteger(1);

    public MyCountSemaphore() {
    }

    private MyCountSemaphore(Map<Integer, Pair<Integer, List<Integer>>> copy_table, int next_address) {
        this.semaphoreTable = copy_table;
        this.next_address.set(next_address);
    }

    @Override
    public boolean isDefined(int address) {
        synchronized (this) {
            return semaphoreTable.containsKey(address);
        }
    }

    @Override
    public int allocate(Integer value) {
        int addr = next_address.getAndIncrement();
        Pair<Integer, List<Integer>> newEntry = new Pair<>(value, new ArrayList<>());
        synchronized (this) {
            semaphoreTable.put(addr, newEntry);
        }
        return addr;
    }


    @Override
    public Pair<Integer, List<Integer>> lookup(int address) {
        synchronized (this) {
            return semaphoreTable.get(address);
        }
    }

    @Override
    public void update(int address, Integer value) {
        Pair<Integer, List<Integer>> newEntry = new Pair<>(value, new ArrayList<>());
        synchronized (this) {
            semaphoreTable.replace(address, newEntry);
        }
    }

    @Override
    synchronized public int size() {
        return semaphoreTable.size();
    }

    @Override
    synchronized public ISemaphore deep_copy() {
        Map<Integer, Pair<Integer, List<Integer>>> copy_table = new HashMap<>(semaphoreTable);
        return new MyCountSemaphore(copy_table, next_address.get());
    }

    @Override
    synchronized public Set<Map.Entry<Integer, Pair<Integer, List<Integer>>>> entrySet() {
        return semaphoreTable.entrySet();
    }

    @Override
    synchronized public Map<Integer, Pair<Integer, List<Integer>>> getContent() {
        return this.semaphoreTable;
    }

    @Override
    public void setContent(Map<Integer, Pair<Integer, List<Integer>>> content) {
        synchronized (this) {
            this.semaphoreTable = content;
        }
    }

    @Override
    synchronized public Set<Integer> keys() {
        return this.semaphoreTable.keySet();
    }

    @Override
    public String toString() {
        return semaphoreTable.toString();
    }

}
