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

public class LockStatement implements IStatement {
    private final String varName;
    private static final Lock lock = new ReentrantLock();

    public LockStatement(String varName) {
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
                    if (lockTable.get(foundIndex) == -1) {;
                        state.getLockTable().update(foundIndex, state.getId());
                    } else {
                        state.getExecutionStack().push(this);
                    }
                } else {
                    throw new MyException("Index is not in the lock table!");
                }
            } else {
                throw new MyException("Var is not of type int!");
            }
        } else {
            throw new MyException("Variable not defined!");
        }
        lock.unlock();
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new LockStatement(varName);
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        if (typeEnv.getValue(varName).equals(new IntType())) {
            return typeEnv;
        } else {
            throw new MyException("Var is not of int type!");
        }
    }

    @Override
    public String toString() {
        return String.format("lock(%s)", varName);
    }
}
