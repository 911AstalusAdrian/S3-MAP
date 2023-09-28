package Model.Statements;

import Exceptions.MyException;
import Model.ADT.MyDict;
import Model.ADT.MyIDictionary;
import Model.ADT.MyIStack;
import Model.ADT.MyStack;
import Model.ProgramState;
import Model.Types.Type;
import Model.Values.Value;

import java.util.Map;

public class ForkStatement implements IStatement {
    private final IStatement statement;

    public ForkStatement(IStatement statement) {
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIStack<IStatement> newExecutionStack = new MyStack<>();
        newExecutionStack.push(statement);
        return new ProgramState(newExecutionStack, state.getSymTable().copy(), state.getOut(), state.getFileTable(), state.getHeap(), state.getBarrierTable());
    }

    @Override
    public IStatement deepCopy() {
        return new ForkStatement(statement.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        statement.typeCheck(typeEnv.copy());
        return typeEnv;
    }

    @Override
    public String toString() {
        return String.format("Fork{\n%s\n}", statement);
    }
}
