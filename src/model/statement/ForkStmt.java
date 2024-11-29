package model.statement;

import model.adt.ExeStack;
import model.exception.StmtException;
import model.state.PrgState;

public class ForkStmt implements IStmt {
    private IStmt stmt;

    public ForkStmt(IStmt stmt) {
        this.stmt = stmt;
    }

    @Override
    public PrgState execute(PrgState state) throws StmtException {
        ExeStack<IStmt> newStack = new ExeStack<>();
        newStack.push(this.stmt);

        return new PrgState(newStack, state.getSymTable(), state.getOut(), this.stmt, state.getFileTable(), state.getHeap());
    }

    @Override
    public IStmt deepCopy() {
        return null;
    }
}
