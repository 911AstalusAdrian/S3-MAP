package Model.Statements;

import Exceptions.MyException;
import Model.ADT.MyIDictionary;
import Model.Expressions.ValueExpression;
import Model.ProgramState;
import Model.Types.Type;
import Model.Values.IntValue;

public class WaitStatementEXM implements IStatement{

    private final Integer number;

    public WaitStatementEXM(Integer number) {
        this.number = number;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        if (number != 0) {
            IStatement newStatement = new CompoundStatement(new PrintStatement(new ValueExpression(new IntValue(number))),
                    new WaitStatementEXM(number - 1));
            state.getExecutionStack().push(newStatement);
        }
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
        return "wait(" + number.toString() + ")";
    }
}
