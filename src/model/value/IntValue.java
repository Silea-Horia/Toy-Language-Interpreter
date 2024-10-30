package model.value;

import model.type.IType;
import model.type.IntType;

public class IntValue implements IValue {
    private int val;

    public IntValue(int val) { this.val = val; }

    public int getVal() { return val; }

    @Override
    public String toString() { return Integer.toString(this.val); }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public IType getType() { return new IntType(); }
}
