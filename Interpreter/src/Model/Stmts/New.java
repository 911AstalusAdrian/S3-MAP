package Model.Stmts;

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

public class New implements IStmt{

    String varName;
    Exp expression;

    public New(String varName, Exp e){this.varName = varName; this.expression = e;}

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws TypecheckException {
        Type typevar = typeEnv.get(varName);
        Type typexp = expression.typecheck(typeEnv);
        if (typevar.equals(new RefType(typexp)))
            return typeEnv;
        else throw new TypecheckException("NEW - right hand side and left hand side have different types.");
    }

    @Override
    public PrgState execute(PrgState state) throws ExecException {
        MyIDictionary<String, Value> symTbl = state.getTbl();
        IHeap heap = state.getHeap();

        if(!symTbl.isDefined(varName))
            throw new ExecException("New - Non existent variable.");

        Value val = symTbl.get(varName);
        if(!(val.getType() instanceof RefType))
            throw new ExecException("New - Variable not of RefType.");

        try {
            Value expVal = expression.eval(symTbl, state.getHeap());
            Type locType = ((RefValue) val).getLocationType();
            if (!locType.equals(expVal.getType()))
                throw new ExecException("New - " + varName + " of incorrect type.");

            Integer newPos = heap.add(expVal);
            symTbl.update(varName, new RefValue(newPos, locType));

            return null;
        }
        catch (EvalException ee){throw new ExecException("New exec - " + ee.toString());}
    }

    @Override
    public String toString(){
        return "New(" + varName + "," + expression.toString() + ")";
    }
}
