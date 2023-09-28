package model.statements;

import model.ProgramState;
import model.customExceptions.TypeCheckError;
import model.customExceptions.WrongType;
import model.dataStructures.IDictionary;
import model.dataTypes.BoolType;
import model.dataTypes.IType;
import model.expressions.IExpression;
import model.expressions.NegateExpression;
import model.values.IValue;

public class RepeatUntilStatement implements IStatement{
    private final IStatement action;
    private final IExpression condition;
    public RepeatUntilStatement(IStatement action, IExpression condition) {
        this.action = action;
        this.condition = condition;

    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        IValue expValue = condition.evaluate(state.getSymTable(), state.getHeap());
        if (!expValue.getType().equals(BoolType.T))
            throw new WrongType("Condition", BoolType.T, expValue.getType());
        state.getExecutionStack().push(new CompStatement(action, new WhileStatement(new NegateExpression(condition), action)));
        return null;
    }

    @Override
    public IStatement deep_copy() {
        return new RepeatUntilStatement(action, condition);
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnv) throws Exception {
        IType typeExp = condition.typeCheck(typeEnv);
        if (!typeExp.equals(BoolType.T))
            throw new TypeCheckError("Condition of repeat until statement is not of type boolean");
        action.typeCheck(typeEnv.deep_copy());
        return typeEnv;
    }

    public String toString() {
        return "(REPEAT " + action.toString() + " UNTIL " + condition + ")" ;
    }

}
