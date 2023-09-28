package Model.Statements;

import Exceptions.MyException;
import Model.ADT.MyIDictionary;
import Model.ADT.MyIHeap;
import Model.Expressions.IExpression;
import Model.ProgramState;
import Model.Types.ReferenceType;
import Model.Types.Type;
import Model.Values.ReferenceValue;
import Model.Values.Value;

public class HeapWritingStatement implements IStatement {
    private final String varName;
    private final IExpression expression;

    public HeapWritingStatement(String varName, IExpression expression) {
        this.varName = varName;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIHeap heap = state.getHeap();
        if (!symTable.isDefined(varName)) {
            throw new MyException(String.format("%s is not defined", varName));
        }
        Value value = symTable.getValue(varName);
        if (!(value instanceof ReferenceValue)) {
            throw new MyException(String.format("%s not of reference type", varName));
        }
        ReferenceValue referenceValue = (ReferenceValue) value;
        if (!heap.isDefined(referenceValue.getAddress())) {
            throw new MyException(String.format("Address %s was not defined!", referenceValue.getAddress()));
        }
        Value evaluatedExpr = expression.eval(symTable, heap);
        if (!evaluatedExpr.getType().equals(referenceValue.getLocationType())) {
            throw new MyException(String.format("%s not of %s", evaluatedExpr, referenceValue.getLocationType()));
        }
        heap.update(referenceValue.getAddress(), evaluatedExpr);
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new HeapNewStatement(varName, expression.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        if (typeEnv.getValue(varName).equals(new ReferenceType(expression.typeCheck(typeEnv))))
            return typeEnv;
        else
            throw new MyException("WriteHeap: right hand side and left hand side have different types.");
    }

    @Override
    public String toString() {
        return String.format("WriteHeap{%s, %s}", varName, expression);
    }
}
