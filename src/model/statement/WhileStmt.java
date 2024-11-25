package model.statement;

import model.exception.ExpressionException;
import model.exception.StmtException;
import model.expression.IExp;
import model.state.PrgState;
import model.type.BoolType;
import model.value.BoolValue;
import model.value.IValue;

public class WhileStmt implements IStmt {
    private IExp condition;
    private IStmt command;
    private BoolType boolType;

    public WhileStmt(IExp condition, IStmt command) {
        this.condition = condition;
        this.command = command;
        this.boolType = new BoolType();
    }

    @Override
    public PrgState execute(PrgState state) throws StmtException {
        try {
            IValue eval = this.condition.eval(state.getSymTable(), state.getHeap());

            if (!eval.getType().equals(this.boolType)) {
                throw new StmtException("Condition is not a bool type\n");
            }

            if (((BoolValue)eval).getValue()) {
                state.getExeStack().push(this);
                state.getExeStack().push(this.command);
            }

            return null;
        } catch (ExpressionException e) {
            throw new StmtException(e.getMessage());
        }
    }

    @Override
    public IStmt deepCopy() {
        return new WhileStmt(this.condition.deepCopy(), this.command.deepCopy());
    }

    @Override
    public String toString() {
        return "While(" + this.condition + ") " + this.command;
    }
}
