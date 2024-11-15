package model.expression;

import model.adt.ISymTable;
import model.exception.ExpressionException;
import model.value.IValue;

public interface IExp {
    IValue eval(ISymTable<String, IValue> tbl) throws ExpressionException;
    IExp deepCopy();
}
