package model.statement;

import model.exception.ExpressionException;
import model.exception.StmtException;
import model.expression.IExp;
import model.state.PrgState;
import model.type.StringType;
import model.value.IValue;
import model.value.StringValue;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class openRFile implements IStmt {
    private IExp exp;

    public openRFile(IExp exp) {
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws StmtException {
        try {
            IValue result = this.exp.eval(state.getSymTable());

            if (!result.getType().equals(new StringType())) {
                throw new StmtException("Expression is not a string\n");
            }

            String filePath = ((StringValue) result).getValue();

            if (state.getSymTable().contains(filePath)) {
                throw new StmtException("File is already open\n");
            }

            try {
                BufferedReader br = new BufferedReader(new FileReader(filePath));

                state.getFileTable().insert((StringValue)result, br);

                return state;
            } catch (FileNotFoundException e) {
                throw new StmtException("File not found\n");
            }
        } catch (ExpressionException e) {
            throw new StmtException(e.getMessage());
        }
    }

    @Override
    public IStmt deepCopy() {
        return new openRFile(exp.deepCopy());
    }
}
