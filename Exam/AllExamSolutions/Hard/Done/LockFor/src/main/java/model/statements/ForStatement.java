package model.statements;

import model.ProgramState;
import model.customExceptions.TypeCheckError;
import model.customExceptions.WrongType;
import model.dataStructures.IDictionary;
import model.dataStructures.IStack;
import model.dataTypes.BoolType;
import model.dataTypes.IType;
import model.expressions.IExpression;
import model.values.IValue;

public class ForStatement implements IStatement{
    private final IStatement initialize;
    private final IExpression controlCondition;
    private final IStatement body;
    private final IStatement iterationStep;

    public ForStatement(IStatement initialize, IExpression controlCondition, IStatement body, IStatement iterationStep) {
        this.initialize = initialize;
        this.controlCondition = controlCondition;
        this.body = body;
        this.iterationStep = iterationStep;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        IStack<IStatement> exeStack = state.getExecutionStack();
        IValue expValue = controlCondition.evaluate(state.getSymTable(), state.getHeap());
        if (!expValue.getType().equals(BoolType.T))
            throw new WrongType("Condition", BoolType.T, expValue.getType());
        exeStack.push(new CompStatement(initialize, new WhileStatement(controlCondition, new CompStatement(body, iterationStep))));
        return null;
    }

    @Override
    public IStatement deep_copy() {
        return new ForStatement(initialize, controlCondition, body, iterationStep);
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnv) throws Exception {
        IType typeExp = controlCondition.typeCheck(typeEnv);
        if (!typeExp.equals(BoolType.T))
            throw new TypeCheckError("Condition of for statement is not of type boolean");
        initialize.typeCheck(typeEnv);
        body.typeCheck(typeEnv.deep_copy());
        iterationStep.typeCheck(typeEnv.deep_copy());
        return typeEnv;
    }

    public String toString() {
        return "(FOR(" + initialize.toString() + ";" + controlCondition.toString() + ";" + iterationStep.toString()+ ") " + body.toString() + ")";
    }
}
