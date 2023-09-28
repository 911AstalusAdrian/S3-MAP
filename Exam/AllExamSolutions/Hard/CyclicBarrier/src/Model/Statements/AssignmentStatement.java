package Model.Statements;

import Exceptions.MyException;
import Model.ADT.MyIDictionary;
import Model.ADT.MyIHeap;
import Model.Expressions.IExpression;
import Model.ProgramState;
import Model.Types.Type;
import Model.Values.Value;


public class AssignmentStatement implements IStatement {
    private final String key;
    private final IExpression expression;

    public AssignmentStatement(String key, IExpression expression) {
        this.key = key;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIDictionary<String, Value> symbolTable = state.getSymTable();
        MyIHeap heap = state.getHeap();
        if (symbolTable.isDefined(key)) {
            Value value = expression.eval(symbolTable, heap);
            Type typeId = (symbolTable.getValue(key)).getType();
            if (value.getType().equals(typeId)) {
                symbolTable.update(key,value);
            } else {
                throw new MyException("Declared type of variable " + key + " and type of the assigned expression do not match.");
            }
        } else {
            throw new MyException("The variable " + key + " was not declared before.");
        }
        return null;
    }

    public IStatement deepCopy() { return new AssignmentStatement(key, expression.deepCopy()); }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typeVar = typeEnv.getValue(key);
        Type typeExpr = expression.typeCheck(typeEnv);
        if (typeVar.equals(typeExpr)) {
            return typeEnv;
        } else {
            throw new MyException("Assignment error: right hand side and left hand side have different types!");
        }
    }

    @Override
    public String toString() {
        return String.format("{%s = %s}", key, expression);
    }
}
