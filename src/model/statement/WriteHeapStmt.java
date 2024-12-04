package model.statement;

import model.exception.DictionaryException;
import model.exception.ExpressionException;
import model.exception.StmtException;
import model.expression.IExp;
import model.state.PrgState;
import model.type.RefType;
import model.value.IValue;
import model.value.RefValue;

public class WriteHeapStmt implements IStmt {
    private String varName;
    private IExp exp;
    private RefType refType;

    public WriteHeapStmt(String varName, IExp exp) {
        this.varName = varName;
        this.exp = exp;
        this.refType = new RefType(null);
    }

    @Override
    public PrgState execute(PrgState state) throws StmtException {
        try {
            IValue value = state.getSymTable().lookup(this.varName);

            if (!value.getType().equals(this.refType)) {
                throw new StmtException("Variable is not a RefType\n");
            }

            RefValue refValue = (RefValue) value;

            Integer address = refValue.getAddress();

            if (!state.getHeap().contains(address)) {
                throw new StmtException("Address is not in Heap\n");
            }

            IValue res = this.exp.eval(state.getSymTable(), state.getHeap());

            if (!res.getType().equals(refValue.getLocationType())) {
                throw new StmtException("Expression type doesn't match the location type\n");
            }

            state.getHeap().set(address, res);

            return null;
        } catch (DictionaryException | ExpressionException e) {
            throw new StmtException(e.getMessage());
        }
    }

    @Override
    public IStmt deepCopy() {
        return new WriteHeapStmt(this.varName, this.exp.deepCopy());
    }

    @Override
    public String toString() {
        return "wh(" + this.varName + ", " + this.exp + ")";
    }
}
