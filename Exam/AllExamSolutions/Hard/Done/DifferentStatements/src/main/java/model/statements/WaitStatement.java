package model.statements;

import model.ProgramState;
import model.customExceptions.TypeCheckError;
import model.customExceptions.WrongType;
import model.dataStructures.IDictionary;
import model.dataStructures.IStack;
import model.dataTypes.IType;
import model.dataTypes.IntType;
import model.expressions.IExpression;
import model.expressions.ValueExpression;
import model.values.IValue;
import model.values.IntValue;

public class WaitStatement implements IStatement{
    private final IExpression number;

    public WaitStatement(IExpression number) {
        this.number = number;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        IStack<IStatement> exeStack = state.getExecutionStack();
        IValue expValue = number.evaluate(state.getSymTable(), state.getHeap());
        if (!expValue.getType().equals(IntType.T))
            throw new WrongType("Expression", IntType.T, expValue.getType());
        Integer nr = ((IntValue)expValue).getWrappedValue();
        if(nr != 0){
            exeStack.push(new CompStatement(new PrintStatement(number), new WaitStatement(new ValueExpression(new IntValue(nr - 1)))));
        }
        return null;
    }

    @Override
    public IStatement deep_copy() {
        return new WaitStatement(number);
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnv) throws Exception {
        IType typeExp = number.typeCheck(typeEnv);
        if (!typeExp.equals(IntType.T))
            throw new TypeCheckError("Wait expression is not of type integer");
        return typeEnv;
    }

    @Override
    public String toString() {
        return "wait("+number.toString()+")";
    }
}
