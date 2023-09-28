package Model.Statements;

import Exceptions.MyException;
import Model.ADT.MyIDictionary;
import Model.ADT.MyIToySemaphoreTable;
import Model.ADT.Tuple;
import Model.ProgramState;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.IntValue;
import Model.Values.Value;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReleaseStatement implements IStatement {
    private final String varName;
    private static final Lock lock = new ReentrantLock();

    public ReleaseStatement(String var) {
        this.varName = var;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        lock.lock();
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIToySemaphoreTable semaphoreTable = state.getToySemaphoreTable();
        if (symTable.isDefined(varName)) {
            if (symTable.getValue(varName).getType().equals(new IntType())) {
                IntValue fi = (IntValue) symTable.getValue(varName);
                int foundIndex = fi.getValue();
                if (semaphoreTable.containsKey(foundIndex)) {
                    Tuple<Integer, List<Integer>, Integer> foundSemaphore = semaphoreTable.get(foundIndex);
                    if (foundSemaphore.getSecond().contains(state.getId())) {
                        foundSemaphore.getSecond().remove((Integer) state.getId());
                    }
                    semaphoreTable.update(foundIndex, new Tuple<>(foundSemaphore.getFirst(), foundSemaphore.getSecond(), foundSemaphore.getThird()));
                } else {
                    throw new MyException("Index not found in the semaphore table!");
                }
            } else {
                throw new MyException("Index must be of int type!");
            }
        } else {
            throw new MyException("Index not found in the symbol table!");
        }
        lock.unlock();
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }

    @Override
    public IStatement deepCopy() {
        return new ReleaseStatement(varName);
    }

    @Override
    public String toString() {
        return String.format("release(%s)", varName);
    }
}
