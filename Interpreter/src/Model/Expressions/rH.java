package Model.Expressions;

import Model.Exceptions.ADTException;
import Model.Exceptions.EvalException;
import Model.Exceptions.TypecheckException;
import Model.States.IHeap;
import Model.States.MyIDictionary;
import Model.Types.RefType;
import Model.Types.Type;
import Model.Values.RefValue;
import Model.Values.Value;

public class rH implements Exp{

    Exp expression;

    public rH(Exp e){expression = e;}

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws TypecheckException {
        Type t = expression.typecheck(typeEnv);
        if(t instanceof RefType){
            RefType rt = (RefType) t;
            return rt.getInner();
        }
        else throw new TypecheckException("rH - Argument is not a Ref Type!");
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, IHeap myHeap) throws EvalException {

        try {
            Value val = expression.eval(tbl, myHeap);
            if (!(val instanceof RefValue))
                throw new EvalException("rH - Expression not of RefType.");
            RefValue refVal = (RefValue) val;
            return myHeap.getValue(refVal.getAddr());
        }
        catch (ADTException ex){
            throw new EvalException("rH - " + ex.toString());
        }
    }

    @Override
    public String toString(){
        return "rH(" + expression.toString() + ")";
    }
}
