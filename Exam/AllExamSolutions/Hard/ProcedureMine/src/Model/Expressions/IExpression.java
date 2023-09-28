package Model.Expressions;

import Exceptions.MyException;
import Model.ADT.MyIDictionary;
import Model.ADT.MyIHeap;
import Model.Types.Type;
import Model.Values.Value;

public interface IExpression {
    Value eval(MyIDictionary<String, Value> symbolTable, MyIHeap heap) throws MyException;
    IExpression deepCopy();
    Type typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException;
}
