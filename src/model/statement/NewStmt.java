package model.statement;

import model.adt.Heap;
import model.adt.IHeap;
import model.exception.DictionaryException;
import model.exception.ExpressionException;
import model.exception.StmtException;
import model.expression.IExp;
import model.state.PrgState;
import model.type.RefType;
import model.value.IValue;
import model.value.RefValue;

public class NewStmt implements IStmt {
    private String varName;
    private IExp exp;
    private RefType refType;

    public NewStmt(String varName, IExp exp) {
        this.varName = varName;
        this.exp = exp;
        this.refType = new RefType(null);
    }

    @Override
    public PrgState execute(PrgState state) throws StmtException {
        try {
            IValue value = state.getSymTable().lookup(this.varName);

            if (!value.getType().equals(this.refType)) {
                throw new StmtException("Variable is not a ref type\n");
            }

            IValue res = this.exp.eval(state.getSymTable());

            if (!((RefValue)value).getLocationType().equals(res.getType())) {
                throw new StmtException("The expression type is different from the reference type\n");
            }

            int address = state.getHeap().allocate(res);

            state.getSymTable().insert(this.varName, new RefValue(address, ((RefValue)value).getLocationType()));

            return state;
        } catch (DictionaryException | ExpressionException e) {
            throw new StmtException(e.getMessage());
        }

    }

    @Override
    public IStmt deepCopy() {
        return new NewStmt(this.varName, this.exp.deepCopy());
    }

    @Override
    public String toString() {
        return "new(" + this.varName + ", " + this.exp + ')';
    }
}
