package model.expression;

import model.adt.IDictionary;
import model.exception.ExpressionException;
import model.value.IValue;

public interface IExp {
    IValue eval(IDictionary<String, IValue> tbl) throws ExpressionException;
    IExp deepCopy();
}
