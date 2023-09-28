package Model.Expressions;
import Model.Exceptions.EvalException;
import Model.Exceptions.TypecheckException;
import Model.States.IHeap;
import Model.States.MyIDictionary;
import Model.Types.Type;
import Model.Values.Value;

public class VarExp implements Exp{
    String id;

    public VarExp(String v){id = v;}

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws TypecheckException {
        return typeEnv.get(id);
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, IHeap myHeap) throws EvalException {
        if(!tbl.isDefined(id))
            throw new EvalException("VarExp - Undefined variable with name " + id);
        else return tbl.get(id);
    }

    public String toString(){return id;}
}
