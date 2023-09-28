package model.statements;

import model.ProgramState;
import model.customExceptions.TypeCheckError;
import model.customExceptions.WrongType;
import model.dataStructures.IDictionary;
import model.dataStructures.IStack;
import model.dataTypes.IType;
import model.expressions.IExpression;
import model.expressions.RelationalExpression;
import model.values.IValue;

public class SwitchStatement implements IStatement{
    private final IExpression testExp;
    private final IExpression case1Exp;
    private final IStatement case1St;
    private final IExpression case2Exp;
    private final IStatement case2St;
    private final IStatement defaultSt;

    public SwitchStatement(IExpression testExp, IExpression case1Exp, IStatement case1St, IExpression case2Exp, IStatement case2St, IStatement defaultSt) {
        this.testExp = testExp;
        this.case1Exp = case1Exp;
        this.case1St = case1St;
        this.case2Exp = case2Exp;
        this.case2St = case2St;
        this.defaultSt = defaultSt;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        IStack<IStatement> exeStack = state.getExecutionStack();
        IValue expValue = testExp.evaluate(state.getSymTable(), state.getHeap());
        IValue expValue1 = case1Exp.evaluate(state.getSymTable(), state.getHeap());
        IValue expValue2 = case2Exp.evaluate(state.getSymTable(), state.getHeap());
        if(!(expValue.getType().equals(expValue1.getType())))
            throw new WrongType("Expression for Case 1", expValue.getType(),expValue1.getType());
        if(!(expValue.getType().equals(expValue2.getType())))
            throw new WrongType("Expression for Case 2", expValue.getType(),expValue2.getType());
        exeStack.push(new IfStatement(new RelationalExpression(testExp, case1Exp, "=="), case1St, new IfStatement(new RelationalExpression(testExp, case2Exp, "=="), case2St, defaultSt)));
        return null;
    }

    @Override
    public IStatement deep_copy() {
        return new SwitchStatement(testExp,case1Exp,case1St,case2Exp,case2St,defaultSt);
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnv) throws Exception {
        IType typeExp = testExp.typeCheck(typeEnv);
        IType typeExp1 = case1Exp.typeCheck(typeEnv);
        IType typeExp2 = case2Exp.typeCheck(typeEnv);
        if(!typeExp.equals(typeExp1))
            throw new TypeCheckError("Test expression and first case expression have mismatching types");
        if(!typeExp.equals(typeExp2))
            throw new TypeCheckError("Test expression and second case expression have mismatching types");
        case1St.typeCheck(typeEnv.deep_copy());
        case2St.typeCheck(typeEnv.deep_copy());
        defaultSt.typeCheck(typeEnv.deep_copy());
        return typeEnv;
    }

    @Override
    public String toString() {
        return "switch(" + testExp.toString() + ") (case " + case1Exp.toString() + ": " + case1St.toString()+") (case " + case2Exp.toString() + ": " + case2St.toString() +") (default: " + defaultSt.toString() + ")";
    }
}
