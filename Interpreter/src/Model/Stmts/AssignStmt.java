package Model.Stmts;

import Model.Exceptions.*;
import Model.Expressions.Exp;
import Model.PrgState;
import Model.States.MyIDictionary;
import Model.States.MyIStack;
import Model.Types.Type;
import Model.Values.Value;

public class AssignStmt implements IStmt{
    String id;
    Exp exp;

    public AssignStmt(String i, Exp e){id=i;exp=e;}

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws TypecheckException {
        Type typevar = typeEnv.get(id);
        Type typexp = exp.typecheck(typeEnv);
        if (typevar.equals(typexp))
            return typeEnv;
        else throw new TypecheckException("Assign - right hand side and left hand side have different types.");
    }

    @Override
    public PrgState execute(PrgState state) throws ExecException {
        MyIStack<IStmt> stk = state.getStk();
        MyIDictionary<String, Value> tbl = state.getTbl();
        try {
            if (tbl.isDefined(id)) {
                Value val = exp.eval(tbl, state.getHeap());
                Type type = (tbl.get(id)).getType();
                if (val.getType().equals(type))
                    tbl.update(id, val);
                else
                    throw new ExecException("Assign - declared type of variable" + id + "and type of the assigned expression do not match");
            } else throw new ExecException("Assign - the used variable " + id + " was not declared before.");
            return null;
        }
        catch (EvalException ee){
            throw new ExecException("Assign exec - " + ee.toString());
        }
    }

    public String toString(){return id + " = " + exp.toString();}
}
