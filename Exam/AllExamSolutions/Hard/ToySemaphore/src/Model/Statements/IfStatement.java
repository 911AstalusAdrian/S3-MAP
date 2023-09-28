package Model.Statements;

import Exceptions.MyException;
import Model.ADT.MyIDictionary;
import Model.Expressions.IExpression;
import Model.ProgramState;
import Model.Types.BoolType;
import Model.Types.Type;
import Model.Values.BoolValue;
import Model.Values.Value;

public class IfStatement implements IStatement {
    private final IExpression expression;
    private final IStatement firstStatement;
    private final IStatement secondStatement;

    public IfStatement(IExpression expression, IStatement firstStatement, IStatement secondStatement) {
        this.expression = expression;
        this.firstStatement = firstStatement;
        this.secondStatement = secondStatement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        Value value = expression.eval(state.getSymTable(), state.getHeap());
        if (value.getType().equals(new BoolType())) {
            BoolValue condition = (BoolValue) value;
            if (condition.getValue()) {
                state.getExecutionStack().push(firstStatement);
            } else {
                state.getExecutionStack().push(secondStatement);
            }
            return null;
        }
        throw new MyException(String.format("%s not of type bool inside if", value));
    }

    @Override
    public IStatement deepCopy() {
        return new IfStatement(expression.deepCopy(), firstStatement.deepCopy(), secondStatement.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typeExp = expression.typeCheck(typeEnv);
        if (typeExp.equals(new BoolType())) {
            firstStatement.typeCheck(typeEnv.copy());
            secondStatement.typeCheck(typeEnv.copy());
            return typeEnv;
        } else {
            throw new MyException("The condition of if is not of bool type!");
        }
    }

    @Override
    public String toString() {
        return String.format("if(%s){\n\t%s\n}else{\n\t%s\n}", expression.toString(), firstStatement.toString(), secondStatement.toString());
    }
}
