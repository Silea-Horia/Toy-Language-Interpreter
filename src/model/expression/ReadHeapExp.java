package model.expression;

import model.adt.IHeap;
import model.adt.ISymTable;
import model.exception.DictionaryException;
import model.exception.ExpressionException;
import model.type.RefType;
import model.value.IValue;
import model.value.RefValue;

public class ReadHeapExp implements IExp {
    private IExp exp;
    private RefType refType;

    public ReadHeapExp(IExp exp) {
        this.exp = exp;
        this.refType = new RefType(null);
    }

    @Override
    public IValue eval(ISymTable<String, IValue> tbl, IHeap heap) throws ExpressionException {
        IValue res = this.exp.eval(tbl, heap);

        if (!res.getType().equals(this.refType)) {
            throw new ExpressionException("Expression is not a ref type\n");
        }

        RefValue refValue = (RefValue) res;

        Integer key = refValue.getAddress();

        try {
            return heap.getValue(key);
        } catch (DictionaryException e) {
            throw new ExpressionException(e.getMessage());
        }
    }

    @Override
    public IExp deepCopy() {
        return new ReadHeapExp(this.exp.deepCopy());
    }

    @Override
    public String toString() {
        return "rH(" + this.exp + ')';
    }
}
