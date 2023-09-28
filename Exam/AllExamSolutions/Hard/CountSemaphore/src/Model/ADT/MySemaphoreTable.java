package Model.ADT;

import Exceptions.MyException;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.List;

public class MySemaphoreTable implements MyISemaphoreTable {
    private HashMap<Integer, Pair<Integer, List<Integer>>> semaphoreTable;
    private int freeLocation = 0;

    public MySemaphoreTable() {
        semaphoreTable = new HashMap<>();
    }

    @Override
    public void put(int key, Pair<Integer, List<Integer>> value) throws MyException {
        synchronized (this) {
            if (!semaphoreTable.containsKey(key)) {
                semaphoreTable.put(key, value);
            } else {
                throw new MyException(String.format("Barrier table already contains the key %d!", key));
            }
        }
    }

    @Override
    public Pair<Integer, List<Integer>> get(int key) throws MyException {
        synchronized (this) {
            if (semaphoreTable.containsKey(key)) {
                return semaphoreTable.get(key);
            } else {
                throw new MyException(String.format("Barrier table doesn't contain the key %d!", key));
            }
        }
    }

    @Override
    public boolean containsKey(int key) {
        synchronized (this) {
            return semaphoreTable.containsKey(key);
        }
    }

    @Override
    public int getFreeAddress() {
        synchronized (this) {
            freeLocation++;
            return freeLocation;
        }
    }

    @Override
    public void setFreeAddress(int freeAddress) {
        synchronized (this) {
            this.freeLocation = freeAddress;
        }
    }

    @Override
    public void update(int key, Pair<Integer, List<Integer>> value) throws MyException {
        synchronized (this) {
            if (semaphoreTable.containsKey(key)) {
                semaphoreTable.replace(key, value);
            } else {
                throw new MyException(String.format("Barrier table doesn't contain key %d!", key));
            }
        }
    }

    @Override
    public HashMap<Integer, Pair<Integer, List<Integer>>> getSemaphoreTable() {
        synchronized (this) {
            return semaphoreTable;
        }
    }

    public void setNewSemaphoreTable(HashMap<Integer, Pair<Integer, List<Integer>>> semaphoreTable) {
        synchronized (this) {
            this.semaphoreTable = semaphoreTable;
        }
    }

    @Override
    public String toString() {
        return this.semaphoreTable.toString();
    }
}
