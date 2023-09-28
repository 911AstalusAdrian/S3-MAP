package Model.Statements;

import Exceptions.MyException;
import Model.ADT.MyIDictionary;
import Model.Expressions.IExpression;
import Model.ProgramState;
import Model.Types.BoolType;
import Model.Types.Type;
import Model.Values.BoolValue;
import Model.Values.Value;

public class WhileStatement implements IStatement {
    private final IExpression expression;
    private final IStatement statement;

    public WhileStatement(IExpression expression, IStatement statement) {
        this.expression = expression;
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        Value value = expression.eval(state.getSymTable().peek(), state.getHeap());
        if (!(value instanceof BoolValue)) {
            throw new MyException(String.format("Expression %s cannot be evaluated as boolean", expression));
        }
        BoolValue boolValue = (BoolValue) value;
        if (boolValue.getValue()) {
            state.getExecutionStack().push(this);
            state.getExecutionStack().push(statement);
        }
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new WhileStatement(expression.deepCopy(), statement.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typeExp = expression.typeCheck(typeEnv);
        if (typeExp.equals(new BoolType())) {
            statement.typeCheck(typeEnv.copy());
            return typeEnv;
        } else {
            throw new MyException("The condition of while if not of type bool!");
        }
    }

    @Override
    public String toString() {
        return String.format("while(%s) {\n%s\n}", expression, statement);
    }
}
