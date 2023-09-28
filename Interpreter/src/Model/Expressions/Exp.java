package Model.Expressions;

import Model.Exceptions.EvalException;
import Model.Exceptions.TypecheckException;
import Model.States.IHeap;
import Model.States.MyIDictionary;
import Model.Types.Type;
import Model.Values.Value;

public interface Exp {
    Value eval(MyIDictionary<String, Value> tbl, IHeap myHeap) throws EvalException;
    Type typecheck(MyIDictionary<String, Type> typeEnv) throws TypecheckException;
    String toString();
}
