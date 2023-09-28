package Model.Expressions;

import Exceptions.MyException;
import Model.ADT.MyIDictionary;
import Model.ADT.MyIHeap;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.IntValue;
import Model.Values.Value;

public class MULExpressionEXM implements IExpression {
    private final IExpression firstExpression;
    private final IExpression secondExpression;

    public MULExpressionEXM(IExpression firstExpression, IExpression secondExpression) {
        this.firstExpression = firstExpression;
        this.secondExpression = secondExpression;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> symbolTable, MyIHeap heap) throws MyException {
        Value firstValue, secondValue;
        firstValue = firstExpression.eval(symbolTable, heap);
        if (firstValue.getType().equals(new IntType())) {
            secondValue = secondExpression.eval(symbolTable, heap);
            if (secondValue.getType().equals(new IntType())) {
                IntValue firstIntValue = (IntValue) firstValue;
                IntValue secondIntValue = (IntValue) secondValue;
                return new IntValue(firstIntValue.getValue() * secondIntValue.getValue() - firstIntValue.getValue() - secondIntValue.getValue());
            } else {
                throw new MyException("Second operand is not an integer!");
            }
        } else {
            throw new MyException("First operand is not an integer!");
        }
    }

    @Override
    public IExpression deepCopy() {
        return null;
    }

    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type firstType, secondType;
        firstType = firstExpression.typeCheck(typeEnv);
        secondType = secondExpression.typeCheck(typeEnv);
        if (firstType.equals(new IntType())) {
            if (secondType.equals(new IntType())) {
                return new IntType();
            } else {
                throw new MyException("Second operand is not an integer!");
            }
        } else {
            throw new MyException("First operand is not an integer!");
        }
    }

    @Override
    public String toString() {
        return String.format("MUL(%s %s)", firstExpression, secondExpression);
    }
}

