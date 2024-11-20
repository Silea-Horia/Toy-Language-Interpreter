package model.statement;

import model.exception.DictionaryException;
import model.exception.ExpressionException;
import model.exception.StmtException;
import model.expression.IExp;
import model.state.PrgState;
import model.type.IntType;
import model.type.StringType;
import model.value.IValue;
import model.value.IntValue;
import model.value.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFileStmt implements IStmt {
    private IExp exp;
    private String varName;

    public ReadFileStmt(IExp exp, String varName) {
        this.exp = exp;
        this.varName = varName;
    }

    @Override
    public PrgState execute(PrgState state) throws StmtException {
        if (state.getSymTable().contains(this.varName)) throw new StmtException("The variable is not defined!\n");
        try {
            if (!state.getSymTable().lookup(this.varName).getType().equals(new IntType()))
                throw new StmtException("Type is not int!\n");
        } catch (DictionaryException de) {
            throw new StmtException(de.getMessage());
        }
        IValue result = null;
        try {
            result = this.exp.eval(state.getSymTable(), state.getHeap());
        } catch (ExpressionException e) {
            throw new StmtException(e.getMessage());
        }
        if (!result.getType().equals(new StringType())) throw new StmtException("The evaluated expression is not a string type!\n");
        try {
            BufferedReader reader = state.getFileTable().lookup((StringValue)result);
            try {
                String fileLine = reader.readLine();
                if (fileLine == null)
                    fileLine = "0";
                int intFileLine = Integer.parseInt(fileLine);
                state.getSymTable().insert(this.varName, new IntValue(intFileLine));
            } catch (IOException e) {
                throw new StmtException(e.getMessage());
            }
        } catch (DictionaryException e) {
            throw new StmtException(e.getMessage());
        }
        return state;
    }

    @Override
    public IStmt deepCopy() {
        return null;
    }
}
