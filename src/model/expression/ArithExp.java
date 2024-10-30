package model.expression;

import model.adt.IMyDictionary;
import model.exception.ExpressionException;
import model.type.IntType;
import model.value.IValue;
import model.value.IntValue;

public class ArithExp implements IExp {
    private IExp e1;
    private IExp e2;
    private char op; // 1 - addition, 2 - subtraction, 3 - multiplication, 4 - division

    public ArithExp(IExp e1, IExp e2, char op) {
        this.e1 = e1;
        this.e2 = e2;
        this.op = op;
    }

    @Override
    public IValue eval(IMyDictionary<String, IValue> tbl) throws ExpressionException {
        IValue v1, v2;
        v1 = this.e1.eval(tbl);
        if (v1.getType().equals(new IntType())) {
            v2 = this.e2.eval(tbl);
            if (v2.getType().equals(new IntType())) {
                int n1 = ((IntValue)v1).getVal();
                int n2 = ((IntValue)v2).getVal();
                switch (this.op) {
                    case '+':
                        return new IntValue(n1 + n2);
                    case '-':
                        return new IntValue(n1 - n2);
                    case '*':
                        return new IntValue(n1 * n2);
                    case '/':
                        if (n2 == 0) throw new ExpressionException("Division by zero!\n");
                        else return new IntValue(n1 / n2);
                }
            } else throw new ExpressionException("Second operand is not an integer!\n");
        }
        throw new ExpressionException("First operand is not an integer!\n");
    }

    @Override
    public IExp deepCopy() {
        return new ArithExp(this.e1.deepCopy(), this.e2.deepCopy(), this.op);
    }

    @Override
    public String toString() {
        switch (this.op) {
            case '+':
                return this.e1 + "+" + this.e2;
            case '-':
                return this.e1 + "-" + this.e2;
            case '*':
                return this.e1 + "*" + this.e2;
            default:
                return this.e1 + "/" + this.e2;
        }
    }
}
