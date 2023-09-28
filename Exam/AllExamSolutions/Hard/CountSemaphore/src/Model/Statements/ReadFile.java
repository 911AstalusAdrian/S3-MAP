package Model.Statements;

import Exceptions.MyException;
import Model.ADT.MyIDictionary;
import Model.ADT.MyIHeap;
import Model.Expressions.IExpression;
import Model.ProgramState;
import Model.Types.IntType;
import Model.Types.StringType;
import Model.Types.Type;
import Model.Values.IntValue;
import Model.Values.StringValue;
import Model.Values.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFile implements IStatement{
    private final IExpression expression;
    private final String varName;

    public ReadFile(IExpression expression, String varName) {
        this.expression = expression;
        this.varName = varName;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIDictionary<String, Value> symbolTable = state.getSymTable();
        MyIDictionary<String, BufferedReader> fileTable = state.getFileTable();
        MyIHeap heap = state.getHeap();
        if (!symbolTable.isDefined(varName)) {
            throw new MyException(String.format("%s is not in the symbol table!", varName));
        }
        Value value = symbolTable.getValue(varName);
        if (!value.getType().equals(new IntType())) {
            throw new MyException(String.format("%s is not of IntType!", value));
        }
        value = expression.eval(symbolTable, heap);
        if (!value.getType().equals(new StringType())) {
            throw new MyException(String.format("%s does not evaluate to StringType", value));
        }
        StringValue string = (StringValue) value;
        if (!fileTable.isDefined(string.getValue())) {
            throw new MyException(String.format("The file table does not contain %s", string));
        }
        BufferedReader br = fileTable.getValue(string.getValue());
        try {
            String line = br.readLine();
            if (line == null) {
                line = "0";
            }
            symbolTable.put(varName, new IntValue(Integer.parseInt(line)));
        } catch (IOException e) {
            throw new MyException(String.format("Could not read from file %s", string));

        }
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new ReadFile(expression.deepCopy(), varName);
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        if (expression.typeCheck(typeEnv).equals(new StringType()))
            if (typeEnv.getValue(varName).equals(new IntType()))
                return typeEnv;
            else
                throw new MyException("ReadFile requires an int as its variable parameter.");
        else
            throw new MyException("ReadFile requires a string as es expression parameter.");
    }

    @Override
    public String toString() {
        return String.format("ReadFile{%s, %s}", expression, varName);
    }
}
