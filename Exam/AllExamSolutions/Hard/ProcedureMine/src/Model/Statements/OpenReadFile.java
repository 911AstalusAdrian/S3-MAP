package Model.Statements;

import Exceptions.MyException;
import Model.ADT.MyIDictionary;
import Model.Expressions.IExpression;
import Model.ProgramState;
import Model.Types.StringType;
import Model.Types.Type;
import Model.Values.StringValue;
import Model.Values.Value;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class OpenReadFile implements IStatement {
    private final IExpression expression;

    public OpenReadFile(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public IStatement deepCopy() {
        return new OpenReadFile(expression.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type type = expression.typeCheck(typeEnv);
        if (type.equals(new StringType())) {
            return typeEnv;
        } else {
            throw new MyException("OpenReadFile requires a string expression!");
        }
    }

    @Override
    public String toString() {
        return String.format("OpenReadFile{%s}", expression);
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        Value value = expression.eval(state.getSymTable().peek(), state.getHeap());
        if (!value.getType().equals(new StringType())) {
            throw new MyException(String.format("ERROR: %s does not evaluate to StringValue", expression));
        }
        StringValue fileName = (StringValue) value;
        MyIDictionary<String, BufferedReader> fileTable = state.getFileTable();
        if (fileTable.isDefined(fileName.getValue())) {
            throw new MyException(String.format("The file %s is already opened!", fileName));
        }
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(fileName.getValue()));
        } catch (IOException e) {
            throw new MyException(String.format("The file %s could not be opened", fileName));
        }
        fileTable.put(fileName.getValue(), br);
        return null;
    }
}
