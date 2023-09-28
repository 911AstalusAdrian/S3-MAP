package Model.Expressions;

import Exceptions.MyException;
import Model.ADT.MyIDictionary;
import Model.ADT.MyIHeap;
import Model.Types.BoolType;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.BoolValue;
import Model.Values.IntValue;
import Model.Values.Value;

public class RelationalExpression implements IExpression {
    private final IExpression firstExpression;
    private final IExpression secondExpression;
    private final String operator;

    public RelationalExpression(IExpression firstExpression, String operator, IExpression secondExpression) {
        this.firstExpression = firstExpression;
        this.operator = operator;
        this.secondExpression = secondExpression;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> symbolTable, MyIHeap heap) throws MyException {
        Value firstValue = firstExpression.eval(symbolTable, heap);
        if (!firstValue.getType().equals(new IntType())) {
            throw new MyException(String.format("%s is not an integer!", firstValue));
        }
        Value secondValue = secondExpression.eval(symbolTable, heap);
        if (!secondValue.getType().equals(new IntType())) {
            throw new MyException(String.format("%s is not an integer!", secondValue));
        }
        return switch (operator) {
            case "<" -> new BoolValue(((IntValue) firstValue).getValue() < ((IntValue) secondValue).getValue());
            case "<=" -> new BoolValue(((IntValue) firstValue).getValue() <= ((IntValue) secondValue).getValue());
            case "==" -> new BoolValue(((IntValue) firstValue).getValue() == ((IntValue) secondValue).getValue());
            case "!=" -> new BoolValue(((IntValue) firstValue).getValue() != ((IntValue) secondValue).getValue());
            case ">" -> new BoolValue(((IntValue) firstValue).getValue() > ((IntValue) secondValue).getValue());
            case ">=" -> new BoolValue(((IntValue) firstValue).getValue() >= ((IntValue) secondValue).getValue());
            default -> throw new MyException("Invalid operand!");
        };
    }

    @Override
    public IExpression deepCopy() {
        return new RelationalExpression(firstExpression.deepCopy(), operator, secondExpression.deepCopy());
    }

    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type type1, type2;
        type1 = firstExpression.typeCheck(typeEnv);
        type2 = secondExpression.typeCheck(typeEnv);
        if (type1.equals(new IntType())) {
            if (type2.equals(new IntType())) {
                return new BoolType();
            } else {
                throw new MyException("Second operand is not an integer!");
            }
        } else {
            throw new MyException("First operand is not an integer!");
        }
    }

    @Override
    public String toString() {
        return String.format("%s %s %s", firstExpression, operator, secondExpression);
    }
}
