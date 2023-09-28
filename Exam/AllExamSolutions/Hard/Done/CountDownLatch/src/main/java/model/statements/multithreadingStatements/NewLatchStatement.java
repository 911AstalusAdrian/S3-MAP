package model.statements.multithreadingStatements;

import model.ProgramState;
import model.customExceptions.TypeCheckError;
import model.customExceptions.VariableNameNotFound;
import model.customExceptions.WrongType;
import model.dataStructures.IDictionary;
import model.dataStructures.IHeap;
import model.dataTypes.IType;
import model.dataTypes.IntType;
import model.expressions.IExpression;
import model.statements.IStatement;
import model.values.IValue;
import model.values.IntValue;

public class NewLatchStatement implements IStatement {
    private final String varName;
    private final IExpression exp;

    public NewLatchStatement(String varName, IExpression exp) {
        this.varName = varName;
        this.exp = exp;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        IDictionary<String, IValue> symtable = state.getSymTable();
        IHeap<IValue> heap = state.getHeap();
        IValue expval = exp.evaluate(symtable, heap);
        if(!(expval.getType().equals(IntType.T)))
            throw new WrongType("Expression", expval.getType(), IntType.T);
        if (!symtable.isDefined(varName))
            throw new VariableNameNotFound(varName);
        IValue varval = symtable.lookup(varName);
        if (!(varval.getType().equals(IntType.T)))
            throw new WrongType("Identifier", varval.getType(), IntType.T);
        int newEntry = state.getLatchTable().allocate(((IntValue)expval).getWrappedValue());
        symtable.update(varName, new IntValue(newEntry));
        return null;
    }

    @Override
    public IStatement deep_copy() {
        return new NewLatchStatement(varName, exp);
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnv) throws Exception {
        IType typeVar = typeEnv.lookup(varName);
        IType typeExp = exp.typeCheck(typeEnv);
        if (!(typeVar instanceof IntType))
            throw new TypeCheckError(varName + " is not of type integer");
        if(!(typeExp instanceof IntType))
            throw new TypeCheckError(exp + " is not of type integer");
        return typeEnv;
    }

    public String toString() {
        return "newLatch(" + varName + "," + exp + ")";
    }
}
