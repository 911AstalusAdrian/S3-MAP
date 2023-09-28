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

public class UnlockStatement implements IStatement {
    private final String varName;

    public UnlockStatement(String varName) {
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
            return null;
        Integer lockTableEntry = lockTable.lookup(address);
        if(lockTableEntry == state.getId()) {
            lockTable.update(address, -1);
        }
        return null;
    }

    @Override
    public IStatement deep_copy() {
        return new UnlockStatement(varName);
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnv) throws Exception {
        IType typeVar = typeEnv.lookup(varName);
        if (!(typeVar instanceof IntType))
            throw new TypeCheckError(varName + " is not of type integer");
        return typeEnv;
    }

    public String toString() {
        return "unlock(" + varName + ")";
    }
}
