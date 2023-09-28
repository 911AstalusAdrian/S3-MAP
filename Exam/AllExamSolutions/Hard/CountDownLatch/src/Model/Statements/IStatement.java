package Model.Statements;

import Exceptions.MyException;
import Model.ADT.MyIDictionary;
import Model.ProgramState;
import Model.Types.Type;

public interface IStatement {
    ProgramState execute(ProgramState state) throws MyException;
    IStatement deepCopy();
    MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException;
}
