package model.dataStructures;

import javafx.util.Pair;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class MyBarrierTable implements IBarrierTable{
    private Map<Integer, Pair<Integer, List<Integer>>> barrierTable = new HashMap<Integer, Pair<Integer, List<Integer>>>();
    private AtomicInteger next_address = new AtomicInteger(1);

    public MyBarrierTable() {
    }

    private MyBarrierTable(Map<Integer, Pair<Integer, List<Integer>>> copy_table, int next_address) {
        this.barrierTable = copy_table;
        this.next_address.set(next_address);
    }

    @Override
    public boolean isDefined(int address) {
        synchronized (this) {
            return barrierTable.containsKey(address);
        }
    }

    @Override
    public int allocate(Integer value) {
        int addr = next_address.getAndIncrement();
        Pair<Integer, List<Integer>> newEntry = new Pair<>(value, new ArrayList<>());
        synchronized (this) {
            barrierTable.put(addr, newEntry);
        }
        return addr;
    }


    @Override
    public Pair<Integer, List<Integer>> lookup(int address) {
        synchronized (this) {
            return barrierTable.get(address);
        }
    }

    @Override
    public void update(int address, Integer value) {
        Pair<Integer, List<Integer>> newEntry = new Pair<>(value, new ArrayList<>());
        synchronized (this) {
            barrierTable.replace(address, newEntry);
        }
    }

    @Override
    synchronized public int size() {
        return barrierTable.size();
    }

    @Override
    synchronized public IBarrierTable deep_copy() {
        Map<Integer, Pair<Integer, List<Integer>>> copy_table = new HashMap<>(barrierTable);
        return new MyBarrierTable(copy_table, next_address.get());
    }

    @Override
    synchronized public Set<Map.Entry<Integer, Pair<Integer, List<Integer>>>> entrySet() {
        return barrierTable.entrySet();
    }

    @Override
    synchronized public Map<Integer, Pair<Integer, List<Integer>>> getContent() {
        return this.barrierTable;
    }

    @Override
    public void setContent(Map<Integer, Pair<Integer, List<Integer>>> content) {
        synchronized (this) {
            this.barrierTable = content;
        }
    }

    @Override
    synchronized public Set<Integer> keys() {
        return this.barrierTable.keySet();
    }

    @Override
    public String toString() {
        return barrierTable.toString();
    }

}
