package model.statement;

import model.adt.IMyList;
import model.exception.StmtException;
import model.expression.IExp;
import model.state.PrgState;
import model.value.IValue;

public class PrintStmt implements IStmt {
    IExp exp;

    public PrintStmt(IExp exp) {
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws StmtException {
        IMyList<IValue> list = state.getOut();
        list.add(this.exp.eval(state.getSymTable()));
        return state;
    }

    @Override
    public String toString() {
        return "print(" + this.exp + ")";
    }

    @Override
    public IStmt deepCopy() {
        return new PrintStmt(this.exp.deepCopy());
    }
}
