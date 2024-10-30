package model.statement;

import model.exception.StmtException;
import model.state.PrgState;

public interface IStmt {
    PrgState execute(PrgState state) throws StmtException;
    IStmt deepCopy();
}
