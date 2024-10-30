package model.expression;

import model.adt.IMyDictionary;
import model.exception.ExpressionException;
import model.value.IValue;

public interface IExp {
    IValue eval(IMyDictionary<String, IValue> tbl) throws ExpressionException;
    IExp deepCopy();
}
