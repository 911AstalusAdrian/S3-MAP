package Repository;

import Exceptions.MyException;
import Model.ProgramState;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository{
    private List<ProgramState> programStates;
    private final String logFilePath;

    public Repository(ProgramState initialProgramState, String logFilePath) {
        this.programStates = new ArrayList<>();
        programStates.add(initialProgramState);
        this.logFilePath = logFilePath;
    }

    @Override
    public List<ProgramState> getProgramList() {
        return programStates;
    }

    @Override
    public void setProgramStates(List<ProgramState> programStates) {
        this.programStates = programStates;
    }

    @Override
    public void logProgramStateExecution(ProgramState programState) throws IOException {
        PrintWriter logFile;
        logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
        logFile.println(programState);
        logFile.close();
    }

    @Override
    public void setPrgStatesList(List<ProgramState> programStates) {
        this.programStates = programStates;
    }


    @Override
    public void emptyLogFile() throws IOException {
        PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, false)));
        logFile.close();
    }

    @Override
    public void addProgram(ProgramState program) {
        programStates.add(program);
    }
}
