package Model.Expressions;

import Model.Exceptions.EvalException;
import Model.Exceptions.TypecheckException;
import Model.States.IHeap;
import Model.States.MyIDictionary;
import Model.Types.Type;
import Model.Values.Value;

public class ValueExp implements Exp{
    Value e;

    public ValueExp(Value val){e=val;}

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws TypecheckException {
        return e.getType();
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, IHeap myHeap) throws EvalException {return e;}

    public String toString(){return e.toString();}
}
