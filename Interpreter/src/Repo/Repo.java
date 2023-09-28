package Repo;

import Model.PrgState;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Repo implements IRepo{

    private List<PrgState> states;
    private final String logFilePath;


    public Repo(String filepath) throws IOException {
        states = new ArrayList<PrgState>();
        logFilePath = filepath;
    }

    @Override
    public void addState(PrgState newState) {states.add(newState);}

    @Override
    public List<PrgState> getPrgList() {
        return this.states;
    }

    @Override
    public void setPrgList(List<PrgState> newList) {
        this.states = newList;
    }

    @Override
    public void logPrgStateExec(PrgState state) throws RepoException {
        try {
            PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
            logFile.println(state.toString());
            logFile.close();
        }
        catch(IOException ex) {
            throw new RepoException("Could not write the log into the file!\n" + ex.toString());
        }
    }

    @Override
    public void emptyRepo() {states.clear();}

    @Override
    public void emptyFile() throws RepoException {
        try {
            PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, false)));
            logFile.flush();
            logFile.close();
        }
        catch (IOException ex){
            throw new RepoException("Could not empty the log file!\n" + ex.toString());
        }
    }

    @Override
    public Integer getPrgStateCount(){return states.size();}

    @Override
    public ArrayList<Integer> getPrgStateIDs() {
        ArrayList<Integer> stateIDs = new ArrayList<>();
        for(PrgState e: states)
            stateIDs.add(e.getID());
        return stateIDs;
    }
}

