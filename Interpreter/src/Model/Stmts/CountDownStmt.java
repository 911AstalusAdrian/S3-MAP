package Model.Stmts;

import Model.Exceptions.ADTException;
import Model.Exceptions.ExecException;
import Model.Exceptions.TypecheckException;
import Model.Expressions.ValueExp;
import Model.PrgState;
import Model.States.ILatch;
import Model.States.MyIDictionary;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.IntValue;
import Model.Values.Value;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CountDownStmt implements IStmt{

    String variable;
    Lock countdownLock = new ReentrantLock();

    public CountDownStmt(String var){variable = var;}

    @Override
    public PrgState execute(PrgState state) throws ExecException {
        countdownLock.lock();
        MyIDictionary<String, Value> symTable = state.getTbl();
        ILatch latchTable = state.getLatchTable();
        if(!symTable.isDefined(variable))
            throw new ExecException("CountDown - variable not defined: " + variable);
        else{
            IntValue val = (IntValue) symTable.get(variable);
            int index = val.getVal();
            if(!latchTable.containsKey(index))
                throw new ExecException("CountDown - index not found in the latch table.");
            else{
                try{
                    if(latchTable.get(index) > 0)
                        latchTable.update(index, latchTable.get(index)-1);
                    state.getStk().push(new PrintStmt(new ValueExp(new IntValue(state.getID()))));
                }
                catch (ADTException ae){throw new ExecException("CountDown catch - " + ae.toString());}
            }
        }
        countdownLock.unlock();
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws TypecheckException {
        Type tv = typeEnv.get(variable);
        if(!tv.equals(new IntType()))
            throw new TypecheckException("CountDown typecheck - variable not int.");
        else return typeEnv;
    }

    @Override
    public String toString(){return "countDown(" + variable + ")";}
}
