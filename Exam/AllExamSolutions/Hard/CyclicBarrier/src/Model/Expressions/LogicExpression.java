package Model.Expressions;

import Exceptions.MyException;
import Model.ADT.MyIDictionary;
import Model.ADT.MyIHeap;
import Model.Types.BoolType;
import Model.Types.Type;
import Model.Values.BoolValue;
import Model.Values.Value;

import java.util.Objects;

public class LogicExpression implements IExpression {
    IExpression firstExpression;
    IExpression secondExpression;
    String operation;

    public LogicExpression(IExpression firstExpression, IExpression secondExpression, String operation) {
        this.firstExpression = firstExpression;
        this.secondExpression = secondExpression;
        this.operation = operation;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> symbolTable, MyIHeap heap) throws MyException {
        Value firstValue, secondValue;
        firstValue = firstExpression.eval(symbolTable, heap);
        if (firstValue.getType().equals(new BoolType())) {
            secondValue = secondExpression.eval(symbolTable,heap);
            if (secondValue.getType().equals(new BoolType())) {
                BoolValue firstBoolValue = (BoolValue) firstValue;
                BoolValue secondBoolValue = (BoolValue) secondValue;
                if (Objects.equals(operation, "and")) {
                    return new BoolValue(firstBoolValue.getValue() && secondBoolValue.getValue());
                }
                if (Objects.equals(operation, "or")) {
                    return new BoolValue(firstBoolValue.getValue() || secondBoolValue.getValue());
                }
            } else {
                throw new MyException("Second operand is not of type bool");
            }
        } else {
            throw new MyException("First operand is not of type bool");
        }
        return null;
    }

    @Override
    public IExpression deepCopy() {
        return new LogicExpression(firstExpression.deepCopy(), secondExpression.deepCopy(), operation);
    }

    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type type1, type2;
        type1 = firstExpression.typeCheck(typeEnv);
        type2 = secondExpression.typeCheck(typeEnv);
        if (type1.equals(new BoolType())) {
            if (type2.equals(new BoolType())) {
                return new BoolType();
            } else {
                throw new MyException("Second operand is not a boolean!");
            }
        } else {
            throw new MyException("First operand is not a boolean!");
        }
    }

    @Override
    public String toString() {
        return firstExpression.toString() + " " + operation + " " + secondExpression.toString();
    }
}
