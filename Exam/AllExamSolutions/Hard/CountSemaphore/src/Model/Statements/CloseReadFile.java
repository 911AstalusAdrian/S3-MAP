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
import java.io.IOException;

public class CloseReadFile implements IStatement {
    private final IExpression expression;

    public CloseReadFile(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        Value value = expression.eval(state.getSymTable(), state.getHeap());
        if (!value.getType().equals(new StringType())) {
            throw new MyException(String.format("%s does not evaluate to StringValue", expression));
        }
        StringValue fileName = (StringValue) value;
        MyIDictionary<String, BufferedReader> fileTable = state.getFileTable();
        if (!fileTable.isDefined(fileName.getValue())) {
            throw new MyException(String.format("%s is not present in the file table!", fileName));
        }
        BufferedReader br = fileTable.getValue(fileName.getValue());
        try {
            br.close();
        } catch (IOException e) {
            throw new MyException(String.format("Unexpected error while closing %s", fileName));
        }
        fileTable.remove(fileName.getValue());
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new CloseReadFile(expression.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type type = expression.typeCheck(typeEnv);
        if (type.equals(new StringType())) {
            return typeEnv;
        } else {
            throw new MyException("CloseReadFile requires a string expression!");
        }
    }

    @Override
    public String toString() {
        return String.format("CloseReadFile{%s}", expression);
    }
}
