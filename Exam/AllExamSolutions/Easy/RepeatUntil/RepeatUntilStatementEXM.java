package Model.Statements;

import Exceptions.MyException;
import Model.ADT.MyIDictionary;
import Model.Expressions.IExpression;
import Model.ProgramState;
import Model.Types.BoolType;
import Model.Types.Type;

public class RepeatUntilStatementEXM implements IStatement {
    private final IStatement statement;
    private final IExpression expression;

    public RepeatUntilStatementEXM(IStatement statement, IExpression expression) {
        this.statement = statement;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        IStatement newStatement = new CompoundStatement(statement, new WhileStatement(expression, statement));
        state.getExecutionStack().push(newStatement);
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type expressionType;
        expressionType = expression.typeCheck(typeEnv);
        if (expressionType.equals(new BoolType())) {
            statement.typeCheck(typeEnv.copy());
            return typeEnv;
        } else {
            throw new MyException("Expression type is not boolean!");
        }
    }

    @Override
    public String toString() {
        return String.format("\nrepeat {\n %s \n} until {%s}\n", statement.toString(), expression.toString());
    }
}
