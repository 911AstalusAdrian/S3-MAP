package Model.Statements;

import Exceptions.MyException;
import Model.ADT.MyIDictionary;
import Model.ProgramState;
import Model.Types.Type;

public class ReturnStatement implements IStatement {
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        state.getSymTable().pop();
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }

    @Override
    public IStatement deepCopy() {
        return new ReturnStatement();
    }

    @Override
    public String toString() {
        return "return";
    }
}
