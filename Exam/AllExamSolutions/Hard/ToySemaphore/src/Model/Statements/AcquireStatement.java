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

public class AcquireStatement implements IStatement {
    private final String varName;
    private static final Lock lock = new ReentrantLock();

    public AcquireStatement(String var) {
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
                    int NL = foundSemaphore.getSecond().size();
                    int N1 = foundSemaphore.getFirst();
                    int N2 = foundSemaphore.getThird();
                    if ((N1 - N2) > NL) {
                        if (!foundSemaphore.getSecond().contains(state.getId())) {
                            foundSemaphore.getSecond().add(state.getId());
                            semaphoreTable.update(foundIndex, new Tuple<>(N1, foundSemaphore.getSecond(), N2));
                        }
                    } else {
                        state.getExecutionStack().push(this);
                    }
                } else {
                    throw new MyException("Index is not in the semaphore table!");
                }
            } else {
                throw new MyException("Index does not have the int type!");
            }
        } else
            throw new MyException("Index not in the symbol table!");
        lock.unlock();
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }

    @Override
    public IStatement deepCopy() {
        return new AcquireStatement(varName);
    }

    @Override
    public String toString() {
        return String.format("acquire(%s)", varName);
    }
}
