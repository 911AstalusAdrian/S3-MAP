package Model.Stmts;

import Model.Exceptions.ADTException;
import Model.Exceptions.EvalException;
import Model.Exceptions.ExecException;
import Model.Exceptions.TypecheckException;
import Model.Expressions.Exp;
import Model.PrgState;
import Model.States.IHeap;
import Model.States.MyIDictionary;
import Model.Types.RefType;
import Model.Types.Type;
import Model.Values.RefValue;
import Model.Values.Value;

public class wH implements IStmt{

    String varName;
    Exp expression;

    public wH(String varName, Exp e){this.varName = varName; expression = e;}

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws TypecheckException {
        Type t1 = typeEnv.get(varName);
        Type t2 = expression.typecheck(typeEnv);
        if(t1.equals(new RefType(t2)))
            return typeEnv;
        else throw new TypecheckException("wH - left hand side and right hand side do not match.");
    }

    @Override
    public PrgState execute(PrgState state) throws ExecException {
        MyIDictionary<String, Value> symTbl = state.getTbl();
        IHeap heap = state.getHeap();

        if(!symTbl.isDefined(varName))
            throw new ExecException("wH - Undefined variable.");

        Value varValue = symTbl.get(varName);
        if(!(varValue instanceof RefValue))
            throw new ExecException("wH - " + varName + " not of type RefValue.");

        RefValue refVarValue = (RefValue) varValue;
        try {
            Value eval = expression.eval(symTbl, heap);

            if (!eval.getType().equals(refVarValue.getLocationType()))
                throw new ExecException("wH - No matching values.");

            heap.update(refVarValue.getAddr(), eval);


            return null;
        }
        catch (EvalException ee){throw new ExecException("wH exec (eval) - " + ee.toString());}
        catch (ADTException ae){throw new ExecException("wH exec (ADT) - " + ae.toString());}
    }

    @Override
    public String toString(){
        return "wh(" + varName + "," + expression.toString() + ")";
    }
}
