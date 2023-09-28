package Model.Statements;

import Exceptions.MyException;
import Model.ADT.MyIDictionary;
import Model.ADT.MyIHeap;
import Model.ADT.MyISemaphoreTable;
import Model.Expressions.IExpression;
import Model.ProgramState;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.IntValue;
import Model.Values.Value;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CreateSemaphoreStatement implements IStatement {
    private final String varName;
    private final IExpression expression;
    private static final Lock lock = new ReentrantLock();

    public CreateSemaphoreStatement(String var, IExpression expression) {
        this.varName = var;
        this.expression = expression;
    }
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        lock.lock();
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIHeap heap = state.getHeap();
        MyISemaphoreTable semaphoreTable = state.getSemaphoreTable();
        IntValue nr = (IntValue) (expression.eval(symTable, heap));
        int number = nr.getValue();
        int freeAddress = semaphoreTable.getFreeAddress();
        semaphoreTable.put(freeAddress, new Pair<>(number, new ArrayList<>()));
        if (symTable.isDefined(varName) && symTable.getValue(varName).getType().equals(new IntType()))
            symTable.update(varName, new IntValue(freeAddress));
        lock.unlock();
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        if (typeEnv.getValue(varName).equals(new IntType())) {
            if (expression.typeCheck(typeEnv).equals(new IntType()))
                return typeEnv;
            else
                throw new MyException("Expression is not of int type!");
        } else {
            throw new MyException(String.format("%s is not of type int!", varName));
        }
    }

    @Override
    public IStatement deepCopy() {
        return new CreateSemaphoreStatement(varName, expression.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("createSemaphore(%s, %s)", varName, expression);
    }
}
