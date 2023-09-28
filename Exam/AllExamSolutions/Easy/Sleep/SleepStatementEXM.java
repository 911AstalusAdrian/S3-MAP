package Model.Statements;

import Exceptions.MyException;
import Model.ADT.MyIDictionary;
import Model.ProgramState;
import Model.Types.Type;

public class SleepStatementEXM implements IStatement {
    private final Integer numberSeconds;

    public SleepStatementEXM(Integer numberSeconds) {
         this.numberSeconds = numberSeconds;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        if (numberSeconds != 0)
            state.getExecutionStack().push(new SleepStatementEXM(numberSeconds - 1));
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }

    @Override
    public String toString() {
        return "sleep(" + numberSeconds.toString() + ")";
    }
}
