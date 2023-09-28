package Model.Statements;

import Exceptions.MyException;
import Model.ADT.MyIBarrierTable;
import Model.ADT.MyIDictionary;
import Model.ADT.MyIHeap;
import Model.ProgramState;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.IntValue;
import Model.Values.Value;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AwaitStatement implements IStatement {
    private final String varName;
    private static final Lock lock = new ReentrantLock();

    public AwaitStatement(String varName) {
        this.varName = varName;
    }
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        lock.lock();
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIHeap heap = state.getHeap();
        MyIBarrierTable barrierTable = state.getBarrierTable();
        if (symTable.isDefined(varName)) {
            IntValue f = (IntValue) symTable.getValue(varName);
            int foundIndex = f.getValue();
            if (barrierTable.containsKey(foundIndex)) {
                Pair<Integer, List<Integer>> foundBarrier = barrierTable.get(foundIndex);
                int NL = foundBarrier.getValue().size();
                int N1 = foundBarrier.getKey();
                ArrayList<Integer> list = (ArrayList<Integer>) foundBarrier.getValue();
                if (N1 > NL) {
                    if (list.contains(state.getId()))
                        state.getExecutionStack().push(this);
                    else {
                        list.add(state.getId());
                        barrierTable.update(foundIndex, new Pair<>(N1, list));
                        state.getExecutionStack().push(new AwaitStatement(varName));
                    }
                }
            } else {
                throw new MyException("Index not in Barrier Table!");
            }
        } else {
            throw new MyException("Var is not defined!");
        }
        lock.unlock();
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new AwaitStatement(varName);
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        if (typeEnv.getValue(varName).equals(new IntType()))
            return typeEnv;
        else
            throw new MyException("Var is not of type int!");
    }

    @Override
    public String toString() {
        return String.format("await(%s)", varName);
    }
}
