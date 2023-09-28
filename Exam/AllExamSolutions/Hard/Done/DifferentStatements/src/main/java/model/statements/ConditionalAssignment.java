package model.statements;

import model.ProgramState;
import model.customExceptions.TypeCheckError;
import model.customExceptions.VariableNameNotFound;
import model.customExceptions.WrongType;
import model.dataStructures.IDictionary;
import model.dataTypes.BoolType;
import model.dataTypes.IType;
import model.expressions.IExpression;
import model.values.IValue;

public class ConditionalAssignment implements IStatement{
    private final String varName;
    private final IExpression exp1;
    private final IExpression exp2;
    private final IExpression exp3;

    public ConditionalAssignment(String varName, IExpression exp1, IExpression exp2, IExpression exp3) {
        this.varName = varName;
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.exp3 = exp3;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        IDictionary<String, IValue> symbolTable = state.getSymTable();
        if (!symbolTable.isDefined(varName))
            throw new VariableNameNotFound(varName);
        IValue val1 = exp1.evaluate(state.getSymTable(), state.getHeap());
        if (!val1.getType().equals(BoolType.T))
            throw new WrongType("Condition", BoolType.T, val1.getType());
        IValue val2 = exp2.evaluate(symbolTable, state.getHeap());
        if (!val2.getType().equals(symbolTable.lookup(varName).getType()))
            throw new WrongType("Expression", val2.getType(), symbolTable.lookup(varName).getType());
        IValue val3 = exp3.evaluate(symbolTable, state.getHeap());
        if (!val3.getType().equals(symbolTable.lookup(varName).getType()))
            throw new WrongType("Expression", val3.getType(), symbolTable.lookup(varName).getType());
        state.getExecutionStack().push(new IfStatement(exp1, new Assignment(varName, exp2), new Assignment(varName, exp3)));
        return null;
    }

    @Override
    public IStatement deep_copy() {
        return new ConditionalAssignment(varName, exp1, exp2, exp3);
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnv) throws Exception {
        IType typeVar = typeEnv.lookup(varName);
        IType typeExp1 = exp1.typeCheck(typeEnv);
        IType typeExp2 = exp2.typeCheck(typeEnv);
        IType typeExp3 = exp3.typeCheck(typeEnv);
        if (!typeExp1.equals(BoolType.T))
            throw new TypeCheckError("Condition of conditional assignment is not of boolean type");
        if (!typeVar.equals(typeExp2))
            throw new TypeCheckError("Conditional Assignment: first expression and variable have different types");
        if (!typeVar.equals(typeExp3))
            throw new TypeCheckError("Conditional Assignment: second expression and variable have different types");
        return typeEnv;
    }

    @Override
    public String toString() {
        return varName + " = (" + exp1.toString() + ")?" + exp2.toString() + ":" + exp3.toString();
    }
}
