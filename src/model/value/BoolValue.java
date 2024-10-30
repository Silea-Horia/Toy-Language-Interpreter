package model.value;

import model.type.BoolType;
import model.type.IType;

public class BoolValue implements IValue{
    private boolean val;

    public BoolValue(boolean val) { this.val = val; }

    public boolean getVal() { return this.val; }

    @Override
    public String toString() { return Boolean.toString(this.val); }

    @Override
    public IType getType() { return new BoolType(); }
}
