package Model.Statements;

import Exceptions.MyException;
import Model.ADT.MyIDictionary;
import Model.Expressions.IExpression;
import Model.Expressions.RelationalExpression;
import Model.Expressions.VariableExpression;
import Model.ProgramState;
import Model.Types.IntType;
import Model.Types.Type;

public class ForStatementEXM implements IStatement {
    private String varName;
    private IExpression firstExpression;
    private IExpression secondExpression;
    private IExpression thirdExpression;
    private IStatement statement;

    public ForStatementEXM(String varName, IExpression firstExpression, IExpression secondExpression, IExpression thirdExpression, IStatement statement) {
        this.varName = varName;
        this.firstExpression = firstExpression;
        this.secondExpression = secondExpression;
        this.thirdExpression = thirdExpression;
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        IStatement newFor = new CompoundStatement(new AssignmentStatement("v", firstExpression),
                        new WhileStatement(new RelationalExpression(new VariableExpression("v"), "<", secondExpression),
                                new CompoundStatement(statement, new AssignmentStatement("v", thirdExpression))));
        state.getExecutionStack().push(newFor);
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        typeEnv.put(varName, new IntType());
        Type typv = typeEnv.getValue(varName);
        Type typexp1 = firstExpression.typeCheck(typeEnv);
        Type typexp2 = secondExpression.typeCheck(typeEnv);
        Type typexp3 = thirdExpression.typeCheck(typeEnv);

        if (typv.equals(new IntType()) && typexp1.equals(new IntType()) && typexp2.equals(new IntType()) && typexp3.equals(new IntType())) {
            statement.typeCheck(typeEnv.copy());
            return typeEnv;
        }
        else {
            throw new MyException("The for is invalid");
        }
    }

    @Override
    public String toString() {
        return "for(" + varName + "=" + firstExpression.toString() + ";" + varName + "<" + secondExpression.toString() + ";" + varName +
                "=" + thirdExpression.toString() + "){" + statement.toString() + "}";
    }
}
