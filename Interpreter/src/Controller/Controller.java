package Controller;

import Model.PrgState;
import Model.States.MyIStack;
import Model.Stmts.IStmt;
import Model.Values.RefValue;
import Model.Values.Value;
import Repo.IRepo;
import Repo.RepoException;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller implements IController {
    private final IRepo repo;
    ExecutorService executor;

    public Controller(IRepo repository){repo=repository;}

    @Override
    public IRepo getRepo() {return repo;}

    @Override
    public void allSteps() throws ControllerException {
        try {
            repo.emptyFile();
        }
        catch (RepoException re){throw new ControllerException("Controller - " + re.toString());}
        executor = Executors.newFixedThreadPool(2);
        List<PrgState> prgList = removeCompletedPrg(repo.getPrgList());

        while(prgList.size() > 0){
            /// GARBAGE COLLECTOR
            prgList.get(0).getHeap().setHeapContent(unsafeGarbageCollector(getAddrFromSymTable(prgList.get(0).getTbl().getContent().values()),
                    prgList.get(0).getHeap().getHeapContent()));

            try {
                oneStepForAllPrg(prgList);
            }
            catch (Exception e){
                System.out.println("EXCEPTION CAUGHT");
            }
            prgList = removeCompletedPrg(repo.getPrgList());
        }

        executor.shutdownNow();
        repo.setPrgList(prgList);
    }

    @Override
    public void addToRepo(PrgState state) {
        this.repo.addState(state);
    }

    @Override
    public void clearRepo() {
        this.repo.emptyRepo();
    }

    @Override
    public List<PrgState> removeCompletedPrg(List<PrgState> inPrgList) {
        return inPrgList.stream()
                .filter(p -> p.isNotCompleted())
                .collect(Collectors.toList());
    }

    @Override
    public void oneStepForAllPrg(List<PrgState> prgList) throws RuntimeException, InterruptedException {
        executor=Executors.newFixedThreadPool(2);
        List<Callable<PrgState>> callList = prgList.stream()
                .map((PrgState p) -> (Callable<PrgState>)(p::oneStep))
                .collect(Collectors.toList());

        List<PrgState> newPrgList = executor.invokeAll(callList).stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (InterruptedException | ExecutionException e) {
                        throw new RuntimeException("Controller (1) - " + e.toString());
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        prgList.addAll(newPrgList);
        prgList.forEach(prg -> {
            try {
                repo.logPrgStateExec(prg);
            } catch (RepoException e) {
                throw new RuntimeException("Controller (2) - " + e.toString());
            }
        });
        repo.setPrgList(removeCompletedPrg(prgList));
    }

    Map<Integer, Value> unsafeGarbageCollector(List<Integer> symTableAddr, Map<Integer,Value> heap){
        return heap.entrySet().stream()
                .filter(e->(symTableAddr.contains(e.getKey()) || heap.containsKey(e.getKey())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    List<Integer> getAddrFromSymTable(Collection<Value> symTableValues){
        return symTableValues.stream()
                .filter(v-> v instanceof RefValue)
                .map(v-> {RefValue v1 = (RefValue)v; return v1.getAddr();})
                .collect(Collectors.toList());
    }
}
