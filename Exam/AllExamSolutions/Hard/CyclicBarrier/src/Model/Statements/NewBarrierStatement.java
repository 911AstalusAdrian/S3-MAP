package Model.Statements;

import Exceptions.MyException;
import Model.ADT.MyBarrierTable;
import Model.ADT.MyIBarrierTable;
import Model.ADT.MyIDictionary;
import Model.ADT.MyIHeap;
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

public class NewBarrierStatement implements IStatement {
    private final String varName;
    private final IExpression expression;
    private static final Lock lock = new ReentrantLock();

    public NewBarrierStatement(String varName, IExpression expression) {
        this.varName = varName;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        lock.lock();
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIHeap heap = state.getHeap();
        MyIBarrierTable barrierTable = state.getBarrierTable();
        IntValue number = (IntValue) (expression.eval(symTable, heap));
        int nr = number.getValue();
        int freeAddress = barrierTable.getFreeAddress();
        barrierTable.put(freeAddress, new Pair<>(nr, new ArrayList<>()));
        if (symTable.isDefined(varName))
            symTable.update(varName, new IntValue(freeAddress));
        else
            throw new MyException(String.format("%s is not defined in the symbol table!", varName));
        lock.unlock();
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new NewBarrierStatement(varName, expression.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        if (typeEnv.getValue(varName).equals(new IntType()))
            if (expression.typeCheck(typeEnv).equals(new IntType()))
                return typeEnv;
            else
                throw new MyException("Expression is not of type int!");
        else
            throw new MyException("Variable is not of type int!");
    }

    @Override
    public String toString() {
        return String.format("newBarrier(%s, %s)", varName, expression);
    }
}
