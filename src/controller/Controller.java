package controller;

import model.adt.IExeStack;
import model.adt.IHeap;
import model.exception.ControllerException;
import model.exception.RepoException;
import model.exception.StackException;
import model.exception.StmtException;
import model.state.PrgState;
import model.statement.IStmt;
import model.value.IValue;
import model.value.RefValue;
import repository.IRepository;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Controller {
    private IRepository repository;
    private boolean displayFlag;

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

    public PrgState oneStep(PrgState prgState) throws ControllerException {
        IExeStack<IStmt> stack = prgState.getExeStack();
        try {
            IStmt crtStmt = stack.pop();
            try {
                return crtStmt.execute(prgState);
            } catch (StmtException e) {
                throw new ControllerException(e.getMessage());
            }
        } catch (StackException e) {
            throw new ControllerException("Execution stack is empty!\n");
        }
    }

    public void allStep() throws ControllerException {
        PrgState prgState = this.repository.getCrtState();
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
                    prgState.getHeap().setContent(unsafeGarbageCollector(getAddrFromSymTable(prgState.getSymTable().getContent().values()), prgState.getHeap().getContent()));
                    this.repository.logPrgState();
                } catch (RepoException re) {
                    throw new ControllerException(re.getMessage());
                }

            }
        }
    }

    // TODO make safe
    private Map<Integer, IValue> unsafeGarbageCollector(List<Integer> symTableAddr, Map<Integer, IValue> heap) {
        return heap.entrySet().stream().filter(e->symTableAddr.contains(e.getKey())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private List<Integer> getAddrFromSymTable(Collection<IValue> symTableValues) {
        return symTableValues.stream().filter(v->v instanceof RefValue).map(v->{RefValue v1 = (RefValue) v; return v1.getAddress();}).collect(Collectors.toList());
    }
}
