package model.expression;

import model.adt.IMyDictionary;
import model.exception.ExpressionException;
import model.type.BoolType;
import model.value.BoolValue;
import model.value.IValue;

public class LogicExp implements IExp{
    private IExp e1;
    private IExp e2;
    private int op; // 1 - and, 2 - or

    public LogicExp(IExp e1, IExp e2, int op) {
        this.e1 = e1;
        this.e2 = e2;
        this.op = op;
    }

    @Override
    public IValue eval(IMyDictionary<String, IValue> tbl) throws ExpressionException {
        IValue v1, v2;
        v1 = this.e1.eval(tbl);
        if (v1.getType().equals(new BoolType())) {
            v2 = this.e2.eval(tbl);
            if (v2.getType().equals(new BoolType())) {
                if (this.op == 1) return new BoolValue(((BoolValue)v1).getVal() && ((BoolValue)v2).getVal());
                else return new BoolValue(((BoolValue)v1).getVal() || ((BoolValue)v2).getVal()  );
            } else throw new ExpressionException("Second operand is not boolean!\n");
        }
        throw new ExpressionException("First operand is not boolean!\n");
    }

    @Override
    public IExp deepCopy() {
        return new LogicExp(this.e1.deepCopy(), this.e2.deepCopy(), this.op);
    }
}
