package Model.Stmts;

import Model.Exceptions.EvalException;
import Model.Exceptions.ExecException;
import Model.Exceptions.TypecheckException;
import Model.Expressions.Exp;
import Model.PrgState;
import Model.States.MyIDictionary;
import Model.Types.BoolType;
import Model.Types.Type;
import Model.Values.BoolValue;
import Model.Values.Value;

public class WhileStmt implements IStmt{

    Exp expression;
    IStmt statement;

    public WhileStmt(Exp e, IStmt s){expression = e; statement = s;}

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws TypecheckException {
        Type t = expression.typecheck(typeEnv);
        if (t.equals(new BoolType())){
            statement.typecheck(typeEnv.clone());
            return typeEnv;
        }
        else throw new TypecheckException("while - The condition is not a boolean.");
    }

    @Override
    public PrgState execute(PrgState state) throws ExecException {
        try {
            Value val = expression.eval(state.getTbl(), state.getHeap());
            if (!val.getType().equals(new BoolType()))
                throw new ExecException("while - Expression doesn't evaluate to bool");

            BoolValue bVal = (BoolValue) val;
            if (bVal.getVal()) {
                state.getStk().push(this);
                state.getStk().push(statement);
            }
            return null;
        }
        catch (EvalException ee){throw new ExecException("while exec - " + ee.toString());}
    }

    @Override
    public String toString(){
        return "(while " + expression.toString() + " " + statement.toString()+")";
    }
}
