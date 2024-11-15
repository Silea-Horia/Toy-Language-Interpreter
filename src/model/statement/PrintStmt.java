package model.statement;

import model.adt.IOutList;
import model.exception.ExpressionException;
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
        IOutList<IValue> list = state.getOut();
        try {
            list.add(this.exp.eval(state.getSymTable()));
        } catch (ExpressionException e) {
            throw new StmtException(e.getMessage());
        }
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
