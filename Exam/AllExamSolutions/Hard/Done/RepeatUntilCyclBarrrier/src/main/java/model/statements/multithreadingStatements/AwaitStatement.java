package model.statements.multithreadingStatements;

import javafx.util.Pair;
import model.ProgramState;
import model.customExceptions.InvalidAddress;
import model.customExceptions.TypeCheckError;
import model.customExceptions.VariableNameNotFound;
import model.customExceptions.WrongType;
import model.dataStructures.IBarrierTable;
import model.dataStructures.IDictionary;
import model.dataStructures.IHeap;
import model.dataTypes.IType;
import model.dataTypes.IntType;
import model.statements.IStatement;
import model.values.IValue;
import model.values.IntValue;

import java.lang.reflect.AnnotatedWildcardType;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AwaitStatement implements IStatement {
    private final String varName;
    private static Lock lock = new ReentrantLock();

    public AwaitStatement(String varName) {
        this.varName = varName;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        IDictionary<String, IValue> symtable = state.getSymTable();
        IBarrierTable barrierTable = state.getBarrierTable();
        if (!symtable.isDefined(varName))
            throw new VariableNameNotFound(varName);
        IValue val = symtable.lookup(varName);
        if (!(val.getType().equals(IntType.T)))
            throw new WrongType("Identifier", val.getType(), IntType.T);
        Integer address = ((IntValue)val).getWrappedValue();
        if(!barrierTable.isDefined(address))
            throw new InvalidAddress(varName);
        Pair<Integer, List<Integer>> barTableEntry = barrierTable.lookup(address);
        lock.lock();
        List<Integer> idList = barTableEntry.getValue();
        Integer listLen = idList.size();
        if(barTableEntry.getKey() > listLen) {
            if(!(idList.contains(state.getId()))) {
                idList.add(state.getId());
            }
            state.getExecutionStack().push(new AwaitStatement(varName));
        }
        lock.unlock();
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
