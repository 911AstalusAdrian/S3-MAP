package Model.States;

import Model.Exceptions.ADTException;

import java.util.HashMap;

public class MyLatch implements ILatch{

    private HashMap<Integer, Integer> latchTable;
    private int newLocation = 0;

    public MyLatch(){latchTable = new HashMap<>();}

    @Override
    public void put(int key, int value) throws ADTException{
        synchronized (this) {
            if (!latchTable.containsKey(key)) {
                latchTable.put(key, value);
            } else {
                throw new ADTException("Latch table already contains the key" + key);
            }
        }
    }

    @Override
    public int get(int key) throws ADTException{
        synchronized (this) {
            if (latchTable.containsKey(key)) {
                return latchTable.get(key);
            } else {
                throw new ADTException("Latch table doesn't contain the key" + key);
            }
        }
    }

    @Override
    public boolean containsKey(int key) {
        synchronized (this) {
            return latchTable.containsKey(key);
        }
    }

    @Override
    public int getFreeAddress() {
        synchronized (this) {
            newLocation++;
            return newLocation;
        }
    }

    @Override
    public void update(int key, int value) throws ADTException{
        synchronized (this) {
            if (latchTable.containsKey(key)) {
                latchTable.replace(key, value);
            } else {
                throw new ADTException("Latch table doesn't contain the key" + key);
            }
        }
    }

    @Override
    public void setFreeAddress(int freeAddress) {
        synchronized (this) {
            this.newLocation = freeAddress;
        }
    }

    @Override
    public HashMap<Integer, Integer> getLatchTable() {
        synchronized (this) {
            return latchTable;
        }
    }

    @Override
    public void setLatchTable(HashMap<Integer, Integer> newLatchTable) {
        synchronized (this) {
            this.latchTable = newLatchTable;
        }
    }

    @Override
    public String toString() {
        StringBuilder latchTableStringBuilder = new StringBuilder();
        for (int key : latchTable.keySet()) {
            latchTableStringBuilder.append(String.format("%d -> %d\n", key, latchTable.get(key)));
        }
        return latchTableStringBuilder.toString();
    }
}
