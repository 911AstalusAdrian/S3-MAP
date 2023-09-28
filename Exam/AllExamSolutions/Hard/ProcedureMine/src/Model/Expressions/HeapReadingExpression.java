package Model.Expressions;

import Exceptions.MyException;
import Model.ADT.MyIDictionary;
import Model.ADT.MyIHeap;
import Model.Types.ReferenceType;
import Model.Types.Type;
import Model.Values.ReferenceValue;
import Model.Values.Value;

import java.sql.Ref;

public class HeapReadingExpression implements IExpression {
    private final IExpression expression;

    public HeapReadingExpression(IExpression expression) {
        this.expression = expression;
    }
    @Override
    public Value eval(MyIDictionary<String, Value> symbolTable, MyIHeap heap) throws MyException {
        Value value = expression.eval(symbolTable, heap);
        if (!(value instanceof ReferenceValue)) {
            throw new MyException(String.format("%s not of reference type", value));
        }
        ReferenceValue referenceValue = (ReferenceValue) value;
        if (!heap.isDefined(referenceValue.getAddress())) {
            throw new MyException(String.format("Address %s was not defined", referenceValue.getAddress()));
        }
        return heap.get(referenceValue.getAddress());
    }

    @Override
    public IExpression deepCopy() {
        return new HeapReadingExpression(expression.deepCopy());
    }

    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type type = expression.typeCheck(typeEnv);
        if (type instanceof ReferenceType) {
            ReferenceType referenceType = (ReferenceType) type;
            return referenceType.getInnerType();
        } else {
            throw new MyException("The read heap argument is not of type reference!");
        }
    }

    @Override
    public String toString() {
        return String.format("ReadHeap{%s}", expression);
    }
}
