package Model.Statements;

import Exceptions.MyException;
import Model.ADT.MyIDictionary;
import Model.Expressions.IExpression;
import Model.ProgramState;
import Model.Types.BoolType;
import Model.Types.Type;

public class ConditionalAssignmentStatementEXM implements IStatement {
    private final String varName;
    private final IExpression firstExpression;
    private final IExpression secondExpression;
    private final IExpression thirdExpression;

    public ConditionalAssignmentStatementEXM(String varName, IExpression firstExpression, IExpression secondExpression, IExpression thirdExpression) {
        this.varName = varName;
        this.firstExpression = firstExpression;
        this.secondExpression = secondExpression;
        this.thirdExpression = thirdExpression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        IStatement newStatement = new IfStatement(firstExpression, new AssignmentStatement(varName, secondExpression),
                new AssignmentStatement(varName, thirdExpression));
        state.getExecutionStack().push(newStatement);
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type varType = typeEnv.getValue(varName);
        Type firstExprVarType = secondExpression.typeCheck(typeEnv);
        Type secondExprVarType = thirdExpression.typeCheck(typeEnv);
        Type condType = firstExpression.typeCheck(typeEnv);
        if (condType.equals(new BoolType())) {
            if (varType.equals(firstExprVarType)) {
                if (varType.equals(secondExprVarType)) {
                    return typeEnv;
                } else {
                    throw new MyException("Variable and second expression must have the same type!");
                }
            } else {
                throw new MyException("Variable and first expression must have the same type!");
            }
        } else {
            throw new MyException("Expression must be of type bool!");
        }
    }

    @Override
    public String toString() {
        return String.format("\n%s=%s?%s:%s\n", varName, firstExpression.toString(), secondExpression.toString(), thirdExpression.toString());
    }
}
