package model.expression;

import model.adt.IHeap;
import model.adt.ISymTable;
import model.exception.DictionaryException;
import model.exception.ExpressionException;
import model.value.IValue;

public class VarExp implements IExp {
    private String id;

    public VarExp(String id) { this.id = id; }

    @Override
    public IValue eval(ISymTable<String, IValue> tbl, IHeap heap) throws ExpressionException {
        try {
            return tbl.lookup(this.id);
        } catch (DictionaryException ex) {
            throw new ExpressionException("Variable not defined!\n");
        }
    }

    @Override
    public IExp deepCopy() {
        return new VarExp(this.id);
    }

    @Override
    public String toString() {
        return this.id;
    }
}
