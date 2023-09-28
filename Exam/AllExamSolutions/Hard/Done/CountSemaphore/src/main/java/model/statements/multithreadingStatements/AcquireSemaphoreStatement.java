package model.statements.multithreadingStatements;

import javafx.util.Pair;
import model.ProgramState;
import model.customExceptions.InvalidAddress;
import model.customExceptions.TypeCheckError;
import model.customExceptions.VariableNameNotFound;
import model.customExceptions.WrongType;
import model.dataStructures.IDictionary;
import model.dataStructures.ISemaphore;
import model.dataTypes.IType;
import model.dataTypes.IntType;
import model.statements.IStatement;
import model.values.IValue;
import model.values.IntValue;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AcquireSemaphoreStatement implements IStatement {
    private final String varName;
    private static Lock lock = new ReentrantLock();

    public AcquireSemaphoreStatement(String varName) {
        this.varName = varName;

    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        IDictionary<String, IValue> symtable = state.getSymTable();
        ISemaphore semaphoreTable = state.getSemaphoreTable();
        if (!symtable.isDefined(varName))
            throw new VariableNameNotFound(varName);
        IValue val = symtable.lookup(varName);
        if (!(val.getType().equals(IntType.T)))
            throw new WrongType("Identifier", val.getType(), IntType.T);
        Integer address = ((IntValue)val).getWrappedValue();
        if(!semaphoreTable.isDefined(address))
            throw new InvalidAddress(varName);
        Pair<Integer, List<Integer>> semTableEntry = semaphoreTable.lookup(address);
        lock.lock();
        List<Integer> idList = semTableEntry.getValue();
        Integer listLen = idList.size();
        if(semTableEntry.getKey() > listLen) {
            if(!(idList.contains(state.getId()))) {
                idList.add(state.getId());
            }
        }
        else {
            state.getExecutionStack().push(new AcquireSemaphoreStatement(varName));
        }
        lock.unlock();
        return null;
    }

    @Override
    public IStatement deep_copy() {
        return new AcquireSemaphoreStatement(varName);
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnv) throws Exception {
        IType typeVar = typeEnv.lookup(varName);
        if (!(typeVar instanceof IntType))
            throw new TypeCheckError(varName + " is not of type integer");
        return typeEnv;
    }

    public String toString() {
        return "acquire(" + varName + ")";
    }
}
