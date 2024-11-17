package model.type;

import model.value.IValue;
import model.value.RefValue;

public class RefType implements IType {
    private IType inner;

    public RefType(IType inner) { this.inner = inner; }

    public IType getInner() { return inner; }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof RefType) {
            return inner.equals(((RefType) obj).inner);
        }
        return false;
    }

    @Override
    public String toString() {
        return "Ref(" + this.inner + ')';
    }

    @Override
    public IValue getDefaultValue() {
        return new RefValue(0, this.inner);
    }
}
