package Model.Stmts;

import Model.Exceptions.ExecException;
import Model.Exceptions.TypecheckException;
import Model.Expressions.Exp;
import Model.PrgState;
import Model.States.MyIDictionary;
import Model.Types.BoolType;
import Model.Types.Type;

public class CondAssignStmt implements IStmt{

    Exp ex1, ex2, ex3;
    String value;

    public CondAssignStmt(String val, Exp e1, Exp e2, Exp e3){value = val; ex1 = e1; ex2 = e2; ex3 = e3;};

    @Override
    public PrgState execute(PrgState state) throws ExecException {
        IStmt newStmt = new IfStmt(ex1, new AssignStmt(value, ex2), new AssignStmt(value, ex3));
        state.getStk().push(newStmt);
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws TypecheckException {
        // exp1 has type bool, also that v, exp2 and exp3 have the same type
        Type t1 = ex1.typecheck(typeEnv);
        if(!t1.equals(new BoolType()))
            throw new TypecheckException("CondAssign - exp1 not of type bool!");

        Type tv = typeEnv.get(value);
        Type t2 = ex2.typecheck(typeEnv);
        Type t3 = ex3.typecheck(typeEnv);

        if(tv.equals(t2) && tv.equals(t3)) return typeEnv;
        else throw new TypecheckException("CondAssign - value, exp2 and exp3 do not have the same type");
//        return null;
    }

    @Override
    public String toString(){
        return value + "=" + ex1.toString() + "?" + ex2.toString() + ":" + ex3.toString();
    }

}
