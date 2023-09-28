package model.statements.multithreadingStatements;

import model.ProgramState;
import model.customExceptions.TypeCheckError;
import model.customExceptions.VariableNameNotFound;
import model.customExceptions.WrongType;
import model.dataStructures.IDictionary;
import model.dataTypes.IType;
import model.dataTypes.IntType;
import model.statements.IStatement;
import model.statements.NopStatement;
import model.values.IValue;
import model.values.IntValue;

public class NewLockStatement implements IStatement {
    private final String varName;

    public NewLockStatement(String varName) {
        this.varName = varName;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        IDictionary<String, IValue> symTable = state.getSymTable();
        if (symTable.isDefined(varName)) {
            IValue varval = symTable.lookup(varName);
            if (!(varval.getType().equals(IntType.T)))
                throw new WrongType("Identifier", varval.getType(), IntType.T);
            int newEntry = state.getLockTable().allocate(-1);
            symTable.update(varName, new IntValue(newEntry));
        }
        else{
            int newEntry = state.getLockTable().allocate(-1);
            symTable.add(varName, new IntValue(newEntry));
        }
        return null;
    }

    @Override
    public IStatement deep_copy() {
        return new NewLockStatement(varName);
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnv) throws Exception {
        if(typeEnv.isDefined(varName)) {
            IType typeVar = typeEnv.lookup(varName);
            if (!(typeVar instanceof IntType))
                throw new TypeCheckError(varName + " is not of type integer");
        }
        else{
            typeEnv.add(varName, IntType.T);
        }
        return typeEnv;
    }

    public String toString() {
        return "newLock(" + varName + ")";
    }
}
