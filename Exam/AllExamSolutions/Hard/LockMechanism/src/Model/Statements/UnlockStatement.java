package Model.Statements;

import Exceptions.MyException;
import Model.ADT.MyIDictionary;
import Model.ADT.MyILockTable;
import Model.ProgramState;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.IntValue;
import Model.Values.Value;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class UnlockStatement implements IStatement {
    private final String varName;
    private static final Lock lock = new ReentrantLock();

    public UnlockStatement(String varName) {
        this.varName = varName;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        lock.lock();
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyILockTable lockTable = state.getLockTable();
        if (symTable.isDefined(varName)) {
            if (symTable.getValue(varName).getType().equals(new IntType())) {
                IntValue fi = (IntValue) symTable.getValue(varName);
                int foundIndex = fi.getValue();
                if (lockTable.containsKey(foundIndex)) {
                    if (lockTable.get(foundIndex) == state.getId())
                        state.getLockTable().update(foundIndex, -1);
                } else {
                    throw new MyException("Index not in the lock table!");
                }
            } else {
                throw new MyException("Var is not of int type!");
            }
        } else {
            throw new MyException("Variable is not defined!");
        }
        lock.unlock();
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new UnlockStatement(varName);
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        if (typeEnv.getValue(varName).equals(new IntType()))
            return typeEnv;
        else
            throw new MyException("Var is not of type int!");
    }

    @Override
    public String toString() {
        return String.format("unlock(%s)", varName);
    }
}
