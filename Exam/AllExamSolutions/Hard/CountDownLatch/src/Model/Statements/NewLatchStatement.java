package Model.Statements;

import Exceptions.MyException;
import Model.ADT.MyIDictionary;
import Model.ADT.MyIHeap;
import Model.ADT.MyILatchTable;
import Model.Expressions.IExpression;
import Model.ProgramState;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.IntValue;
import Model.Values.Value;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NewLatchStatement implements IStatement {
    private final String varName;
    private final IExpression expression;
    private static final Lock lock = new ReentrantLock();

    public NewLatchStatement(String var, IExpression expression) {
        this.varName = var;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        lock.lock();
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIHeap heap = state.getHeap();
        MyILatchTable latchTable = state.getLatchTable();
        IntValue nr = (IntValue) (expression.eval(symTable, heap));
        int number = nr.getValue();
        int freeAddress = latchTable.getFreeAddress();
        latchTable.put(freeAddress, number);
        if (symTable.isDefined(varName)) {
            symTable.update(varName, new IntValue(freeAddress));
        } else {
            throw new MyException(String.format("%s is not defined in the symbol table!", varName));
        }
        lock.unlock();
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        if (typeEnv.getValue(varName).equals(new IntType())) {
            if (expression.typeCheck(typeEnv).equals(new IntType())) {
                return typeEnv;
            } else {
                throw new MyException("Expression doesn't have the int type!");
            }
        } else {
            throw new MyException(String.format("%s is not of int type!", varName));
        }
    }

    @Override
    public IStatement deepCopy() {
        return new NewLatchStatement(varName, expression.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("newLatch(%s, %s)", varName, expression);
    }
}
