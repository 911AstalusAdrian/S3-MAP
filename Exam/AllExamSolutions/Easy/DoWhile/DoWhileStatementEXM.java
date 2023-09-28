package Model.Statements;

import Exceptions.MyException;
import Model.ADT.MyIDictionary;
import Model.Expressions.IExpression;
import Model.ProgramState;
import Model.Types.BoolType;
import Model.Types.Type;

public class DoWhileStatementEXM implements IStatement {
    private IStatement statement;
    private IExpression expression;

    public DoWhileStatementEXM(IStatement statement, IExpression expression) {
        this.statement = statement;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        IStatement newDoWhile = new CompoundStatement(statement, new WhileStatement(expression, statement));
        state.getExecutionStack().push(newDoWhile);
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new DoWhileStatementEXM(statement.deepCopy(), expression.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typexp = expression.typeCheck(typeEnv);
        if (typexp.equals(new BoolType())) {
            statement.typeCheck(typeEnv.copy());
            return typeEnv;
        } else throw new MyException("Condition should be bool");
    }

    @Override
    public String toString() {
        return String.format("do %s while %s", statement, expression);
    }
}
