package model.statement;

import model.adt.IStack;
import model.exception.StmtException;
import model.state.PrgState;

public class CompStmt implements IStmt {
    private IStmt first;
    private IStmt second;

    public CompStmt(IStmt first, IStmt second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public PrgState execute(PrgState state) throws StmtException {
        IStack<IStmt> stack = state.getExeStack();
        stack.push(this.second);
        stack.push(this.first);
        return state;
    }

    @Override
    public String toString() {
        return "(" + this.first + "; " + this.second + ")";
    }

    @Override
    public IStmt deepCopy() {
        return new CompStmt(this.first.deepCopy(), this.second.deepCopy());
    }
}
