package model.expression;

import model.adt.IMyDictionary;
import model.exception.ExpressionException;
import model.value.IValue;

public class ValueExp implements IExp{
    private IValue e;

    public ValueExp(IValue e) { this.e = e; }

    @Override
    public IValue eval(IMyDictionary<String, IValue> tbl) throws ExpressionException { return this.e; }

    @Override
    public IExp deepCopy() {
        return new ValueExp(this.e);
    }

    @Override
    public String toString() {
        return this.e.toString();
    }
}
