package model.statement;

import model.exception.DictionaryException;
import model.exception.ExpressionException;
import model.exception.StmtException;
import model.expression.IExp;
import model.state.PrgState;
import model.type.StringType;
import model.value.IValue;
import model.value.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseRFile implements IStmt {
    private IExp exp;
    private static StringType stringType;

    public CloseRFile(IExp exp) {
        this.exp = exp;
        stringType = new StringType();
    }

    @Override
    public PrgState execute(PrgState state) throws StmtException {
        try {
            IValue eval = this.exp.eval(state.getSymTable(), state.getHeap());

            if (!eval.getType().equals(stringType)) {
                throw new ExpressionException("Expression is not a string");
            }

            try (BufferedReader br = state.getFileTable().lookup((StringValue) eval)){
                br.close();
                state.getFileTable().remove((StringValue) eval);
                return null;
            }
        } catch (ExpressionException | DictionaryException | IOException e) {
            throw new StmtException(e.getMessage());
        }
    }

    @Override
    public IStmt deepCopy() {
        return new CloseRFile(this.exp.deepCopy());
    }

    @Override
    public String toString() {
        return "Close file: " + this.exp;
    }
}
