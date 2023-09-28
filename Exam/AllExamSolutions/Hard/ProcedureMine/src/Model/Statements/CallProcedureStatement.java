package Model.Statements;

import Exceptions.MyException;
import Model.ADT.MyDict;
import Model.ADT.MyIDictionary;
import Model.ADT.MyIHeap;
import Model.ADT.MyIProcedureTable;
import Model.Expressions.IExpression;
import Model.ProgramState;
import Model.Types.Type;
import Model.Values.Value;

import java.util.List;

public class CallProcedureStatement implements IStatement {
    private final String procedureName;
    private final List<IExpression> expressions;

    public CallProcedureStatement(String procedureName, List<IExpression> expressions) {
        this.procedureName = procedureName;
        this.expressions = expressions;
    }
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIDictionary<String, Value> symTable = state.getTopSymTable();
        MyIHeap heap = state.getHeap();
        MyIProcedureTable procTable = state.getProcedureTable();
        if (procTable.isDefined(procedureName)) {
            List<String> variables = procTable.getValue(procedureName).getKey();
            IStatement statement = procTable.getValue(procedureName).getValue();
            MyIDictionary<String, Value> newSymTable = new MyDict<>();
            for(String var: variables) {
                int ind = variables.indexOf(var);
                newSymTable.put(var, expressions.get(ind).eval(symTable, heap));
            }
            state.getSymTable().push(newSymTable);
            state.getExecutionStack().push(new ReturnStatement());
            state.getExecutionStack().push(statement);
        } else {
            throw new MyException("Procedure not defined!");
        }
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }

    @Override
    public IStatement deepCopy() {
        return new CallProcedureStatement(procedureName, expressions);
    }

    @Override
    public String toString() {
        return String.format("call %s%s", procedureName, expressions.toString());
    }
}
