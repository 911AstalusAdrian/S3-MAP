package Model.ADT;

import Exceptions.MyException;
import Model.Statements.IStatement;
import javafx.util.Pair;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public interface MyIProcedureTable {
    boolean isDefined(String key);

    void put(String key, Pair<List<String>, IStatement> value);

    Pair<List<String>, IStatement> getValue(String key) throws MyException;

    void update(String key, Pair<List<String>, IStatement> value) throws MyException;

    Collection<Pair<List<String>, IStatement>> values();

    void remove(String key) throws MyException;

    Set<String> keySet();

    HashMap<String, Pair<List<String>, IStatement>> getContent();

    MyIDictionary<String, Pair<List<String>, IStatement>> deepCopy() throws MyException;
}
