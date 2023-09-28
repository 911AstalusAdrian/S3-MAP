package Model.Expressions;

import Exceptions.MyException;
import Model.ADT.MyIDictionary;
import Model.ADT.MyIHeap;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.IntValue;
import Model.Values.Value;

public class ArithmeticExpression implements IExpression {
    IExpression firstExpression;
    IExpression secondExpression;
    char operation;

    public ArithmeticExpression(IExpression firstExpression, IExpression secondExpression, char operation) {
        this.firstExpression = firstExpression;
        this.secondExpression = secondExpression;
        this.operation = operation;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> symbolTable, MyIHeap heap) throws MyException {
        Value firstValue, secondValue;
        firstValue = firstExpression.eval(symbolTable, heap);
        if (firstValue.getType().equals(new IntType())) {
            secondValue = secondExpression.eval(symbolTable,heap);
            if (secondValue.getType().equals(new IntType())) {
                IntValue firstIntValue = (IntValue)firstValue;
                IntValue secondIntValue = (IntValue)secondValue;
                switch (operation) {
                    case '+': return new IntValue(firstIntValue.getValue() + secondIntValue.getValue());
                    case '-': return new IntValue(firstIntValue.getValue() - secondIntValue.getValue());
                    case '*': return new IntValue(firstIntValue.getValue() * secondIntValue.getValue());
                    case '/': {
                        if (secondIntValue.getValue() == 0) {
                            throw new MyException("Division by zero!");
                        } else {
                            return new IntValue(firstIntValue.getValue() / secondIntValue.getValue());
                        }
                    }
                    default:
                        throw new MyException("Operation is not a valid one");
                }
            } else {
                throw new MyException("Second operand is not an integer!");
            }
        } else {
            throw new MyException("First operand is not an integer!");
        }
    }

    @Override
    public IExpression deepCopy() {
        return new ArithmeticExpression(firstExpression.deepCopy(), secondExpression.deepCopy(), operation);
    }

    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type type1, type2;
        type1 = firstExpression.typeCheck(typeEnv);
        type2 = secondExpression.typeCheck(typeEnv);
        if (type1.equals(new IntType())) {
            if (type2.equals(new IntType())) {
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
        return String.format("%s %s %s", firstExpression, operation, secondExpression);
    }
}
