package Model.Stmts;

import Model.Exceptions.ADTException;
import Model.Exceptions.EvalException;
import Model.Exceptions.ExecException;
import Model.Exceptions.TypecheckException;
import Model.Expressions.Exp;
import Model.PrgState;
import Model.States.IHeap;
import Model.States.ILatch;
import Model.States.MyIDictionary;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.IntValue;
import Model.Values.Value;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class newLatchStmt implements IStmt{

    String variable;
    Exp expression;
    Lock latchLock = new ReentrantLock();

    public newLatchStmt(String var, Exp e){variable = var; expression = e;}


    @Override
    public PrgState execute(PrgState state) throws ExecException {
        latchLock.lock();
        MyIDictionary<String, Value> symTbl = state.getTbl();
        IHeap myHeap = state.getHeap();
        ILatch latchTable = state.getLatchTable();
        try {
            IntValue number = (IntValue) (expression.eval(symTbl, myHeap));
            int newNumber = number.getVal();
            int freeAddr = latchTable.getFreeAddress();
            latchTable.put(freeAddr, newNumber);
            if(!symTbl.isDefined(variable))
                throw new ExecException("newLatch - var not defined");
            else symTbl.update(variable, new IntValue(freeAddr));
        }
        catch (EvalException | ADTException ee){throw new ExecException("newLatch - " + ee.toString());}
        latchLock.unlock();
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws TypecheckException {
        Type tv = typeEnv.get(variable);
        if(!tv.equals(new IntType()))
            throw new TypecheckException("newLatch typecheck 1");
        else{
            if(!expression.typecheck(typeEnv).equals(new IntType()))
                throw new TypecheckException("newLatch typecheck 2");
            else return typeEnv;
        }
    }

    @Override
    public String toString(){return "newLatch(" + variable + ", " + expression.toString() + ") ";};
}
