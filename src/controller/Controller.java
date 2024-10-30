package controller;

import model.adt.IMyStack;
import model.exception.ControllerException;
import model.exception.StackException;
import model.state.PrgState;
import model.statement.IStmt;
import repository.IRepository;

public class Controller {
    private IRepository repository;
    private boolean displayFlag;

    public Controller(IRepository repository) {
        this.repository = repository;
        this.displayFlag = false;
    }

    public Controller(IRepository repository, boolean displayFlag) {
        this.repository = repository;
        this.displayFlag = displayFlag;
    }

    public PrgState oneStep(PrgState prgState) throws ControllerException {
        IMyStack<IStmt> stack = prgState.getExeStack();
        try {
            IStmt crtStmt = stack.pop();
            return crtStmt.execute(prgState);
        } catch (StackException e) {
            throw new ControllerException("Execution stack is empty!\n");
        }
    }

    public void allStep() {
        PrgState prgState = repository.getCrtState();
        if (this.displayFlag) System.out.println(prgState);
        while (!prgState.getExeStack().isEmpty()) {
            oneStep(prgState);
            if (this.displayFlag) System.out.println(prgState);
        }
    }
}
