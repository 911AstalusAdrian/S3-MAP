package model.statements;

import javafx.util.Pair;
import model.ProgramState;
import model.customExceptions.ProcedureError;
import model.customExceptions.ProcedureNotFound;
import model.dataStructures.IDictionary;
import model.dataStructures.IHeap;
import model.dataStructures.MyDictionary;
import model.dataTypes.IType;
import model.expressions.IExpression;
import model.statements.multithreadingStatements.ReturnFromProcedureStatement;
import model.values.IValue;

import java.util.List;

public class CallProcedureStatement implements IStatement{
    private final String procName;
    private final List<IExpression> arguments;

    public CallProcedureStatement(String procName, List<IExpression> arguments) {
        this.procName = procName;
        this.arguments = arguments;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        IDictionary<String, Pair<List<String>, IStatement>> procTable = state.getProcTable();
        if(!procTable.isDefined(procName)){
            throw new ProcedureNotFound(procName);
        }
        List<String> formalParams = procTable.lookup(procName).getKey();
        IDictionary<String, IValue> symTable = state.getTopSymTable();
        IHeap<IValue> heap = state.getHeap();
        IDictionary<String, IValue> newSymTable = new MyDictionary<>();
        if(!(arguments.size() == formalParams.size())){
            throw new ProcedureError(procName+": mismatched parameters");
        }
        for(int i = 0; i < arguments.size(); i++){
            IValue vi = arguments.get(i).evaluate(symTable, heap);
            newSymTable.add(formalParams.get(i), vi);
        }
        state.getSymTableStack().push(newSymTable);
        state.getExecutionStack().push(new ReturnFromProcedureStatement());
        state.getExecutionStack().push(procTable.lookup(procName).getValue());
        return null;
    }

    @Override
    public IStatement deep_copy() {
        return new CallProcedureStatement(procName, arguments);
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnv) throws Exception {
        return typeEnv;
    }

    @Override
    public String toString(){
        return procName + "(" + arguments.stream().map(IExpression::toString).reduce((acc, exp)->(acc+","+exp)).get()+")" ;
    }
}
