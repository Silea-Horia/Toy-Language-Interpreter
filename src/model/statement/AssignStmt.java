package model.statement;

import model.adt.IMyDictionary;
import model.adt.IMyStack;
import model.exception.DictionaryException;
import model.exception.ExpressionException;
import model.exception.StmtException;
import model.expression.IExp;
import model.state.PrgState;
import model.type.IType;
import model.value.IValue;

public class AssignStmt implements IStmt {
    private String id;
    private IExp exp;

    public AssignStmt(String id, IExp exp) {
        this.id = id;
        this.exp = exp;
    }

    @Override
    public String toString() {
        return this.id + "=" + this.exp;
    }

    @Override
    public PrgState execute(PrgState state) throws StmtException {
        IMyStack<IStmt> stack = state.getExeStack();
        IMyDictionary<String, IValue> tbl = state.getSymTable();
        IType typeId;
        if (tbl.contains(this.id)) {
            IValue val = null;
            try {
                val = this.exp.eval(tbl);
            } catch (ExpressionException e) {
                throw new StmtException(e.getMessage());
            }
            try {
                typeId = (tbl.lookup(this.id)).getType();
                if (val.getType().equals(typeId)) {
                    tbl.insert(this.id, val);
                } else {
                    throw new StmtException("Declared type of variable " + this.id + " doesn't match the type of the assigned expression!\n");
                }
            } catch (DictionaryException _) {
                throw new StmtException("WTF?!\n");
            }
        } else {
            throw new StmtException("The used variable " + this.id + " was not declared before usage!\n");
        }
        return state;
    }

    @Override
    public IStmt deepCopy() {
        return new AssignStmt(this.id, this.exp.deepCopy());
    }
}
