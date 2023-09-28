package Model.Stmts;

import Model.Exceptions.ExecException;
import Model.Exceptions.TypecheckException;
import Model.PrgState;
import Model.States.MyIDictionary;
import Model.States.MyIStack;
import Model.States.MyStack;
import Model.Types.Type;

public class Fork implements IStmt {

    IStmt statement;

    public Fork(IStmt st){statement = st;}


    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws TypecheckException {
        statement.typecheck(typeEnv.clone());
        return typeEnv;
    }

    @Override
    public PrgState execute(PrgState state) throws ExecException {
        MyIStack<IStmt> newStack = new MyStack<>();
        return new PrgState(newStack,state.getTbl().clone(), state.getOut(), state.getHeap(), statement, state.getLatchTable());
    }

    @Override
    public String toString(){
        return "Fork(" + statement.toString() + ")";
    }
}
