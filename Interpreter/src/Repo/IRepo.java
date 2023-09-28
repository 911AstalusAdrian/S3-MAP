package Repo;

import Model.PrgState;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface IRepo {


    void addState(PrgState newState);
    void emptyRepo();
    void logPrgStateExec(PrgState state) throws RepoException;
    void emptyFile() throws RepoException;
    List<PrgState> getPrgList();
    void setPrgList(List<PrgState> newList);
    Integer getPrgStateCount();
    ArrayList<Integer> getPrgStateIDs();
}
