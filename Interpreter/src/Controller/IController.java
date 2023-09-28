package Controller;

import Model.PrgState;
import Repo.IRepo;

import java.util.List;

public interface IController {
    IRepo getRepo();
    void allSteps() throws Exception, ControllerException;
    void addToRepo(PrgState state);
    void clearRepo();
    List<PrgState> removeCompletedPrg(List<PrgState> inPrgList);
    void oneStepForAllPrg(List<PrgState> prgList) throws InterruptedException, RuntimeException;
}
