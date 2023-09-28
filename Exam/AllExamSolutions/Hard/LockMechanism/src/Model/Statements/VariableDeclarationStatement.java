package Model.Statements;

import Exceptions.MyException;
import Model.ADT.MyIDictionary;
import Model.ProgramState;
import Model.Types.Type;
import Model.Values.Value;

public class VariableDeclarationStatement implements IStatement{
    private final String variableName;
    private final Type variableType;

    public VariableDeclarationStatement(String variableName, Type variableType) {
        this.variableName = variableName;
        this.variableType = variableType;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        if (symTable.isDefined(variableName)) {
            throw new MyException(String.format("%S already exists in the symTable", variableName));
        }
        symTable.put(variableName, variableType.getDefault());
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new VariableDeclarationStatement(variableName, variableType);
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        typeEnv.put(variableName, variableType);
        return typeEnv;
    }

    @Override
    public String toString() {
        return String.format("(%s:%s)", variableName, variableType.toString());
    }
}
