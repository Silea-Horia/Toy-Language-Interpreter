package model.expression;

import model.adt.ISymTable;
import model.exception.ExpressionException;
import model.type.IntType;
import model.value.BoolValue;
import model.value.IValue;
import model.value.IntValue;

public class RelationalExp implements IExp {
    private IExp left;
    private IExp right;
    private RelationalOperation relation;

    public RelationalExp(IExp left, IExp right, RelationalOperation relation) {
        this.left = left;
        this.right = right;
        this.relation = relation;
    }

    @Override
    public IValue eval(ISymTable<String, IValue> tbl) throws ExpressionException {
        try {
            IValue leftVal = left.eval(tbl);

            if (!leftVal.getType().equals(new IntType())) {
                throw new ExpressionException("Left operand is not an Int type\n");
            }

            IValue rightVal = right.eval(tbl);

            if (!rightVal.getType().equals(new IntType())) {
                throw new ExpressionException("Right operand is not an Int type\n");
            }

            switch (this.relation) {
                case SMALLER -> {
                    return new BoolValue(((IntValue)leftVal).getValue() < ((IntValue)rightVal).getValue());
                }

                case SMALLEROREQUAL -> {
                    return new BoolValue(((IntValue)leftVal).getValue() <= ((IntValue)rightVal).getValue());
                }

                case GREATER -> {
                    return new BoolValue(((IntValue)leftVal).getValue() > ((IntValue)rightVal).getValue());
                }

                case GREATEROREQUAL -> {
                    return new BoolValue(((IntValue)leftVal).getValue() >= ((IntValue)rightVal).getValue());
                }

                case EQUAL -> {
                    return new BoolValue(((IntValue)leftVal).getValue() == ((IntValue)rightVal).getValue());
                }

                case NOTEQUAL -> {
                    return new BoolValue(((IntValue)leftVal).getValue() != ((IntValue)rightVal).getValue());
                }

                default -> throw new ExpressionException("Invalid relation\n");
            }

        } catch (ExpressionException e) {
            throw new ExpressionException(e.getMessage());
        }
    }

    @Override
    public IExp deepCopy() {
        return new RelationalExp(left.deepCopy(), right.deepCopy(), relation);
    }
}
