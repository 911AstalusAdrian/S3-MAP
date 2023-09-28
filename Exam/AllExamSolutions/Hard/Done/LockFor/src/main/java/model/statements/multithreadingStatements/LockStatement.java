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

public class LockStatement implements IStatement {
    private final String varName;

    public LockStatement(String varName) {
        this.varName = varName;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        IDictionary<String, IValue> symTable = state.getSymTable();
        ITable<Integer> lockTable = state.getLockTable();
        if (!symTable.isDefined(varName))
            throw new VariableNameNotFound(varName);
        IValue varval = symTable.lookup(varName);
        if (!(varval.getType().equals(IntType.T)))
            throw new WrongType("Identifier", varval.getType(), IntType.T);
        Integer address = ((IntValue)varval).getWrappedValue();
        if(!lockTable.isDefined(address))
            throw new InvalidAddress(varName);
        Integer lockTableEntry = lockTable.lookup(address);
        if(lockTableEntry == -1) {
            lockTable.update(address, state.getId());
        }
        else{
            state.getExecutionStack().push(new LockStatement(varName));
        }
        return null;
    }

    @Override
    public IStatement deep_copy() {
        return new LockStatement(varName);
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnv) throws Exception {
        IType typeVar = typeEnv.lookup(varName);
        if (!(typeVar instanceof IntType))
            throw new TypeCheckError(varName + " is not of type integer");
        return typeEnv;
    }

    public String toString() {
        return "lock(" + varName + ")";
    }
}
