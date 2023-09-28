package Controller;

import Exceptions.MyException;
import Model.ADT.MyIHeap;
import Model.ProgramState;
import Model.Values.ReferenceValue;
import Model.Values.Value;
import Repository.IRepository;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Controller {
    IRepository repository;
    private ExecutorService executor;
    boolean willPrint = false;

    public void setWillPrint(boolean value) {
        willPrint = value;
    }

    public Controller(IRepository repository) {
        this.repository = repository;
    }

    public Map<Integer, Value> garbageCollector(List<Integer> symTableAddr, List<Integer> heapAddr,Map<Integer, Value> heap) {
        return heap.entrySet().stream().
                filter(e->(symTableAddr.contains(e.getKey()) || heapAddr.contains(e.getKey())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public List<Integer> getAddrFromSymTable(Collection<Value> symTableValues) {
        return symTableValues.stream()
                .filter(v -> v instanceof ReferenceValue)
                .map(v -> {ReferenceValue v1 = (ReferenceValue) v; return v1.getAddress();})
                .collect(Collectors.toList());
    }

    public List<Integer> getAddrFromHeap(Collection<Value> heapValues) {
        return heapValues.stream()
                .filter(v -> v instanceof ReferenceValue)
                .map(v -> {ReferenceValue v1 = (ReferenceValue) v; return v1.getAddress();})
                .collect(Collectors.toList());
    }

    public List<ProgramState> removeCompletedProgram(List<ProgramState> programStateList) {
        return programStateList.stream().filter(p-> p.isNotCompleted()).collect(Collectors.toList());

    }

    public void oneStep() throws Exception {
        executor = Executors.newFixedThreadPool(2);
        List<ProgramState> programStates = removeCompletedProgram(repository.getProgramList());
        oneStepForAllPrograms(programStates);
        conservativeGarbageCollector(programStates);
        //programStates = removeCompletedPrg(repository.getProgramList());
        executor.shutdownNow();
        //repository.setProgramStates(programStates);
    }

    public void oneStepForAllPrograms(List<ProgramState> programStateList) throws InterruptedException, MyException {
//        programStateList.forEach(prg-> {
//            try {
//                repository.logProgramStateExecution(prg);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        });
        List<Callable<ProgramState>> callList = programStateList.stream()
                .map((ProgramState p) -> (Callable<ProgramState>) (p::oneStep))
                .collect(Collectors.toList());
        List<ProgramState> newProgramList = new ArrayList<>();
        for (Future<ProgramState> future : executor.invokeAll(callList)) {
            ProgramState programState = null;
            try {
                programState = future.get();
            } catch (InterruptedException | ExecutionException e) {
                System.out.println(e.getMessage());
                if (e.getCause() instanceof MyException) {
                    throw new MyException(e.getMessage());
                }
            }
            if (programState != null) {
                newProgramList.add(programState);
            }
        }
//        List<ProgramState> newProgramList = executor.invokeAll(callList).stream()
//                .map(future -> {
//                    try {
//                        return future.get();
//                    } catch (ExecutionException | InterruptedException e) {
//                        System.out.println(e.getMessage());
//                    }
//                    return null;
//                })
//                .filter(Objects::nonNull)
//                .collect(Collectors.toList());
        programStateList.addAll(newProgramList);
        programStateList.forEach(prg-> {
            try {
                repository.logProgramStateExecution(prg);
                System.out.println(prg.toString() + '\n');
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        repository.setPrgStatesList(programStateList);
    }

    public void setProgramStates(List<ProgramState> programStateList) {
        repository.setProgramStates(programStateList);
    }

    public void fullExecution() throws MyException, IOException, InterruptedException {
        executor = Executors.newFixedThreadPool(2);
        List<ProgramState> programStateList = removeCompletedProgram(repository.getProgramList());
        repository.emptyLogFile();
        while(!programStateList.isEmpty()) {
                conservativeGarbageCollector(programStateList);
                oneStepForAllPrograms(programStateList);
                programStateList = removeCompletedProgram(repository.getProgramList());
        }
        executor.shutdownNow();
        repository.setProgramStates(programStateList);
    }

    public void conservativeGarbageCollector(List<ProgramState> programStates) {
        List<Integer> symTableAddresses = Objects.requireNonNull(programStates.stream()
                        .map(p -> getAddrFromSymTable(p.getTopSymTable().values()))
                        .map(Collection::stream)
                        .reduce(Stream::concat).orElse(null))
                .collect(Collectors.toList());
        programStates.forEach(p -> {
            p.getHeap().setContent((HashMap<Integer, Value>) garbageCollector(symTableAddresses, getAddrFromHeap(p.getHeap().getContent().values()), p.getHeap().getContent()));
        });
    }

    public List<ProgramState> getProgramStates() {
        return repository.getProgramList();
    }

}
