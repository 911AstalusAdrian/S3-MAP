package Model.ADT;

import Exceptions.MyException;
import Model.Statements.IStatement;
import javafx.util.Pair;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class MyProcedureTable implements MyIProcedureTable {
    private HashMap<String, Pair<List<String>, IStatement>> procTable;

    public MyProcedureTable() {
        this.procTable = new HashMap<>();
    }

    @Override
    public boolean isDefined(String key) {
        synchronized (this) {
            return this.procTable.containsKey(key);
        }
    }

    @Override
    public void put(String key, Pair<List<String>, IStatement> value) {
        synchronized (this) {
            this.procTable.put(key, value);
        }
    }

    @Override
    public Pair<List<String>, IStatement> getValue(String key) throws MyException {
        synchronized (this) {
            if (!isDefined(key))
                throw new MyException(key + " is not defined.");
            return this.procTable.get(key);
        }
    }

    @Override
    public void update(String key, Pair<List<String>, IStatement> value) throws MyException {
        synchronized (this) {
            if (!isDefined(key))
                throw new MyException(key + " is not defined.");
            this.procTable.put(key, value);
        }
    }

    @Override
    public Collection<Pair<List<String>, IStatement>> values() {
        synchronized (this) {
            return this.procTable.values();
        }
    }

    @Override
    public void remove(String key) throws MyException {
        synchronized (this) {
            if (!isDefined(key))
                throw new MyException(key + " is not defined.");
            this.procTable.remove(key);
        }
    }

    @Override
    public Set<String> keySet() {
        synchronized (this) {
            return procTable.keySet();
        }
    }

    @Override
    public HashMap<String, Pair<List<String>, IStatement>> getContent() {
        synchronized (this) {
            return procTable;
        }
    }

    @Override
    public MyIDictionary<String, Pair<List<String>, IStatement>> deepCopy() throws MyException {
        MyIDictionary<String, Pair<List<String>, IStatement>> toReturn = new MyDict<>();
        for (String key: keySet())
            try {
                toReturn.put(key, getValue(key));
            } catch (MyException e) {
                throw new MyException(e.getMessage());
            }
        return toReturn;
    }

    @Override
    public String toString() {
        return procTable.toString();
    }
}
