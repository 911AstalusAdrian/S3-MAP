package Model.Statements;

import Exceptions.MyException;
import Model.ADT.MyIDictionary;
import Model.ProgramState;
import Model.Types.Type;

public class NopStatement implements IStatement {
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new NopStatement();
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }

    @Override
    public String toString() {
        return "NopStatement";
    }
}
