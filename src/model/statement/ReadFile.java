package model.statement;

import model.adt.ISymTable;
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

public class ReadFile implements IStmt {
    private IExp exp;
    private String varName;
    private static IntType intType;
    private static StringType stringType;

    public ReadFile(IExp exp, String varName) {
        this.exp = exp;
        this.varName = varName;
        stringType = new StringType();
        intType = new IntType();
    }

    @Override
    public PrgState execute(PrgState state) throws StmtException {
        ISymTable<String, IValue> symTable = state.getSymTable();

        if (!symTable.contains(this.varName)) {
            throw new StmtException("Variable doesn't exist");
        }

        try {
            if (!symTable.lookup(this.varName).getType().equals(intType)) {
                throw new StmtException("Variable isn't type int");
            }

            IValue eval = this.exp.eval(symTable);

            if (!eval.getType().equals(stringType)) {
                throw new StmtException("Expression result isn't type string");
            }

            try {
                BufferedReader br = state.getFileTable().lookup((StringValue) eval);
                String readVal = br.readLine();
                IntValue newValue;
                if (readVal == null) {
                    newValue = new IntValue(0);
                } else {
                    newValue = new IntValue(Integer.parseInt(readVal));
                }
                symTable.insert(this.varName, newValue);
                return state;
            } catch (IOException e) {
                throw new StmtException(e.getMessage());
            }
        } catch (DictionaryException | ExpressionException e) {
            throw new StmtException(e.getMessage());
        }
    }

    @Override
    public IStmt deepCopy() {
        return new ReadFile(this.exp.deepCopy(), this.varName);
    }

    @Override
    public String toString() {
        return "Read from file: " + this.exp;
    }
}
