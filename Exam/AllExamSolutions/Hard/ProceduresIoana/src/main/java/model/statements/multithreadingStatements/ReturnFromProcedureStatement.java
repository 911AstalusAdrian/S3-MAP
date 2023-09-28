package model.statements.multithreadingStatements;

import model.ProgramState;
import model.dataStructures.IDictionary;
import model.dataTypes.IType;
import model.expressions.IExpression;
import model.statements.IStatement;

public class ReturnFromProcedureStatement implements IStatement {
    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        state.getSymTableStack().pop();
        return null;
    }

    @Override
    public IStatement deep_copy() {
        return new ReturnFromProcedureStatement();
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnv) throws Exception {
        return typeEnv;
    }

}
