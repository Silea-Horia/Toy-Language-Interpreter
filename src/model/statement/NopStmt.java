package model.statement;

import model.exception.StmtException;
import model.state.PrgState;

public class NopStmt implements IStmt {

    @Override
    public String toString() {
        return "NOP";
    }

    @Override
    public PrgState execute(PrgState state) throws StmtException {
        return state;
    }

    @Override
    public IStmt deepCopy() {
        return new NopStmt();
    }
}
