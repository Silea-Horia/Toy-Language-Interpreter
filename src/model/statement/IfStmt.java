package model.statement;

import model.adt.IMyStack;
import model.exception.StmtException;
import model.expression.IExp;
import model.state.PrgState;
import model.type.BoolType;
import model.value.BoolValue;
import model.value.IValue;

public class IfStmt implements IStmt {
    private IExp exp;
    private IStmt thenS;
    private IStmt elseS;

    public IfStmt(IExp exp, IStmt thenS, IStmt elseS) {
        this.exp = exp;
        this.thenS = thenS;
        this.elseS = elseS;
    }

    @Override
    public String toString() {
        return "IF(" + this.exp + ") THEN(" + this.thenS + ") ELSE(" + this.elseS + ")";
    }

    @Override
    public PrgState execute(PrgState state) throws StmtException {
        IMyStack<IStmt> stack = state.getExeStack();
        IValue cond = this.exp.eval(state.getSymTable());
        if (!cond.getType().equals(new BoolType())) throw new StmtException("Conditional expression is not a boolean!|n");
        if (((BoolValue)cond).getVal()) stack.push(this.thenS);
        else stack.push(this.elseS);
        return state;
    }
    @Override
    public IStmt deepCopy() {
        return new IfStmt(this.exp.deepCopy(), this.thenS.deepCopy(), this.elseS.deepCopy());
    }
}
