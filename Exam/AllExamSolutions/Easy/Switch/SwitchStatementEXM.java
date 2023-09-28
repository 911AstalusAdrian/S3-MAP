package Model.Statements;

import Exceptions.MyException;
import Model.ADT.MyIDictionary;
import Model.Expressions.IExpression;
import Model.Expressions.RelationalExpression;
import Model.ProgramState;
import Model.Types.Type;

public class SwitchStatementEXM implements IStatement {
    private final IExpression expression;
    private final IExpression firstExpression;
    private final IExpression secondExpression;
    private final IStatement firstStatement;
    private final IStatement secondStatement;
    private final IStatement thirdStatement;

    public SwitchStatementEXM(IExpression expression, IExpression firstExpression, IExpression secondExpression, IStatement firstStatement, IStatement secondStatement, IStatement thirdStatement) {
        this.expression = expression;
        this.firstExpression = firstExpression;
        this.secondExpression = secondExpression;
        this.firstStatement = firstStatement;
        this.secondStatement = secondStatement;
        this.thirdStatement = thirdStatement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        IStatement newStatement = new IfStatement(new RelationalExpression(expression, "==", firstExpression),
                firstStatement, new IfStatement(new RelationalExpression(expression, "==", secondExpression),
                secondStatement, thirdStatement));
        state.getExecutionStack().push(newStatement);
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type firstType, secondType, thirdType;
        firstType = expression.typeCheck(typeEnv);
        secondType = firstExpression.typeCheck(typeEnv);
        thirdType = secondExpression.typeCheck(typeEnv);
        if (!firstType.equals(secondType) || !firstType.equals(thirdType) || !secondType.equals(thirdType)) {
            throw new MyException("The expressions must have the same type!");
        } else {
            firstStatement.typeCheck(typeEnv.copy());
            secondStatement.typeCheck(typeEnv.copy());
            thirdStatement.typeCheck(typeEnv.copy());
            return typeEnv;
        }
    }

    @Override
    public String toString() {
        return String.format("\nswitch(%s)\n(case %s:  %s)\n(case %s: %s)\n(default: %s)\n", expression.toString(), firstExpression.toString(),
                firstStatement.toString(), secondExpression.toString(), secondStatement.toString(), thirdStatement.toString());
    }
}
