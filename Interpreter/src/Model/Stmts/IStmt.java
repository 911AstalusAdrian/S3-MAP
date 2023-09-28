package Model.Stmts;
import Model.Exceptions.ExecException;
import Model.Exceptions.TypecheckException;
import Model.PrgState;
import Model.States.MyIDictionary;
import Model.Types.Type;

public interface IStmt {
    PrgState execute(PrgState state) throws ExecException;
    MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws TypecheckException;
}
