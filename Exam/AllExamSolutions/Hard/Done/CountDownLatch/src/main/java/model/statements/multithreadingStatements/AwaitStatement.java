package model.statements.multithreadingStatements;

import model.ProgramState;
import model.customExceptions.InvalidAddress;
import model.customExceptions.TypeCheckError;
import model.customExceptions.VariableNameNotFound;
import model.customExceptions.WrongType;
import model.dataStructures.IDictionary;
import model.dataStructures.ITable;
import model.dataTypes.IType;
import model.dataTypes.IntType;
import model.statements.IStatement;
import model.values.IValue;
import model.values.IntValue;


public class AwaitStatement implements IStatement {
    private final String varName;

    public AwaitStatement(String varName) {
        this.varName = varName;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        IDictionary<String, IValue> symtable = state.getSymTable();
        ITable<Integer> latchTable = state.getLatchTable();
        if (!symtable.isDefined(varName))
            throw new VariableNameNotFound(varName);
        IValue val = symtable.lookup(varName);
        if (!(val.getType().equals(IntType.T)))
            throw new WrongType("Identifier", val.getType(), IntType.T);
        Integer address = ((IntValue)val).getWrappedValue();
        if(!latchTable.isDefined(address))
            throw new InvalidAddress(varName);
        Integer latchTableEntry = latchTable.lookup(address);
        if(latchTableEntry != 0) {
            state.getExecutionStack().push(new AwaitStatement(varName));
        }
        return null;
    }

    @Override
    public IStatement deep_copy() {
        return new AwaitStatement(varName);
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnv) throws Exception {
        IType typeVar = typeEnv.lookup(varName);
        if (!(typeVar instanceof IntType))
            throw new TypeCheckError(varName + " is not of type integer");
        return typeEnv;
    }

    public String toString() {
        return "await(" + varName + ")";
    }
}
