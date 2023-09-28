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

public class NewLockStatement implements IStatement {
    private final String varName;
    private static final Lock lock = new ReentrantLock();

    public NewLockStatement(String varName) {
        this.varName = varName;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        lock.lock();
        MyILockTable lockTable = state.getLockTable();
        MyIDictionary<String, Value> symTable = state.getSymTable();
        int freeAddress = lockTable.getFreeValue();
        lockTable.put(freeAddress, -1);
        if (symTable.isDefined(varName) && symTable.getValue(varName).getType().equals(new IntType())) {
            symTable.update(varName, new IntValue(freeAddress));
        }
        else {
            throw new MyException("Variable not declared!");
        }
        lock.unlock();
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new NewLockStatement(varName);
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        if (typeEnv.getValue(varName).equals(new IntType()))
            return typeEnv;
        else
            throw new MyException("Var is not of int type!");
    }

    @Override
    public String toString() {
        return String.format("newLock(%s)", varName);
    }
}
