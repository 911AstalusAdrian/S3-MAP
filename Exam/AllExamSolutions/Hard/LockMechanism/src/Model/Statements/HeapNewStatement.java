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

public class HeapNewStatement implements IStatement {
    private final String variableName;
    private final IExpression expression;

    public HeapNewStatement(String variableName, IExpression expression) {
        this.variableName = variableName;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIHeap heap = state.getHeap();
        if (!symTable.isDefined(variableName)) {
            throw new MyException(String.format("Variable %s was not defined", variableName));
        }
        Value value = symTable.getValue(variableName);
        if (!(value.getType() instanceof ReferenceType)) { // !value.getType().equals(new ReferenceType(new IntType()))
            throw new MyException(String.format("%s not of reference type", variableName));
        }
        Value evaluated = expression.eval(symTable, heap);
        ReferenceValue refValue = (ReferenceValue) value;
        if (!(refValue.getLocationType().equals(evaluated.getType()))) {
            throw new MyException(String.format("%s not of type %s", variableName, evaluated.getType()));
        }
        Integer newPosition = heap.add(evaluated);
        symTable.put(variableName, new ReferenceValue(newPosition, refValue.getLocationType()));
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new HeapNewStatement(variableName, expression.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typeVar = typeEnv.getValue(variableName);
        Type typeExpr = expression.typeCheck(typeEnv);
        if (typeVar.equals(new ReferenceType(typeExpr))) {
            return typeEnv;
        } else {
            throw new MyException("New statement error: right hand side and left hand side have different type!");
        }
    }

    @Override
    public String toString() {
        return String.format("New{%s, %s}", variableName, expression);
    }
}
