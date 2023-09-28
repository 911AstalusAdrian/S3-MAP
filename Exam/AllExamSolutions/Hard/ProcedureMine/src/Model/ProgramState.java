package Model;

import Exceptions.MyException;
import Model.ADT.*;
import Model.Statements.IStatement;
import Model.Values.Value;

import java.io.BufferedReader;
import java.util.List;
import java.util.Map;

public class ProgramState {
    private final MyIStack<IStatement> executionStack;
    private final MyIStack<MyIDictionary<String, Value>> symTable;
    private final MyIList<String> out;
    private final MyIDictionary<String, BufferedReader> fileTable;
    private final MyIHeap heap;
    private final MyIProcedureTable procedureTable;
    private int id;
    private static int lastID = 0;

    public ProgramState(IStatement originalProgram, MyIProcedureTable procedureTable) {
        executionStack = new MyStack<>();
        symTable = new MyStack<>();
        symTable.push(new MyDict<>());
        out = new MyList<>();
        executionStack.push(originalProgram);
        heap = new MyHeap();
        fileTable = new MyDict<>();
        this.procedureTable = procedureTable;
        id = setId();
    }

    public ProgramState(IStatement originalProgram) {
        executionStack = new MyStack<>();
        symTable = new MyStack<>();
        symTable.push(new MyDict<>());
        out = new MyList<>();
        executionStack.push(originalProgram);
        heap = new MyHeap();
        fileTable = new MyDict<>();
        procedureTable = new MyProcedureTable();
        id = setId();
    }

    public ProgramState(MyIStack<IStatement> executionStack,
                        MyIStack<MyIDictionary<String, Value>> symTable,
                        MyIList<String> out,
                        MyIDictionary<String, BufferedReader> fileTable,
                        MyIHeap heap,
                        MyIProcedureTable procedureTable) {
        this.executionStack = executionStack;
        this.symTable = symTable;
        this.out = out;
        this.heap = heap;
        this.fileTable = fileTable;
        this.procedureTable = procedureTable;
        id = setId();
    }

    public MyIHeap getHeap() {
        return heap;
    }

    public MyIProcedureTable getProcedureTable() { return procedureTable; }

    private synchronized int setId() {
        lastID++;
        return lastID;
    }

    public MyIStack<IStatement> getExecutionStack() {
        return executionStack;
    }

    public MyIStack<MyIDictionary<String, Value>> getSymTable() {
        return symTable;
    }

    public MyIDictionary<String, Value> getTopSymTable() {
        try {
            return symTable.peek();
        } catch (MyException e) {
            e.printStackTrace();
            return null;
        }
    }

    public MyIList<String> getOut() {
        return out;
    }

    public MyIDictionary<String, BufferedReader> getFileTable() {
        return fileTable;
    }

    public Integer getId() { return id; }

    public String executionStackToString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (IStatement statement : executionStack) {
            stringBuilder.append("[\n").append(statement.toString()).append("\n]\n");
        }
        return stringBuilder.toString();
    }

    public String symbolTableToString() throws MyException {
        StringBuilder symTableStringBuilder = new StringBuilder();
        List<MyIDictionary<String, Value>> stack = symTable.getReversed();
        for (MyIDictionary<String, Value> table: stack) {
            for (String key: table.keySet()) {
                symTableStringBuilder.append(String.format("%s -> %s\n", key, table.getValue(key).toString()));
            }
            symTableStringBuilder.append("\n");
        }
        return symTableStringBuilder.toString();
    }

    public String outToString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String item : out) {
            stringBuilder.append(item).append("\n");
        }
        return stringBuilder.toString();
    }

    public String fileTableToString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String key : fileTable.keySet()) {
            stringBuilder.append(key).append("\n");
        }
        return stringBuilder.toString();
    }

    public ProgramState oneStep() throws MyException {
        if (executionStack.isEmpty())
            throw new MyException("Program state stack is empty.");
        IStatement currentStatement = executionStack.pop();
        return currentStatement.execute(this);
    }

    public String heapToString() {
        StringBuilder outStringBuilder = new StringBuilder();
        Map<Integer, Value> map = heap.getContent();
        for (Integer key : map.keySet())
            outStringBuilder.append(key).append(" -> ").append(map.get(key)).append("\n");
        return outStringBuilder.toString();
    }

    public String procedureTableToString() throws MyException {
        StringBuilder procTableStringBuilder = new StringBuilder();
        for (String key: procedureTable.keySet()) {
            procTableStringBuilder.append(String.format("%s -> Params: %s, Statement: %s\n", key, procedureTable.getValue(key).getKey(), procedureTable.getValue(key).getValue()));
        }
        return procTableStringBuilder.toString();
    }

    public boolean isNotCompleted() {
        return !executionStack.isEmpty();
    }

    @Override
    public String toString() {
        try {
            return "ID: " + id + "\nExecution stack: " + executionStackToString() + "\nSymbol table: " + symbolTableToString() + "\nOutput list: " + outToString()
                    + "\nFile table: " + fileTableToString() + "\nHeap table: " + heapToString() + "\nProcedure table: " + procedureTableToString() + "\n----------------------------------------------------\n";
        } catch (MyException e) {
            e.printStackTrace();
        }
        return "";
    }

}
