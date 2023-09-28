package Model.Stmts;

import Model.Exceptions.EvalException;
import Model.Exceptions.ExecException;
import Model.Exceptions.TypecheckException;
import Model.Expressions.Exp;
import Model.PrgState;
import Model.States.MyIDictionary;
import Model.States.MyIStack;
import Model.Types.BoolType;
import Model.Types.Type;
import Model.Values.BoolValue;
import Model.Values.Value;

public class IfStmt implements IStmt{
    Exp exp;
    IStmt thenS, elseS;
    public IfStmt(Exp condition, IStmt _then, IStmt _else){exp=condition;thenS=_then;elseS=_else;}

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws TypecheckException {
        Type typexp = exp.typecheck(typeEnv);
        if (typexp.equals(new BoolType())){
            thenS.typecheck(typeEnv.clone());
            elseS.typecheck(typeEnv.clone());
            return typeEnv;
        }
        else throw new TypecheckException("If - The condition is not boolean!");
    }

    @Override
    public PrgState execute(PrgState state) throws ExecException {
        MyIStack<IStmt> stk = state.getStk();
        MyIDictionary<String, Value> tbl = state.getTbl();
        try {
            Value condValue = exp.eval(tbl, state.getHeap());
            if (!condValue.getType().equals(new BoolType())) {
                throw new ExecException("IF - Invalid Condition!");
            } else {
                BoolValue val = (BoolValue) condValue;
                if (val.getVal())
                    stk.push(thenS);
                else
                    stk.push(elseS);
            }
            return null;
        }
        catch (EvalException ee){throw new ExecException("IF eval - " + ee.toString());}
    }

    public String toString(){
        return "(IF(" + exp.toString() + ") THEN(" + thenS.toString() + ")ELSE(" + elseS.toString() + "))";
    }
}
