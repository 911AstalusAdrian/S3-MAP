package Model.Stmts;
import Model.Exceptions.ExecException;
import Model.Exceptions.TypecheckException;
import Model.States.MyIDictionary;
import Model.States.MyIStack;
import Model.PrgState;
import Model.Types.Type;

public class CompStmt implements IStmt{
    IStmt first;
    IStmt second;

    public CompStmt(IStmt f, IStmt s){this.first = f;this.second = s;}

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws TypecheckException {
        return second.typecheck(first.typecheck(typeEnv));
    }

    @Override
    public PrgState execute(PrgState state) throws ExecException {
        MyIStack<IStmt> stk = state.getStk();
        stk.push(second);
        stk.push(first);
        return null;
    }

    public String toString(){
        return  "" + first.toString() + ";" + second.toString() + "";
    }
}
