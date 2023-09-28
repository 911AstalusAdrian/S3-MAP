package model.statements.multithreadingStatements;

import model.ProgramState;
import model.customExceptions.InvalidReference;
import model.customExceptions.TypeCheckError;
import model.customExceptions.VariableNameNotFound;
import model.customExceptions.WrongType;
import model.dataStructures.IDictionary;
import model.dataStructures.IHeap;
import model.dataTypes.IType;
import model.dataTypes.IntType;
import model.dataTypes.ReferenceType;
import model.expressions.IExpression;
import model.statements.IStatement;
import model.values.IValue;
import model.values.IntValue;

public class NewBarrierStatement implements IStatement {
    private final String var_name;
    private final IExpression exp;

    public NewBarrierStatement(String var_name, IExpression exp) {
        this.var_name = var_name;
        this.exp = exp;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        IDictionary<String, IValue> symtable = state.getSymTable();
        IHeap<IValue> heap = state.getHeap();
        IValue expval = exp.evaluate(symtable, heap);
        if(!(expval.getType().equals(IntType.T)))
            throw new WrongType("Expression", expval.getType(), IntType.T);
        if (!symtable.isDefined(var_name))
            throw new VariableNameNotFound(var_name);
        IValue varval = symtable.lookup(var_name);
        if (!(varval.getType().equals(IntType.T)))
            throw new WrongType("Identifier", varval.getType(), IntType.T);
        int newEntry = state.getBarrierTable().allocate(((IntValue)expval).getWrappedValue());
        symtable.update(var_name, new IntValue(newEntry));
        return null;
    }

    @Override
    public IStatement deep_copy() {
        return new NewBarrierStatement(var_name, exp);
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnv) throws Exception {
        IType typeVar = typeEnv.lookup(var_name);
        IType typeExp = exp.typeCheck(typeEnv);
        if (!(typeVar instanceof IntType))
            throw new TypeCheckError(var_name + " is not of type integer");
        if(!(typeExp instanceof IntType))
            throw new TypeCheckError(exp + " is not of type integer");
        return typeEnv;
    }

    public String toString() {
        return "newBarrier(" + var_name + "," + exp + ")";
    }
}
