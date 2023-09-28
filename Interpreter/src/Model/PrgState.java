package Model;
import Controller.ControllerException;
import Model.States.*;
import Model.Stmts.IStmt;
import Model.Values.Value;

import java.io.BufferedReader;
import java.nio.Buffer;
import java.util.Dictionary;
import java.util.Random;
import java.util.TreeSet;

public class PrgState {
    MyIStack<IStmt> exeStack;
    MyIDictionary<String, Value> symTable;
    MyIDictionary<String, BufferedReader> fileTable;
    IHeap heap;
    MyIList<Value> out;
    ILatch latchTable;
    IStmt originalProgram;

    private static final TreeSet<Integer> ids = new TreeSet<>();
    Integer id;

    public PrgState(MyIStack<IStmt> stk, MyIDictionary<String, Value> symtbl, MyIList<Value> ot, IHeap hp, IStmt prg, ILatch lt){
        exeStack = stk;
        symTable = symtbl;
        out = ot;
        fileTable = new MyDictionary<>();
        heap = hp;
        stk.push(prg);
        id = newId();
        latchTable = lt;
    }

    public PrgState(IStmt prg){
        exeStack = new MyStack<IStmt>();
        symTable = new MyDictionary<>();
        fileTable = new MyDictionary<>();
        heap = new Heap();
        out = new MyList<>();
        exeStack.push(prg);
        id = newId();
        originalProgram = prg;
        latchTable = new MyLatch();
    }

    private static Integer newId() {
        Random random = new Random();
        Integer id;
        synchronized (ids) {
            do {
                id = random.nextInt(20);
            } while (ids.contains(id));
            ids.add(id);
        }
        return id;
    }

    // Getter for ILatch
    public ILatch getLatchTable(){return latchTable;}

    //Getters and setters for IStack
    public void setStk(MyIStack<IStmt> newStk){exeStack = newStk;}
    public MyIStack<IStmt> getStk(){return exeStack;}

    // Getters and setters for IDictionary
    public void setTbl(MyIDictionary <String,Value> newTbl){symTable = newTbl;}
    public MyIDictionary<String, Value> getTbl(){return symTable;}

    // Getters and setters for IList
    public void setOut(MyIList<Value> newOut){out = newOut;}
    public MyIList<Value> getOut(){return out;}

    // Getters and setters for fileTable
    public void setTable(MyIDictionary<String, BufferedReader> newTbl){fileTable = newTbl;}
    public MyIDictionary<String, BufferedReader> getFileTable(){return fileTable;}

    // Getters and setters for Heap
    public void setHeap(IHeap newHeap){heap = newHeap;}
    public IHeap getHeap(){return heap;}

    public Integer getID(){return id;}

    @Override
    public String toString(){
        String outString = "";
        outString = outString + "THREAD ID: " + id.toString() + '\n';
        outString = outString + "Execution stack: \n" + exeStack.toString();
        outString = outString + "Symbol table" + symTable.toString() + '\n';
        outString = outString + "Output: " + out.toString() + '\n';
        outString = outString + "FileTable: " + fileTable.toString() + '\n';
        outString = outString + "Heap: " + heap.toString() + '\n';
        outString = outString + "Latch Table: " + latchTable.toString() + '\n';
        outString = outString + "---------------------------------------------------";
        return outString;
    }

    public boolean isNotCompleted(){return !exeStack.isEmpty();}

    public PrgState oneStep() throws Exception{
    if(exeStack.isEmpty())
        throw new Exception("Program State Stack is Empty.;");
    IStmt currentStmt = exeStack.pop();
    return currentStmt.execute(this);
    }

}
