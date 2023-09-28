package Model.Statements;

import Exceptions.MyException;
import Model.ADT.MyIDictionary;
import Model.ADT.MyILatchTable;
import Model.Expressions.ValueExpression;
import Model.ProgramState;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.IntValue;
import Model.Values.Value;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CountDownStatement implements IStatement {
    private final String varName;
    private static final Lock lock = new ReentrantLock();

    public CountDownStatement(String var) {
        this.varName = var;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        lock.lock();
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyILatchTable latchTable = state.getLatchTable();
        if (symTable.isDefined(varName)) {
            IntValue fi = (IntValue) symTable.getValue(varName);
            int foundIndex = fi.getValue();
            if (latchTable.containsKey(foundIndex)) {
                if (latchTable.get(foundIndex) > 0) {
                    latchTable.update(foundIndex, latchTable.get(foundIndex) - 1);
                }
                state.getExecutionStack().push(new PrintStatement(new ValueExpression(new IntValue(state.getId()))));
            } else {
                throw new MyException("Index not found in the latch table!");
            }
        } else {
            throw new MyException("Variable not defined!");
        }
        lock.unlock();
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        if (typeEnv.getValue(varName).equals(new IntType()))
            return typeEnv;
        else
            throw new MyException(String.format("%s is not of int type!", varName));
    }

    @Override
    public IStatement deepCopy() {
        return new CountDownStatement(varName);
    }

    @Override
    public String toString() {
        return String.format("countDown(%s)", varName);
    }
}
