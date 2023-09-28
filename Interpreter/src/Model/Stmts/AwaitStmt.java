package Model.Stmts;

import Model.Exceptions.ADTException;
import Model.Exceptions.ExecException;
import Model.Exceptions.TypecheckException;
import Model.PrgState;
import Model.States.ILatch;
import Model.States.MyIDictionary;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.IntValue;
import Model.Values.Value;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AwaitStmt implements IStmt{

    String variable;
    Lock awaitLock = new ReentrantLock();

    public AwaitStmt(String var){variable = var;}

    @Override
    public PrgState execute(PrgState state) throws ExecException {
        awaitLock.lock();
        MyIDictionary<String, Value> symTable = state.getTbl();
        ILatch latchTable = state.getLatchTable();
        if (!symTable.isDefined(variable))
            throw new ExecException("Await - Variable " + variable + "is not defined!");
        else {
            IntValue val = (IntValue) symTable.get(variable);
            int index = val.getVal();
            if (!latchTable.containsKey(index))
                throw new ExecException("Await - Index was not found in the latch table.");
            else {
                try {
                    if (latchTable.get(index) != 0)
                        state.getStk().push(this);
                } catch (ADTException ae) {
                    throw new ExecException("Await catch - " + ae.toString());
                }
            }
        }
        awaitLock.unlock();
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws TypecheckException {
        Type tv = typeEnv.get(variable);
        if(!tv.equals(new IntType()))
            throw new TypecheckException("Await typecheck - " + variable + " not of type int");
        else return typeEnv;
    }

    @Override
    public String toString(){return "await(" + variable + "); ";}
}
