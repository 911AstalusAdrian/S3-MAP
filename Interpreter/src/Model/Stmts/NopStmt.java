package Model.Stmts;

import Model.Exceptions.EvalException;
import Model.Exceptions.ExecException;
import Model.Exceptions.TypecheckException;
import Model.PrgState;
import Model.States.MyIDictionary;
import Model.Types.Type;

public class NopStmt implements IStmt{
    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws TypecheckException {
        return typeEnv;
    }

    @Override
    public PrgState execute(PrgState state) throws ExecException {
        return null;
    }
}
