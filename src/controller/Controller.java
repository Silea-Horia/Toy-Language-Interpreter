package controller;

import model.adt.IHeap;
import model.exception.*;
import model.state.PrgState;
import model.value.IValue;
import model.value.RefValue;
import repository.IRepository;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller {
    private IRepository repository;
    private boolean displayFlag;
    private ExecutorService executor;

    public Controller(IRepository repository) {
        this.repository = repository;
        this.displayFlag = true;
    }

    public Controller(IRepository repository, boolean displayFlag) {
        this.repository = repository;
        this.displayFlag = displayFlag;
    }

    public void setDisplayFlag(boolean displayFlag) {
        this.displayFlag = displayFlag;
    }

    public boolean getDisplayFlag() {
        return this.displayFlag;
    }

    public void generateInitialState(int option) {
        this.repository.setState(option);
    }

    private void printAll(List<PrgState> prgStateList) {
        prgStateList.forEach(prg -> {
            try {
                this.repository.logPrgState(prg);
            } catch (RepoException e) {
                System.out.println(e.getMessage());
                System.exit(1);
            }
        });
    }

    public void allStep() throws ControllerException {
        this.executor = Executors.newFixedThreadPool(2);

        List<PrgState> prgStateList = this.removeCompletedPrg(this.repository.getPrgList());
        this.printAll(prgStateList);
        while (!prgStateList.isEmpty()) {
            this.oneStepForAll(prgStateList);

            prgStateList.forEach( prgState ->
                    prgState.getHeap().setContent(
                            safeGarbageCollector(getAddrFromSymTable(prgState.getSymTable().getContent().values(),
                                    prgState.getHeap()), prgState.getHeap().getContent())));

            this.printAll(prgStateList);
            prgStateList = this.removeCompletedPrg(this.repository.getPrgList());
        }

        this.executor.shutdownNow();

        this.repository.setPrgList(prgStateList);

        /*PrgState prgState = this.repository.getCrtState();
        if (this.displayFlag) {
            //System.out.println(prgState);
            try {
                this.repository.logPrgState();
            } catch (RepoException re) {
                throw new ControllerException(re.getMessage());
            }

        }
        while (!prgState.getExeStack().isEmpty()) {
            oneStep(prgState);
            if (this.displayFlag) {
                //System.out.println(prgState);
                try {
                    prgState.getHeap().setContent(
                            safeGarbageCollector(getAddrFromSymTable(prgState.getSymTable().getContent().values(), prgState.getHeap()), prgState.getHeap().getContent()));
                    this.repository.logPrgState();
                } catch (RepoException re) {
                    throw new ControllerException(re.getMessage());
                }

            }
        }*/
    }

    private Map<Integer, IValue> safeGarbageCollector(List<Integer> symTableAddr, Map<Integer, IValue> heap) {
        return heap.entrySet().stream().filter(e->symTableAddr.contains(e.getKey())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private List<Integer> getAddrFromSymTable(Collection<IValue> symTableValues, IHeap heap) {
//        return symTableValues.stream().filter(v->v instanceof RefValue)
//                .map(v->{RefValue v1 = (RefValue) v; return v1.getAddress();})
//                .collect(Collectors.toList());
        return symTableValues.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> (RefValue)v)
                .flatMap(
                        v->{
                            List<Integer> addresses = new ArrayList<>();
                            while (true) {
                                if (v.getAddress() == 0) {
                                    break;
                                }
                                addresses.add(v.getAddress());
                                try {
                                    IValue next = heap.getValue(v.getAddress());
                                    if (next instanceof RefValue) {
                                        v = (RefValue) next;
                                    } else break;
                                } catch (DictionaryException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                            return addresses.stream();
                        }
                ).collect(Collectors.toList());
    }

    private List<PrgState> removeCompletedPrg(List<PrgState> prgStateList) {
        return prgStateList.stream().filter(PrgState::isNotCompleted).collect(Collectors.toList());
    }

    public void oneStepForAll(List<PrgState> prgStateList) throws ControllerException {


        List<Callable<PrgState>> callList = prgStateList.stream().map((PrgState p) -> (Callable<PrgState>)(p::oneStep)).toList();

        try {
            List<PrgState> newPrgList = this.executor.invokeAll(callList).stream().map(future -> {try {return future.get();} catch (
                    InterruptedException | ExecutionException re) { System.out.println(re.getMessage()); System.exit(1);} return null;}).filter(Objects::nonNull).toList();

            prgStateList.addAll(newPrgList);

            //this.printAll(prgStateList);

            this.repository.setPrgList(prgStateList);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }


    }
}
