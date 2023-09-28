package Model.Stmts;
import Model.Exceptions.EvalException;
import Model.Exceptions.ExecException;
import Model.Exceptions.TypecheckException;
import Model.Expressions.Exp;
import Model.PrgState;
import Model.States.MyIDictionary;
import Model.States.MyIList;
import Model.Types.Type;
import Model.Values.Value;

public class PrintStmt implements IStmt{
    Exp exp;

    public PrintStmt(Exp e){exp=e;}

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws TypecheckException {
        exp.typecheck(typeEnv);
        return typeEnv;
    }

    @Override
    public PrgState execute(PrgState state) throws ExecException {
        MyIList<Value> out = state.getOut();
        try {
            out.add(exp.eval(state.getTbl(), state.getHeap()));
            return null;
        }
        catch (EvalException ee){throw new ExecException("Print exec - " + ee.toString());}
    }

    public String toString(){return "print(" + exp.toString() + ")";}
}
